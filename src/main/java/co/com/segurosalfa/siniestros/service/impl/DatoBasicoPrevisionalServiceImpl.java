package co.com.segurosalfa.siniestros.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.CargueSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.FiltroSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.GnrPersonaClienteDTO;
import co.com.segurosalfa.siniestros.dto.GnrTipoDocumentoDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasico;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasicoPrevisional;
import co.com.segurosalfa.siniestros.entity.SnrOrigen;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.GenericSprecification;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrDatoBasicoPrevisionalRepo;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.ISnrDatoBasicoPrevisionalService;
import co.com.segurosalfa.siniestros.service.ISnrDatosBasicosService;
import co.com.segurosalfa.siniestros.util.ObjectsUtil;
import co.com.segurosalfa.siniestros.util.PageableUtil;
import co.com.sipren.common.util.DateUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.SearchCriteria;
import co.com.sipren.common.util.SearchOperation;
import co.com.sipren.common.util.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatoBasicoPrevisionalServiceImpl extends CRUDImpl<SnrDatoBasicoPrevisional, Long>
		implements ISnrDatoBasicoPrevisionalService {

	@Autowired
	private ISnrDatoBasicoPrevisionalRepo repo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IClienteUnicoService clienteUnicoService;
	@Autowired
	private ISnrDatosBasicosService serviceDatoBasico;

	@Autowired
	private ISnrDatoBasicoPrevisionalService serviceDatoBasicoPrevisional;
	

	@Override
	protected IGenericRepo<SnrDatoBasicoPrevisional, Long> getRepo() {
		return repo;
	}

	@Override
	public void crearSiniestroCargue(String usuario, String proceso) throws SiprenException {
		repo.crearSiniestroCargue(usuario, proceso);

	}

	@Override
	public void limpiarTemporalesCargue(String usuario, String proceso) throws SiprenException {
		repo.limpiarTemporalesCargue(usuario, proceso);

	}

	@Override
	public void procesarCargue(CargueSiniestrosDTO siniestro)
			throws SiprenException, NumberFormatException, ParseException {

		this.consultaPorvenirPorAfiliado(ParametroGeneralUtil.USER_TMP, ParametroGeneralUtil.CONS_ORIGEN_CARGUE,
				siniestro.getDocumento() != null ? Long.parseLong(siniestro.getDocumento()) : 0,
				siniestro.getTipoDocumento(),
				siniestro.getTipoSolicitud() != null ? Integer.parseInt(siniestro.getTipoSolicitud()) : 0,
				siniestro.getFechaInicioConsulta() != null
						? DateUtil.convertStringToDate(siniestro.getFechaInicioConsulta())
						: null);

	}

	@Override
	public void consultaPorvenirPorAfiliado(String usuario, String proceso, Long documento, String tipoDoc,
			Integer tipoSolicitud, Date fechaProceso) throws SiprenException, ParseException {

		repo.consultaPorvenirPorAfiliado(usuario, proceso, documento, tipoDoc, tipoSolicitud, fechaProceso);

	}

	public ResponsePageableDTO listarPorFiltro(FiltroSiniestrosDTO dto, Pageable page)
			throws JsonProcessingException, ServiceException, SiprenException {
		GenericSprecification<SnrDatoBasicoPrevisional> genericSprecification = new GenericSprecification<>();
		Page<SnrDatoBasicoPrevisional> pageDatoBasicoPrevisional;
		List<SnrDatoBasicoDTO> listDto = new ArrayList<>();

		/*
		 * Cuando el filtro es hacia un campo de una entidad con la que realiza join se
		 * utiliza esta configuracion. 1. SearchCriteria<?> donde ? es la entidad con la
		 * que hace JOIN 2. El campo al que se le va a aplicar el filtro: "idSinieestro"
		 * 3. El valor que se va a filtar: "dto.getIdSiniestro()" 4. El tipo de filtro
		 * que se va a aplicar: EQUAL 5. Booleano que indica que se debe hacer un JOIN
		 * 6. nombre del campo que esta configurado en la entidad base con JoinColumn
		 */

		if (Objects.nonNull(dto.getSiniestro())) {

			if (Objects.nonNull(dto.getSiniestro().getRango()) && !dto.getSiniestro().getRango().isEmpty()) {

				dto.getSiniestro().getRango().stream().forEach(n -> {
					genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("idSiniestro", n.getDatoInicio(),
							n.getDatoFinal(), SearchOperation.BETWEEN_LONG, Boolean.TRUE, "siniestro", Boolean.FALSE));
				});
			}

			if (Objects.nonNull(dto.getSiniestro().getIndividual()) && !dto.getSiniestro().getIndividual().isEmpty()) {
				dto.getSiniestro().getIndividual().stream().forEach(n -> {
					genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("idSiniestro", n,
							SearchOperation.EQUAL, Boolean.TRUE, "siniestro", Boolean.FALSE));
				});
			}
		}

		if (Objects.nonNull(dto.getPoliza())) {

			if (Objects.nonNull(dto.getPoliza().getRango()) && !dto.getPoliza().getRango().isEmpty()) {
				dto.getPoliza().getRango().stream().forEach(n -> {
					genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("numPoliza", n.getDatoInicio(),
							n.getDatoFinal(), SearchOperation.BETWEEN_LONG, Boolean.TRUE, "siniestro", Boolean.FALSE));
				});
			}

			if (Objects.nonNull(dto.getPoliza().getIndividual()) && !dto.getPoliza().getIndividual().isEmpty()) {
				dto.getPoliza().getIndividual().stream().forEach(n -> {
					genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("numPoliza", n,
							SearchOperation.EQUAL, Boolean.TRUE, "siniestro", Boolean.FALSE));
				});
			}
		}

		if (Objects.nonNull(dto.getPersona())) {

			if (Objects.nonNull(dto.getPersona().getRango()) && !dto.getPersona().getRango().isEmpty()) {
				dto.getPersona().getRango().stream().forEach(n -> {
					genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("persona", n.getDatoInicio(),
							n.getDatoFinal(), SearchOperation.BETWEEN_LONG, Boolean.TRUE, "siniestro", Boolean.FALSE));
				});
			}

			if (Objects.nonNull(dto.getPersona().getIndividual()) && !dto.getPersona().getIndividual().isEmpty()) {
				dto.getPersona().getIndividual().stream().forEach(n -> {
					genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("persona", n,
							SearchOperation.EQUAL, Boolean.TRUE, "siniestro", Boolean.FALSE));
				});
			}

		}

		// Si el filtro es por cedula debe consultar cliente Unico para obtener
		// numPersona
		if (Objects.nonNull(dto.getIdentificacion())) {

			if (Objects.nonNull(dto.getPersona().getRango()) && !dto.getPersona().getRango().isEmpty()) {
				dto.getPersona().getRango().stream().forEach(n -> {
					Long numPersonaI = getNumPersona(Long.valueOf(n.getDatoInicio()));
					Long numPersonaF = getNumPersona(Long.valueOf(n.getDatoFinal()));
					if (Objects.nonNull(numPersonaI) && Objects.nonNull(numPersonaF)) {
						genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("persona", numPersonaI,
								numPersonaF, SearchOperation.BETWEEN_LONG, Boolean.TRUE, "siniestro", Boolean.FALSE));
					}
				});
			}

			if (Objects.nonNull(dto.getPersona().getIndividual()) && !dto.getPersona().getIndividual().isEmpty()) {
				dto.getPersona().getIndividual().stream().forEach(n -> {
					Long numPersona = getNumPersona(Long.valueOf(n));
					if (Objects.nonNull(numPersona)) {
						genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("persona", n,
								SearchOperation.EQUAL, Boolean.TRUE, "siniestro", Boolean.FALSE));
					}
				});
			}

		}

		/*
		 * Cuando el filtro es hacia un campo de una entidad con la que realiza JOIN y
		 * que a su vez tiene un JOIN hacia otra entidad se utiliza esta configuracion.
		 * 1. SearchCriteria<?> donde ? es la entidad con la que hace JOIN 2. El campo
		 * al que se le va a aplicar el filtro: "id" 3. El valor que se va a filtar:
		 * "dto.getOrigen()" 4. El tipo de filtro que se va a aplicar: EQUAL 5. Booleano
		 * que indica que se debe hacer un JOIN 6. nombre del campo que esta configurado
		 * en la sub-entidad con JoinColumn 7. Booleano que indica que se debe hacer un
		 * subJoin 8. nombre del campo que esta configurado en la entidad base con
		 * JoinColumn
		 */
		if (Objects.nonNull(dto.getOrigen())) {
			genericSprecification.addJoins(new SearchCriteria<SnrOrigen>("id", dto.getOrigen(), SearchOperation.EQUAL,
					Boolean.TRUE, "origen", Boolean.TRUE, "siniestro"));
		}

		if (Objects.nonNull(dto.getEstado())) {
			genericSprecification.addJoins(new SearchCriteria<SnrOrigen>("id", dto.getEstado(), SearchOperation.EQUAL,
					Boolean.TRUE, "estado", Boolean.TRUE, "siniestro"));
		}

		if (Objects.nonNull(dto.getFecSiniestroInicial()) && Objects.nonNull(dto.getFecSiniestroFinal())) {

			genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("fecSiniestro",
					dto.getFecSiniestroInicial(), dto.getFecSiniestroFinal(), SearchOperation.BETWEEN, Boolean.TRUE,
					"siniestro", Boolean.FALSE));
		}

		pageDatoBasicoPrevisional = repo.findAll(genericSprecification, page);

		for (SnrDatoBasicoPrevisional datoBasicoPrevisional : pageDatoBasicoPrevisional) {
			SnrDatoBasicoDTO snrDatoBasicoDTO = construirSnrDatoBasico(datoBasicoPrevisional);
			if (Objects.nonNull(snrDatoBasicoDTO)) {
				listDto.add(snrDatoBasicoDTO);
			}
		}

		return PageableUtil.responsePageable(listDto, pageDatoBasicoPrevisional);
	}

	private SnrDatoBasicoDTO construirSnrDatoBasico(SnrDatoBasicoPrevisional snrDatoBasicoPrevisional) {
		SnrDatoBasicoDTO siniestroPrevisionalDTO = modelMapper.map(snrDatoBasicoPrevisional, SnrDatoBasicoDTO.class);
		SnrDatoBasicoDTO siniestroBasicoDTO = modelMapper.map(snrDatoBasicoPrevisional.getSiniestro(),
				SnrDatoBasicoDTO.class);
		GnrPersonaClienteDTO personaClienteDTO = new GnrPersonaClienteDTO();
		personaClienteDTO.setNumPersona(snrDatoBasicoPrevisional.getSiniestro().getPersona());
		siniestroBasicoDTO.setPersona(personaClienteDTO);
		try {
			getInfoPersona(siniestroBasicoDTO);
			siniestroBasicoDTO.getPersona()
					.setNumIdentificacion(Integer.parseInt(siniestroBasicoDTO.getClienteUnico().getCedula()));
			GnrTipoDocumentoDTO tipoDocumentoDTO = new GnrTipoDocumentoDTO();
			tipoDocumentoDTO.setId(Integer.parseInt(siniestroBasicoDTO.getClienteUnico().getTipoDoc()));
			tipoDocumentoDTO.setNombre(siniestroBasicoDTO.getClienteUnico().getTipoDocumento());
			siniestroBasicoDTO.getPersona().setTipoDocumento(tipoDocumentoDTO);
		} catch (JsonProcessingException | SiprenException | ServiceException | IllegalArgumentException
				| SecurityException e) {
			log.error("Error consultando información relacionada con Persona para listado de siniestros: {}", e);
		}
		try {
			return ObjectsUtil.mergeObjects(siniestroBasicoDTO, siniestroPrevisionalDTO);
		} catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error("Error haciendo merge en los objetos de siniestroBasico y siniestroPrevisional : {}", e);
		}
		return null;
	}

	@Override
	public void crearSiniestroPendiente(ProcesarPendientesDTO procesarPendiente)
			throws SiprenException, ParseException {
		log.info("Inicia proceso de procesar afiliado pendiente");
		// Ejecuta SP que se encarga de llenar tablas temporales desde Afp por afiliado
		this.consultaPorvenirPorAfiliado(procesarPendiente.getUsuario(), ParametroGeneralUtil.CONS_ORIGEN_REPROCESAR,
				procesarPendiente.getIdentificacionAfiliado(),
				procesarPendiente.getIdTipoDocumento() != null ? procesarPendiente.getIdTipoDocumento().toString() : "",
				procesarPendiente.getCodTipoSolicitudAfp(),
				DateUtil.convertDateFromLD(procesarPendiente.getFechaSolicitud()));
		log.info("Finaliza proceso de trear datos para el afiliado con cédula: {} desde Afp ",
				procesarPendiente.getIdentificacionAfiliado());

		// Ejecuta SP que crea siniestro y tramite con base a tablas temporales de Afp
		this.crearSiniestroCargue(procesarPendiente.getUsuario(), ParametroGeneralUtil.CONS_ORIGEN_REPROCESAR);
		log.info("Finaliza creación del siniestro");

	}

	private void getInfoPersona(SnrDatoBasicoDTO datoBasico)
			throws SiprenException, ServiceException, JsonProcessingException {

		ClienteUnicoDTO dto = clienteUnicoService
				.consumirRestClienteUnico(String.valueOf(datoBasico.getPersona().getNumPersona()));

		datoBasico.setClienteUnico(dto);
	}

	private Long getNumPersona(Long documento) {
		/*
		 * Si el filtro viene por numIdentificación pero a su vez envian numero de
		 * persona se evalua para evitar consumo a microservicio de cliente unico
		 */
		ClienteUnicoDTO dto;
		try {
			dto = clienteUnicoService.consumirRestClienteUnico(ParametroGeneralUtil.GRAL_TIPO_DOC_CC,
					String.valueOf(documento));

			if (Objects.nonNull(dto)) {
				return dto.getNumPersona();
			}

		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public void actualizaEstadoSiniestro(Long numSiniestro, Integer codEstado) throws SiprenException {
		repo.actualizaEstadoSiniestro(numSiniestro, codEstado);
	}

	@Override
	public String consultaNumPoliza(Date fecSiniestro) throws SiprenException {
		return repo.consultaNumPoliza(fecSiniestro);
	}

	@Override
	public Long consultaUltSiniestroPorAfiliado(Long numPersona) throws SiprenException {
		return repo.consultaUltSiniestroPorAfiliado(numPersona);
	}

	@Override
	public List<SnrDatoBasicoDTO> listarSiniestros()
			throws SiprenException, IllegalAccessException, InstantiationException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		List<SnrDatoBasicoDTO> listaDatosBasicos = new ArrayList<>();
		List<SnrDatoBasicoPrevisional> lista = this.listar();
		for (SnrDatoBasicoPrevisional snrDatoBasicoPrevisional : lista) {
			SnrDatoBasicoDTO siniestroPrevisionalDTO = modelMapper.map(snrDatoBasicoPrevisional,
					SnrDatoBasicoDTO.class);
			SnrDatoBasicoDTO siniestroBasicoDTO = modelMapper.map(snrDatoBasicoPrevisional.getSiniestro(),
					SnrDatoBasicoDTO.class);
			GnrPersonaClienteDTO personaClienteDTO = new GnrPersonaClienteDTO();
			personaClienteDTO.setNumPersona(snrDatoBasicoPrevisional.getSiniestro().getPersona());
			siniestroBasicoDTO.setPersona(personaClienteDTO);
			listaDatosBasicos.add(ObjectsUtil.mergeObjects(siniestroPrevisionalDTO, siniestroBasicoDTO));
		}
		return listaDatosBasicos;
	}

	@Override
	public SnrDatoBasicoDTO listarPorSiniestro(Long numSiniestro) throws SiprenException {
		SnrDatoBasicoPrevisional siniestroPrevisional = repo.listarPorSiniestro(numSiniestro);
		if (Objects.isNull(siniestroPrevisional)) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return construirSnrDatoBasico(siniestroPrevisional);
	}

	@Override
	public SnrDatoBasicoDTO guardarSiniestro(SnrDatoBasicoDTO snrDatoBasicoDTO) throws SiprenException{
		snrDatoBasicoDTO.getEstado().setId(ParametroGeneralUtil.ESTADO_AVISADO);
		snrDatoBasicoDTO.setNumPoliza(Integer.valueOf(serviceDatoBasicoPrevisional
				.consultaNumPoliza(Timestamp.valueOf(snrDatoBasicoDTO.getFecSiniestro().atStartOfDay()))));
		SnrDatoBasicoPrevisional siniestroPrevisional = modelMapper.map(snrDatoBasicoDTO, SnrDatoBasicoPrevisional.class);
		SnrDatoBasico siniestroBasico =  modelMapper.map(snrDatoBasicoDTO, SnrDatoBasico.class);
		
		siniestroBasico.setPersona(snrDatoBasicoDTO.getPersona().getNumPersona());
		siniestroBasico = serviceDatoBasico.registrar(siniestroBasico);
		siniestroPrevisional.setSiniestro(siniestroBasico);
		repo.save(siniestroPrevisional);
		snrDatoBasicoDTO.setIdSiniestro(siniestroBasico.getIdSiniestro());
		return snrDatoBasicoDTO;
	}

	@Override
	public SnrDatoBasicoDTO actualizarSiniestro(SnrDatoBasicoDTO snrDatoBasicoDTO) throws SiprenException {
		SnrDatoBasicoPrevisional siniestroPrevisional = modelMapper.map(snrDatoBasicoDTO,
				SnrDatoBasicoPrevisional.class);
		SnrDatoBasico siniestroBasico = modelMapper.map(snrDatoBasicoDTO, SnrDatoBasico.class);
		siniestroBasico.setPersona(snrDatoBasicoDTO.getPersona().getNumPersona());
		serviceDatoBasico.modificar(siniestroBasico);
		this.modificar(siniestroPrevisional);
		return snrDatoBasicoDTO;
	}

}
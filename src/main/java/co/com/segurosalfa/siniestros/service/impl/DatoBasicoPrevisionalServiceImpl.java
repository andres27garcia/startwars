package co.com.segurosalfa.siniestros.service.impl;

import java.lang.reflect.InvocationTargetException;
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
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoPrevisionalDTO;
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
public class DatoBasicoPrevisionalServiceImpl extends CRUDImpl<SnrDatoBasicoPrevisional, Long> implements ISnrDatoBasicoPrevisionalService {


	@Autowired
	private ISnrDatoBasicoPrevisionalRepo repo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IClienteUnicoService clienteUnicoService;

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
				siniestro.getFechaInicioConsulta() != null ? DateUtil.convertStringToDate(siniestro.getFechaInicioConsulta()) : null);

	}

	@Override
	public void consultaPorvenirPorAfiliado(String usuario, String proceso, Long documento, String tipoDoc,
			Integer tipoSolicitud, Date fechaProceso) throws SiprenException, ParseException {
		//Implementar la conversion desde el SP
		//GnrEquivalenciaTipos gnrEquivalencias = repoEquivalencias.obtenerEquivalenciaAfp(tipoDoc);
//		tipoDoc = gnrEquivalencias != null ? gnrEquivalencias.getAbreviaturaTipoIdAfp() : "";
		repo.consultaPorvenirPorAfiliado(usuario, proceso, documento, tipoDoc, tipoSolicitud, fechaProceso);

	}
	
	public ResponsePageableDTO listarPorFiltro(FiltroSiniestrosDTO dto, Pageable page) {		
		GenericSprecification<SnrDatoBasicoPrevisional> genericSprecification = new GenericSprecification<>();		
		Page<SnrDatoBasicoPrevisional> pageDatoBasicoPrevisional;		
		List<SnrDatoBasicoPrevisionalDTO> listDto = new ArrayList<>();
		
		if(Objects.nonNull(dto.getIdSiniestro())) {
			genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("idSiniestro", 
					dto.getIdSiniestro(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"siniestro"));
		}
		
		if(Objects.nonNull(dto.getNumPoliza())) {
			genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("numPoliza", 
					dto.getNumPoliza(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"siniestro"));
		}
				
		if(Objects.nonNull(dto.getNumPersona())) {						
			genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("numPersona", 
					dto.getNumPersona(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"persona"));		
		}
		
						
		if(Objects.nonNull(dto.getOrigen())) {
			genericSprecification.addJoins(new SearchCriteria<SnrOrigen>("id", 
					dto.getOrigen(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"origen"));
		}
		
		if(Objects.nonNull(dto.getEstado())) {
			genericSprecification.addJoins(new SearchCriteria<SnrOrigen>("id", 
					dto.getEstado(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"estado"));
		}
		
		if((Objects.nonNull(dto.getFecSiniestroInicial()) &&
				!"".equals(dto.getFecSiniestroInicial()))
				&& (Objects.nonNull(dto.getFecSiniestroFinal()) &&
						!"".equals(dto.getFecSiniestroFinal()))) {
			
			genericSprecification.add(new SearchCriteria<SnrDatoBasicoPrevisional>("fecSiniestro", 
					DateUtil.convertDateFromDto(dto.getFecSiniestroInicial()),
					DateUtil.convertDateFromDto(dto.getFecSiniestroFinal()),
					SearchOperation.BETWEEN));
		}
					
		pageDatoBasicoPrevisional = repo.findAll(genericSprecification, page);	
		
		for(SnrDatoBasicoPrevisional datoBasicoPrevisional : pageDatoBasicoPrevisional) {
			SnrDatoBasicoPrevisionalDTO datoBasicoDTO = modelMapper.map(datoBasicoPrevisional, SnrDatoBasicoPrevisionalDTO.class);
			listDto.add(datoBasicoDTO);
			try {
				getInfoPersona(datoBasicoDTO);				
			} catch (JsonProcessingException | SiprenException | ServiceException e) {
				log.error("Error consultando información relacionada con Persona para listado de siniestros: {}", e);
			}
		}
					
		return PageableUtil.responsePageable(listDto, pageDatoBasicoPrevisional);
	}
	
	@Override
	public void crearSiniestroPendiente(ProcesarPendientesDTO procesarPendiente)
			throws SiprenException, ParseException {
		log.info("Inicia proceso de procesar afiliado pendiente");
		// Ejecuta SP que se encarga de llenar tablas temporales desde Afp por afiliado
		this.consultaPorvenirPorAfiliado(procesarPendiente.getUsuario(), ParametroGeneralUtil.CONS_ORIGEN_REPROCESAR,
				procesarPendiente.getIdentificacionAfiliado(),
				procesarPendiente.getIdTipoDocumento() != null ? procesarPendiente.getIdTipoDocumento().toString() : "",
				procesarPendiente.getCodTipoSolicitudAfp(), DateUtil.convertDateFromLD(procesarPendiente.getFechaSolicitud()));
		log.info("Finaliza proceso de trear datos para el afiliado con cédula: {} desde Afp ",
				procesarPendiente.getIdentificacionAfiliado());

		// Ejecuta SP que crea siniestro y tramite con base a tablas temporales de Afp
		this.crearSiniestroCargue(procesarPendiente.getUsuario(), ParametroGeneralUtil.CONS_ORIGEN_REPROCESAR);
		log.info("Finaliza creación del siniestro");

	}
	
	private void getInfoPersona(SnrDatoBasicoPrevisionalDTO datoBasico)
			throws SiprenException, ServiceException, JsonProcessingException {

		ClienteUnicoDTO dto = clienteUnicoService.consumirRestClienteUnico(
				String.valueOf(datoBasico.getSiniestro().getPersona()));
		
		datoBasico.setClienteUnico(dto);		
	}
	
	private void getInfoPersona(SnrDatoBasicoDTO datoBasico)
			throws SiprenException, ServiceException, JsonProcessingException {

		ClienteUnicoDTO dto = clienteUnicoService.consumirRestClienteUnico(
				String.valueOf(datoBasico.getPersona().getNumPersona()));
		
		datoBasico.setClienteUnico(dto);		
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
	public ResponsePageableDTO listarPaginado(Pageable page) throws SiprenException {
		Page<SnrDatoBasicoPrevisional> listPaginado = repo.findAll(page);		
		return PageableUtil.responsePageable(listPaginado.getContent(), listPaginado);
	}

	@Override
	public List<SnrDatoBasicoDTO> listarSiniestros() throws SiprenException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<SnrDatoBasicoDTO> listaDatosBasicos = new ArrayList<>();
		List<SnrDatoBasicoPrevisional> lista = this.listar();
		for (SnrDatoBasicoPrevisional snrDatoBasicoPrevisional : lista) {
			SnrDatoBasicoDTO siniestroPrevisionalDTO = modelMapper.map(snrDatoBasicoPrevisional,
					SnrDatoBasicoDTO.class);			
			SnrDatoBasicoDTO siniestroBasicoDTO = modelMapper.map(snrDatoBasicoPrevisional.getSiniestro(), SnrDatoBasicoDTO.class);
			GnrPersonaClienteDTO personaClienteDTO = new GnrPersonaClienteDTO();
			personaClienteDTO.setNumPersona(snrDatoBasicoPrevisional.getSiniestro().getPersona());
			siniestroBasicoDTO.setPersona(personaClienteDTO);
			listaDatosBasicos.add(ObjectsUtil.mergeObjects(siniestroPrevisionalDTO, siniestroBasicoDTO));
		}
		return listaDatosBasicos;
	}

	@Override
	public SnrDatoBasicoDTO listarPorSiniestro(Long numSiniestro) throws JsonProcessingException, SiprenException, ServiceException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		SnrDatoBasicoPrevisional siniestroPrevisional = repo.listarPorSiniestro(numSiniestro);
		if(Objects.isNull(siniestroPrevisional)) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}		
		SnrDatoBasicoDTO siniestroPrevisionalDTO = modelMapper.map(siniestroPrevisional,
				SnrDatoBasicoDTO.class);
		SnrDatoBasicoDTO siniestroBasicoDTO = modelMapper.map(siniestroPrevisional.getSiniestro(), 
				SnrDatoBasicoDTO.class);
		GnrPersonaClienteDTO personaClienteDTO = new GnrPersonaClienteDTO();
		personaClienteDTO.setNumPersona(siniestroPrevisional.getSiniestro().getPersona());
		siniestroBasicoDTO.setPersona(personaClienteDTO);
		getInfoPersona(siniestroBasicoDTO);
		siniestroBasicoDTO.getPersona().setNumIdentificacion(Integer.parseInt(siniestroBasicoDTO.getClienteUnico().getCedula()));
		GnrTipoDocumentoDTO tipoDocumentoDTO = new GnrTipoDocumentoDTO();
		tipoDocumentoDTO.setId(Integer.parseInt(siniestroBasicoDTO.getClienteUnico().getTipoDoc()));
		tipoDocumentoDTO.setNombre(siniestroBasicoDTO.getClienteUnico().getTipoDocumento());
		siniestroBasicoDTO.getPersona().setTipoDocumento(tipoDocumentoDTO);
		return ObjectsUtil.mergeObjects(siniestroBasicoDTO, siniestroPrevisionalDTO);
	}
}
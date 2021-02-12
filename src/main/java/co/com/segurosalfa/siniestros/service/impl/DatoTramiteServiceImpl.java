package co.com.segurosalfa.siniestros.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.FiltroTramitesDTO;
import co.com.segurosalfa.siniestros.dto.GnrPersonaClienteDTO;
import co.com.segurosalfa.siniestros.dto.GnrTipoDocumentoDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoTramiteDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasico;
import co.com.segurosalfa.siniestros.entity.SnrDatoTramite;
import co.com.segurosalfa.siniestros.entity.SnrEstado;
import co.com.segurosalfa.siniestros.entity.SnrTipo;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.GenericSprecification;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrDatoTramiteRepo;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IDatoTramiteService;
import co.com.segurosalfa.siniestros.util.PageableUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.SearchCriteria;
import co.com.sipren.common.util.SearchOperation;
import co.com.sipren.common.util.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatoTramiteServiceImpl extends CRUDImpl<SnrDatoTramite, Long> implements IDatoTramiteService {

	@Autowired
	private ISnrDatoTramiteRepo repo;
	
	
	@Autowired
	private IClienteUnicoService clienteUnicoService;
	
	@Autowired
	private ModelMapper modelMapper;
		
	

	@Override
	protected IGenericRepo<SnrDatoTramite, Long> getRepo() {
		return repo;
	}

	@Override
	public List<SnrDatoTramiteDTO> listarDatosXSiniestro(Long numSiniestro) throws SiprenException {	
		List<SnrDatoTramite> listTramites = repo.listarDatosPorPersona(numSiniestro);
		List<SnrDatoTramiteDTO> listTramitesDTO = new ArrayList<>();
		listTramites.forEach(tr -> {
			SnrDatoTramiteDTO snrDatoTramiteDTO = modelMapper.map(tr, SnrDatoTramiteDTO.class);
			try {
				mapInfoPersona(snrDatoTramiteDTO, tr.getSiniestro().getPersona());
				listTramitesDTO.add(snrDatoTramiteDTO);
			} catch (SiprenException e) {
				log.error("Error obteniendo tramite: {} ",tr.getIdTramite(), e);
			}
		});
		return listTramitesDTO;
	}

	@Override
	public Long ultimoTramiteXSiniestro(Long numSiniestro) throws SiprenException {
		return repo.ultimoTramiteXSiniestro(numSiniestro);
	}
	

	public void getInfoPersona(SnrDatoBasicoDTO datosBasicos)
			throws SiprenException, ServiceException, JsonProcessingException {
		
		ClienteUnicoDTO dto = clienteUnicoService.consumirRestClienteUnico(
				String.valueOf(datosBasicos.getPersona().getNumPersona()));
		
		datosBasicos.setClienteUnico(dto);		
	}

	@Override
	public SnrDatoTramiteDTO consultarPorId(Long id) throws SiprenException {
		SnrDatoTramite datoTramite = repo.findById(id).orElse(null);
		if(Objects.isNull(datoTramite))
			return null;
		SnrDatoTramiteDTO datosTramitesDTO = modelMapper.map(datoTramite, SnrDatoTramiteDTO.class);
		mapInfoPersona(datosTramitesDTO, datoTramite.getSiniestro().getPersona());
		return datosTramitesDTO;
	}
		
	@Override
	public ResponsePageableDTO listarPorFiltro(FiltroTramitesDTO dto, Pageable page) throws JsonProcessingException, ServiceException, SiprenException {
		GenericSprecification<SnrDatoTramite> genericSprecification = new GenericSprecification<>();
		List<SnrDatoTramiteDTO> listDto = new ArrayList<>();
		Page<SnrDatoTramite> pageSnrDatosTramites;			
								
		if(Objects.nonNull(dto.getIdTramite())) {
			genericSprecification.add(new SearchCriteria<SnrDatoTramite>("idTramite", 
					dto.getIdTramite(), 
					SearchOperation.EQUAL));
		}
		
		if(Objects.nonNull(dto.getNumIdentificacion())) {
			Long numPersona = getNumPersona(dto);
			genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("persona", 
					numPersona, 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"siniestro",
					Boolean.FALSE));
		}
		
		if(Objects.nonNull(dto.getNumPersona())) {
			genericSprecification.addJoins(new SearchCriteria<SnrDatoBasico>("persona", 
					dto.getNumPersona(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"siniestro",
					Boolean.FALSE));
		}
						
		if(Objects.nonNull(dto.getTipoTramite())) {
			genericSprecification.addJoins(new SearchCriteria<SnrTipo>("id", 
					dto.getTipoTramite(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"tipoTramite",
					Boolean.FALSE));
		}
		
		if(Objects.nonNull(dto.getEstadoTramite())) {
			genericSprecification.addJoins(new SearchCriteria<SnrEstado>("id", 
					dto.getEstadoTramite(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"estadoTramite",
					Boolean.FALSE));
		}
		
		if(Objects.nonNull(dto.getClasificacionJur())) {
			genericSprecification.addJoins(new SearchCriteria<SnrTipo>("id", 
					dto.getClasificacionJur(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"clasificacionJur",
					Boolean.FALSE));
		}		
		
		if(Objects.nonNull(dto.getFecRadicacionAlfaIni()) 
				&& Objects.nonNull(dto.getFecRadicacionAlfaFin())) {
			
			genericSprecification.add(new SearchCriteria<SnrDatoTramite>("fecRadicacionAlfa", 
					dto.getFecRadicacionAlfaIni(),
					dto.getFecRadicacionAlfaFin(),
					SearchOperation.BETWEEN));
		}
		
		if(Objects.nonNull(dto.getIdSolicitudAfp())) {
			genericSprecification.add(new SearchCriteria<SnrDatoTramite>("idSolicitudAfp", 
					dto.getIdSolicitudAfp(), 
					SearchOperation.EQUAL));
		}	
		pageSnrDatosTramites = repo.findAll(genericSprecification, page);			
		
		for(SnrDatoTramite datosTramites : pageSnrDatosTramites) {
			SnrDatoTramiteDTO datosTramitesDTO = modelMapper.map(datosTramites, SnrDatoTramiteDTO.class);
			listDto.add(datosTramitesDTO);
			try {
				mapInfoPersona(datosTramitesDTO, datosTramites.getSiniestro().getPersona());
			} catch (SiprenException e) {
				log.error("Error consultando información relacionada con Persona para listado de siniestros: {}", e);
			}
		}
			
		
		return PageableUtil.responsePageable(listDto, pageSnrDatosTramites);		
	}

	@Override
	public List<SnrDatoTramiteDTO> listarDatosPorPersona(Long numPersona) throws SiprenException {
		List<SnrDatoTramite> listTramites = repo.listarDatosPorPersona(numPersona);
		List<SnrDatoTramiteDTO> listTramitesDTO = new ArrayList<>();
		listTramites.forEach(tr -> {
			SnrDatoTramiteDTO snrDatoTramiteDTO = modelMapper.map(tr, SnrDatoTramiteDTO.class);
			try {
				mapInfoPersona(snrDatoTramiteDTO, tr.getSiniestro().getPersona());
				listTramitesDTO.add(snrDatoTramiteDTO);
			} catch (SiprenException e) {
				log.error("Error obteniendo tramite: {} ",tr.getIdTramite(), e);
			}
		});
		return listTramitesDTO;
	}

	@Override
	public void actualizaEstadoTramite(Long numTramite, Integer codEstado) throws SiprenException {
		repo.actualizaEstadoTramite(numTramite, codEstado);		
	}
	
	private void mapInfoPersona(SnrDatoTramiteDTO snrDatoTramiteDTO, Long numPersona) throws SiprenException {
		try {
			if(Objects.nonNull(snrDatoTramiteDTO.getSiniestro())) {
				GnrPersonaClienteDTO personaDTO = new GnrPersonaClienteDTO();
				personaDTO.setNumPersona(numPersona);
				snrDatoTramiteDTO.getSiniestro().setPersona(personaDTO);
				getInfoPersona(snrDatoTramiteDTO.getSiniestro());
				if(Objects.nonNull(snrDatoTramiteDTO.getSiniestro().getClienteUnico())) {
					ClienteUnicoDTO clienteUnico = snrDatoTramiteDTO.getSiniestro().getClienteUnico(); 
					personaDTO.setNumIdentificacion(Integer.parseInt(clienteUnico.getCedula()));
					GnrTipoDocumentoDTO tipoDocumento = new GnrTipoDocumentoDTO();
					tipoDocumento.setId(Integer.parseInt(clienteUnico.getTipoDoc()));
					tipoDocumento.setNombre(clienteUnico.getTipoDocumento());
					personaDTO.setTipoDocumento(tipoDocumento);
				}							
			}				
		} catch (JsonProcessingException | ServiceException e) {
			log.error("Error consultando información relacionada con Persona para listado de tramites", e);
		}
	}
	
	private Long getNumPersona(FiltroTramitesDTO filtroDto) throws JsonProcessingException, ServiceException, SiprenException {
		/*Si el filtro viene por numIdentificación pero a su vez envian numero de persona
		 * se evalua para evitar consumo a microservicio de cliente unico
		 */
		if(Objects.nonNull(filtroDto.getNumPersona())) {
			return filtroDto.getNumPersona();
		}
		
		ClienteUnicoDTO dto = clienteUnicoService.consumirRestClienteUnico(
				ParametroGeneralUtil.GRAL_TIPO_DOC_CC,
				String.valueOf(filtroDto.getNumIdentificacion()));
		
		//Si el documento no retorno resultados con tipoDoc 2, se consulta tipoDoc 1
		if(Objects.isNull(dto)) {
			dto = clienteUnicoService.consumirRestClienteUnico(
					ParametroGeneralUtil.GRAL_TIPO_DOC_TI,
					String.valueOf(filtroDto.getNumIdentificacion()));
		}
		
		if(Objects.nonNull(dto)) {
			return dto.getNumPersona();
		}
		return null;
	}
	
}

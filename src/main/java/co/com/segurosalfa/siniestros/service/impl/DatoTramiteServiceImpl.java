package co.com.segurosalfa.siniestros.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.FiltroTramitesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoTramiteDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoTramite;
import co.com.segurosalfa.siniestros.entity.SnrEstado;
import co.com.segurosalfa.siniestros.entity.SnrTipo;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrDatoTramiteRepo;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IDatoTramiteService;
import co.com.segurosalfa.siniestros.util.PageableUtil;
import co.com.segurosalfa.siniestros.util.SnrDatoTramiteRepoFilter;
import co.com.sipren.common.util.DateUtil;
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
	public ResponsePageableDTO listarPaginado(Pageable page) throws SiprenException {
		Page<SnrDatoTramite> listPaginados = repo.findAll(page);				
		return PageableUtil.responsePageable(listPaginados.getContent(), listPaginados);
	}

	@Override
	public List<SnrDatoTramiteDTO> listarDatosXSiniestro(Long numSiniestro) throws SiprenException {
		List<SnrDatoTramiteDTO> listTramites = repo.listarDatosXSiniestro(numSiniestro).stream()
				.map(t -> modelMapper.map(t, SnrDatoTramiteDTO.class)).collect(Collectors.toList());
		return listTramites;
	}

	@Override
	public Long ultimoTramiteXSiniestro(Long numSiniestro) throws SiprenException {
		return repo.ultimoTramiteXSiniestro(numSiniestro);
	}
	

	public void getInfoPersona(SnrDatoBasicoDTO datosBasicos)
			throws SiprenException, ServiceException, JsonProcessingException {
		
		ClienteUnicoDTO dto = clienteUnicoService.consumirRestClienteUnico(
				String.valueOf(datosBasicos.getPersona()));
		
		datosBasicos.setClienteUnico(dto);		
	}

	@Override
	public SnrDatoTramiteDTO consultarPorId(Long id) throws SiprenException {
		SnrDatoTramiteDTO datosTramitesDTO = modelMapper.map(repo.findById(id).orElse(null), SnrDatoTramiteDTO.class);
		try {
			if(Objects.nonNull(datosTramitesDTO.getSiniestro()))
				getInfoPersona(datosTramitesDTO.getSiniestro());
		} catch (JsonProcessingException | ServiceException e) {
			log.error("Error consultando información relacionada con Persona para listado de tramites: {}", e);
		}
		return datosTramitesDTO;
	}

	@Override
	public ResponsePageableDTO listarPorFiltro(FiltroTramitesDTO dto, Pageable page) {
		SnrDatoTramiteRepoFilter<SnrDatoTramite> genericSprecification = new SnrDatoTramiteRepoFilter<>();
		List<SnrDatoTramiteDTO> listDto = new ArrayList<>();
		Page<SnrDatoTramite> pageSnrDatosTramites;			
								
		if(Objects.nonNull(dto.getIdTramite())) {
			genericSprecification.add(new SearchCriteria<SnrDatoTramite>("idTramite", 
					dto.getIdTramite(), 
					SearchOperation.EQUAL));
		}
		
		if(Objects.nonNull(dto.getNumIdentificacion())) {
			genericSprecification.setIdentificacion(Boolean.TRUE);
			genericSprecification.addJoins(new SearchCriteria<SnrDatoTramite>("numIdentificacion", 
					dto.getNumIdentificacion(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"persona",
					Boolean.TRUE));
		}
		
		if(Objects.nonNull(dto.getNumPersona())) {
			genericSprecification.setIdentificacion(Boolean.TRUE);
			genericSprecification.addJoins(new SearchCriteria<SnrDatoTramite>("numPersona", 
					dto.getNumPersona(), 
					SearchOperation.EQUAL,
					Boolean.TRUE,
					"persona",
					Boolean.TRUE));
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
		
		if((Objects.nonNull(dto.getFecRadicacionAlfaIni()) &&
				!"".equals(dto.getFecRadicacionAlfaIni()))
				&& (Objects.nonNull(dto.getFecRadicacionAlfaFin()) &&
						!"".equals(dto.getFecRadicacionAlfaFin()))) {
			
			genericSprecification.add(new SearchCriteria<SnrDatoTramite>("fecRadicacionAlfa", 
					DateUtil.convertDateFromDto(dto.getFecRadicacionAlfaIni()),
					DateUtil.convertDateFromDto(dto.getFecRadicacionAlfaFin()),
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
				getInfoPersona(datosTramitesDTO.getSiniestro());				
			} catch (JsonProcessingException | SiprenException | ServiceException e) {
				log.error("Error consultando información relacionada con Persona para listado de siniestros: {}", e);
			}
		}
			
		
		return PageableUtil.responsePageable(listDto, pageSnrDatosTramites);		
	}

	@Override
	public List<SnrDatoTramiteDTO> listarDatosPorPersona(Long numPersona) throws SiprenException {
		List<SnrDatoTramiteDTO> listTramites = repo.listarDatosPorPersona(numPersona).stream()
				.map(t -> modelMapper.map(t, SnrDatoTramiteDTO.class)).collect(Collectors.toList());
		return listTramites;
	}

	@Override
	public void actualizaEstadoTramite(Long numTramite, Integer codEstado) throws SiprenException {
		repo.actualizaEstadoTramite(numTramite, codEstado);		
	}
	

}

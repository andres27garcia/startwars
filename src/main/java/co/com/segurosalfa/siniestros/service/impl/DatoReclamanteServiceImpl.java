package co.com.segurosalfa.siniestros.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.GnrPersonaClienteDTO;
import co.com.segurosalfa.siniestros.dto.GnrTipoDocumentoDTO;
import co.com.segurosalfa.siniestros.dto.ListadoReclamantesDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ReprocesoReclamantesDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoReclamanteDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoReclamante;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrDatoReclamanteRepo;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IDatoReclamanteService;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatoReclamanteServiceImpl extends CRUDImpl<SnrDatoReclamante, Long> implements IDatoReclamanteService {

	@Autowired
	private ISnrDatoReclamanteRepo repo;

	@Autowired
	private IClienteUnicoService clienteUnicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	protected IGenericRepo<SnrDatoReclamante, Long> getRepo() {
		return repo;
	}

	@Override
	public List<ListadoReclamantesDTO> listarPorTramite(Long numTramite) throws SiprenException {

		List<ListadoReclamantesDTO> lista = new ArrayList<>();

		repo.listarPorTramite(numTramite).forEach(reclamante -> {

			try {
				ListadoReclamantesDTO tmp = new ListadoReclamantesDTO();
				ClienteUnicoDTO dto = this.getInfoPersona(reclamante);
				if(Objects.nonNull(dto)) {
					tmp.setIdReclamnte(reclamante.getIdReclamante());
					tmp.setIdtramite(reclamante.getTramite().getIdTramite());
					tmp.setCodEstadoReclamante(reclamante.getEstadoReclamante().getId());
					tmp.setEstadoReclamante(reclamante.getEstadoReclamante().getNombre());
					GnrPersonaClienteDTO persona = new GnrPersonaClienteDTO();
					persona.setNumPersona(reclamante.getNumPersona());
					persona.setNumIdentificacion(Integer.parseInt(dto.getCedula()));
					GnrTipoDocumentoDTO tipoDocumento = new GnrTipoDocumentoDTO();
					tipoDocumento.setId(Integer.parseInt(dto.getTipoDoc()));
					tipoDocumento.setNombre(dto.getTipoDocumento());
					persona.setTipoDocumento(tipoDocumento);
					tmp.setPersona(persona);
					tmp.setNombres(dto.getPrimerNombre().concat(" ")
							.concat(dto.getSegundoNombre()).concat(" ")
							.concat(dto.getPrimerApell()).concat(" ")
							.concat(dto.getSegundoApell()));
					tmp.setCodInv(dto.getCondicionInvalidez().equalsIgnoreCase("") ? 
							ParametroGeneralUtil.GRAL_BLN_INVALIDO : dto.getCondicionInvalidez());
				}
				lista.add(tmp);
			} catch (JsonProcessingException | SiprenException | ServiceException e) {
				log.error("Error consultando información relacionada con Persona para listado de reclamantes {}", e);
			}
		});

		return lista;
	}

	public ClienteUnicoDTO getInfoPersona(SnrDatoReclamante datosBasicos)
			throws SiprenException, ServiceException, JsonProcessingException {

		return clienteUnicoService.consumirRestClienteUnico(String.valueOf(datosBasicos.getNumPersona()));
	}

	@Override
	public void insertarReclamante(SnrDatoReclamante dto) throws SiprenException {

		repo.insertarReclamante(dto.getTramite().getIdTramite(), dto.getNumPersona(),
				dto.getEstadoReclamante().getId());
	}

	@Override
	public SnrDatoReclamante listarPorTramitePersona(Long numTramite, Long numPersona) throws SiprenException {
		return repo.listarPorTramitePersona(numTramite, numPersona);
	}

	@Override
	public void crearDatosReclamante(ProcesarPendientesDTO dto) throws SiprenException {
		repo.crearDatosReclamante(dto.getIdSiniestro(), dto.getIdTramite(), dto.getIdSolicitudAfp(), dto.getUsuario(),
				ParametroGeneralUtil.CONS_ORIGEN_REPROCESAR);
	}

	@Override
	public List<ReprocesoReclamantesDTO> consultaReprocesoReclamante(Long tramite, Long numPersona)
			throws SiprenException, JsonProcessingException, ServiceException {
		List<ReprocesoReclamantesDTO> listReclamantes = repo.consultaReprocesoReclamante(tramite, numPersona);
		for (ReprocesoReclamantesDTO reprocesoReclamantesDTO : listReclamantes) {
			ClienteUnicoDTO reclamante = clienteUnicoService.consumirRestClienteUnico(
					String.valueOf(reprocesoReclamantesDTO.getNumPersonaReclamante()));
			reprocesoReclamantesDTO.setNombreReclamante(reclamante);
			reprocesoReclamantesDTO.setTipoDocAfiliado(reclamante.getTipoDoc());
			reprocesoReclamantesDTO.setTipoDocReclamante(reclamante.getCedula());
			
			ClienteUnicoDTO afiliado = clienteUnicoService.consumirRestClienteUnico(
					String.valueOf(reprocesoReclamantesDTO.getNumPersonaAfiliado()));
			reprocesoReclamantesDTO.setDocumentoAfil(Integer.parseInt(afiliado.getCedula()));
		}
		return listReclamantes;
	}

	@Override
	public void guardarReclamante(SnrDatoReclamanteDTO datoReclamanteDTO) throws SiprenException {
		SnrDatoReclamante ent = modelMapper.map(datoReclamanteDTO, SnrDatoReclamante.class);
		ent.setNumPersona(datoReclamanteDTO.getPersona().getNumPersona());
		SnrDatoReclamante obj = listarPorTramitePersona(ent.getTramite().getIdTramite(), ent.getNumPersona());
		if (obj != null) {
			throw new SiprenException(ParametrosMensajes.ERROR_EXISTS_DATA);
		}
		repo.save(ent);
	}

}

package co.com.segurosalfa.siniestros.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.ListadoReclamantesDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ReprocesoReclamantesDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoReclamante;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrDatosReclamanteRepo;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IDatoReclamanteService;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatoReclamanteServiceImpl extends CRUDImpl<SnrDatoReclamante, Long>
		implements IDatoReclamanteService {

	@Autowired
	private ISnrDatosReclamanteRepo repo;

	@Autowired
	private IClienteUnicoService clienteUnicoService;

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
				tmp.setIdReclamnte(reclamante.getId());
				tmp.setIdtramite(reclamante.getTramite().getIdTramite());
				tmp.setCodEstadoReclamante(reclamante.getEstadoReclamante().getId());
				tmp.setEstadoReclamante(reclamante.getEstadoReclamante().getNombre());
				tmp.setPersona(reclamante.getPersona());
				tmp.setNombres(dto.getPrimerNombre() + " " + dto.getSegundoNombre() + " " + dto.getPrimerApell() + " "
						+ dto.getSegundoApell());
				tmp.setCodInv(dto.getCondicionInvalidez().equalsIgnoreCase("") ? "N" : dto.getCondicionInvalidez());
				lista.add(tmp);
			} catch (JsonProcessingException | SiprenException | ServiceException e) {
				log.error("Error consultando información relacionada con Persona para listado de reclamantes {}", e);
			}
		});

		return lista;
	}

	public ClienteUnicoDTO getInfoPersona(SnrDatoReclamante datosBasicos)
			throws SiprenException, ServiceException, JsonProcessingException {

		return clienteUnicoService.consumirRestClienteUnico(
				String.valueOf(datosBasicos.getPersona()));
	}

	@Override
	public void insertarReclamante(SnrDatoReclamante dto) throws SiprenException {

		repo.insertarReclamante(dto.getTramite().getIdTramite(), dto.getPersona(),
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
	public List<ReprocesoReclamantesDTO> consultaReprocesoReclamante(Long tramite, Long documento)
			throws SiprenException {
		return repo.consultaReprocesoReclamante(tramite, documento.intValue());
	}

}

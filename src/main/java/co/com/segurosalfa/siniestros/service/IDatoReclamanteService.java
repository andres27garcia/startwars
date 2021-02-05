package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.dto.ListadoReclamantesDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ReprocesoReclamantesDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoReclamante;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IDatoReclamanteService extends ICRUD<SnrDatoReclamante, Long> {

	List<ListadoReclamantesDTO> listarPorTramite( Long numTramite) throws SiprenException;

	SnrDatoReclamante listarPorTramitePersona(Long numTramite, Long numPersona)
			throws SiprenException;

	void insertarReclamante(SnrDatoReclamante dto) throws SiprenException;
	
	void crearDatosReclamante(ProcesarPendientesDTO dto) throws SiprenException;
	
	List<ReprocesoReclamantesDTO> consultaReprocesoReclamante(Long tramite, Long documento) throws SiprenException;

}

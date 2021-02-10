package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.dto.SnrComentarioReclamanteDTO;
import co.com.segurosalfa.siniestros.entity.SnrComentarioReclamante;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IComentarioReclamanteService extends ICRUD<SnrComentarioReclamante, Long> {

	List<SnrComentarioReclamante> listarDatosPorReclamante(Long idReclamante) throws SiprenException;
	void guardarComentarios(SnrComentarioReclamanteDTO comentarioDTO);

}

package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.dto.SnrComentarioTramiteDTO;
import co.com.segurosalfa.siniestros.entity.SnrComentarioTramite;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IComentarioTramiteService extends ICRUD<SnrComentarioTramite, Long>{

	List<SnrComentarioTramiteDTO> listarDatosPorTramite(Long numTramite) throws SiprenException;
	
}

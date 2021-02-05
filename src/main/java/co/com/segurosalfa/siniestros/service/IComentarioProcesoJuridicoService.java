package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.entity.SnrComentarioProcesoJur;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IComentarioProcesoJuridicoService extends ICRUD<SnrComentarioProcesoJur, Long>{

	List<SnrComentarioProcesoJur> listarPorProcesoJuridico(Long idProcesoJur)
			throws SiprenException;
	
}

package co.com.segurosalfa.siniestros.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrHilHistoriaIbl;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IHilHistoriaIblService extends ICRUD<SnrHilHistoriaIbl, Long>{

	List<SnrHilHistoriaIbl> listarPorPersona(@Param("numPersona") Long numPersona) throws SiprenException;
	
}

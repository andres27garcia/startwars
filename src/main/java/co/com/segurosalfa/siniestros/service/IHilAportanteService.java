package co.com.segurosalfa.siniestros.service;

import co.com.segurosalfa.siniestros.entity.SnrHilAportante;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IHilAportanteService extends ICRUD<SnrHilAportante, Long>{

	SnrHilAportante consultarPorNit(Long numNit) throws SiprenException;
}

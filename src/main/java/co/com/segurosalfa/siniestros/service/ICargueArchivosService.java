package co.com.segurosalfa.siniestros.service;

import co.com.segurosalfa.siniestros.entity.SnrCargueArchivos;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ICargueArchivosService extends ICRUD<SnrCargueArchivos, Integer> {

	SnrCargueArchivos getCargueActivo(String tipo) throws SiprenException;
}

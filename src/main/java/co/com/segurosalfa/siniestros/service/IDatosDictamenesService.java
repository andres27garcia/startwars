package co.com.segurosalfa.siniestros.service;

import co.com.segurosalfa.siniestros.entity.ClfDatosDictamenes;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IDatosDictamenesService extends ICRUD<ClfDatosDictamenes, Integer> {

	ClfDatosDictamenes consultaPorPersona(Integer numPersona) throws SiprenException;

}

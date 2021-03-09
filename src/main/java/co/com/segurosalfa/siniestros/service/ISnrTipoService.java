package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.entity.SnrTipo;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrTipoService extends ICRUD<SnrTipo, Integer>{
	
	List<SnrTipo> listarPorClase(String clase) throws SiprenException;

}
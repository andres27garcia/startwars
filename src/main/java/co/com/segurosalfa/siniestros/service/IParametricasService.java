package co.com.segurosalfa.siniestros.service;

import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrParametrica;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IParametricasService extends ICRUD<SnrParametrica, Integer>{
	
	SnrParametrica parametroXNombre(@Param("nombre") String nombre) throws SiprenException;

}

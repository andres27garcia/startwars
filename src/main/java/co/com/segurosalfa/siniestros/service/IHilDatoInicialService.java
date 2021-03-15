package co.com.segurosalfa.siniestros.service;

import java.time.LocalDate;
import java.util.List;

import co.com.segurosalfa.siniestros.entity.SnrHilDatoInicial;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IHilDatoInicialService extends ICRUD<SnrHilDatoInicial, Long>{
	
	List<SnrHilDatoInicial> listarPorPersona(Long numPersona) throws SiprenException;
	void actualizarRegistroInvalido(String blnRegInv, Long numPersona, LocalDate fecha);

}
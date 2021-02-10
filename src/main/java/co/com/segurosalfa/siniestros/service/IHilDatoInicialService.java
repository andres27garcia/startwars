package co.com.segurosalfa.siniestros.service;

import java.time.LocalDate;

import co.com.segurosalfa.siniestros.entity.SnrHilDatoInicial;

public interface IHilDatoInicialService extends ICRUD<SnrHilDatoInicial, Integer>{
	
	void actualizarRegistroInvalido(String blnRegInv, Long numPersona, LocalDate fecha);

}
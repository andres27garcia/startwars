package co.com.segurosalfa.siniestros.service;

import java.text.ParseException;
import java.util.Date;

import co.com.segurosalfa.siniestros.dto.CargueSiniestrosDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasico;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrDatosBasicosService extends ICRUD<SnrDatoBasico, Integer> {

	void crearSiniestroCargue(String usuario, String proceso) throws SiprenException;

	void limpiarTemporalesCargue(String usuario, String proceso) throws SiprenException;

	void procesarCargue(CargueSiniestrosDTO siniestro) throws SiprenException, NumberFormatException, ParseException;

	void consultaPorvenirPorAfiliado(String usuario, String proceso, Long documento, String tipoDoc,
			Integer tipoSolicitud, Date fechaProceso) throws SiprenException, ParseException;

}
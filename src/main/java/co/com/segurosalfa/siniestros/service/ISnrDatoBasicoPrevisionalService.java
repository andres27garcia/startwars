package co.com.segurosalfa.siniestros.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.CargueSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.FiltroSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasicoPrevisional;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.sipren.common.util.ServiceException;

public interface ISnrDatoBasicoPrevisionalService extends ICRUD<SnrDatoBasicoPrevisional, Long>{
	
	List<SnrDatoBasicoDTO> listarSiniestros() throws SiprenException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException ;
	SnrDatoBasicoDTO listarPorSiniestro(Long numSiniestro) throws JsonProcessingException, SiprenException, ServiceException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException ;
	void crearSiniestroCargue(String usuario, String proceso) throws SiprenException;
	void limpiarTemporalesCargue(String usuario, String proceso) throws SiprenException;
	void procesarCargue(CargueSiniestrosDTO siniestro) throws SiprenException, NumberFormatException, ParseException;
	void consultaPorvenirPorAfiliado(String usuario, String proceso, Long documento, String tipoDoc,
			Integer tipoSolicitud, Date fechaProceso) throws SiprenException, ParseException;
	ResponsePageableDTO listarPorFiltro(FiltroSiniestrosDTO dto, Pageable page) throws SiprenException;
	void crearSiniestroPendiente(ProcesarPendientesDTO procesarPendiente) throws SiprenException, ParseException;
	void actualizaEstadoSiniestro(Long numSiniestro, Integer codEstado) throws SiprenException;
	public String consultaNumPoliza(Date fecSiniestro) throws SiprenException;
	public Long consultaUltSiniestroPorAfiliado(Long numPersona) throws SiprenException;
	public ResponsePageableDTO listarPaginado(Pageable page) throws SiprenException;
	
}
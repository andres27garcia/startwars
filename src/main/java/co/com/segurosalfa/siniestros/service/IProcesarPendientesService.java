package co.com.segurosalfa.siniestros.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.ComparacionPersonaDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.sipren.common.util.ServiceException;

public interface IProcesarPendientesService {

	public List<ProcesarPendientesDTO> listarPendientesAfiliados() throws SiprenException;

	public List<ProcesarPendientesDTO> consultarPendientePorCedula(Integer tipoDoc, Long documento)
			throws SiprenException, JsonProcessingException, ServiceException;

	public List<ComparacionPersonaDTO> procesarPendientes(ClienteUnicoDTO clienteUnico, ClienteUnicoDTO afiliadoAfp,
			Boolean comparaAfp) throws SiprenException;

	public List<ProcesarPendientesDTO> listarPendientesInfoAdicional() throws JsonProcessingException, ServiceException, SiprenException;

	public List<ProcesarPendientesDTO> listarPendientesreclamante() throws SiprenException, JsonProcessingException, ServiceException;

	public List<ProcesarPendientesDTO> consultarReclamantePorCedula(Integer tipoDoc, Long documento) throws SiprenException, JsonProcessingException, ServiceException;

	public List<ProcesarPendientesDTO> consultarPendientesInfoAdicionalPorCedula(Integer tipoDoc, Long documento) throws JsonProcessingException, ServiceException, SiprenException ;

	public void eliminarPendientePorCedula(Integer tipoDoc, Long documento);
	
	public void eliminarPendientePorCedulaAdicional(Integer tipoDoc, Long documento);
}

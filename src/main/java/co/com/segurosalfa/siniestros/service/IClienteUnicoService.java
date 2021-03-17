package co.com.segurosalfa.siniestros.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.CuEpsDTO;
import co.com.segurosalfa.siniestros.dto.CuEstadoCivilDTO;
import co.com.segurosalfa.siniestros.dto.GnrPersonaClienteDTO;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.sipren.common.util.ServiceException;

public interface IClienteUnicoService {

	ClienteUnicoDTO consumirRestClienteUnico(String numPersona)
			throws ServiceException, SiprenException, JsonProcessingException;

	ClienteUnicoDTO consumirRestClienteUnico(String tipoDocumento, String documento)
			throws ServiceException, SiprenException, JsonProcessingException;

	List<GnrPersonaClienteDTO> consumirRestClienteUnicoRango(Integer docIni, Integer docFin)
			throws ServiceException, SiprenException, JsonProcessingException;
	
	CuEpsDTO consultarEps(String codigo) 
			throws ServiceException, SiprenException, JsonProcessingException;
	
	CuEstadoCivilDTO consultarEstadoCivil(String codigo) 
			throws ServiceException, SiprenException, JsonProcessingException;

}

package co.com.segurosalfa.siniestros.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.sipren.common.util.ServiceException;

public interface IClienteUnicoService {

	ClienteUnicoDTO consumirRestClienteUnico(String numPersona)
			throws ServiceException, SiprenException, JsonProcessingException;

	ClienteUnicoDTO consumirRestClienteUnico(String tipoDocumento, String documento)
			throws ServiceException, SiprenException, JsonProcessingException;

	String consumirRestClienteUnicoRango(Integer docIni, Integer docFin)
			throws ServiceException, SiprenException, JsonProcessingException;

}

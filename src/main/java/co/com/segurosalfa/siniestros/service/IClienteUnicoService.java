package co.com.segurosalfa.siniestros.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.sipren.common.util.ServiceException;

public interface IClienteUnicoService {
	
	public ClienteUnicoDTO consumirRestClienteUnico(String numPersona) throws ServiceException, SiprenException, JsonProcessingException;
	public ClienteUnicoDTO consumirRestClienteUnico(String tipoDocumento, String documento) throws ServiceException, SiprenException, JsonProcessingException;

}

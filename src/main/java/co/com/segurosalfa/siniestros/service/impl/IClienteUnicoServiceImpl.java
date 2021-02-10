package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.sipren.common.util.ClienteRestGenerico;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ServiceException;

@Service
public class IClienteUnicoServiceImpl implements IClienteUnicoService {

	@Autowired
	private ClienteRestGenerico service;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private IParametricasService paramService;

	@Override
	public ClienteUnicoDTO consumirRestClienteUnico(String numPersona)
			throws ServiceException, SiprenException, JsonProcessingException {
		Object response;
		response = service.executeApi(null, HttpMethod.GET,
				paramService.parametroXNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U).getValor(),
				paramService.parametroXNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U_PERSONA).getValor()
						.replace("{numPersona}", numPersona),
				5000);
		String json = mapper.writeValueAsString(response);
		return mapper.readValue(json, ClienteUnicoDTO.class);
	}

	@Override
	public ClienteUnicoDTO consumirRestClienteUnico(String tipoDocumento, String numeroDocumento)
			throws ServiceException, SiprenException, JsonProcessingException {
		Object response;
		response = service.executeApi(null, HttpMethod.GET,
				paramService.parametroXNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U).getValor(),
				paramService.parametroXNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U_AFIL).getValor()
						.replace("{tipoDoc}", tipoDocumento).replace("{doc}", numeroDocumento),
				5000);
		String json = mapper.writeValueAsString(response);
		return mapper.readValue(json, ClienteUnicoDTO.class);
	}

}

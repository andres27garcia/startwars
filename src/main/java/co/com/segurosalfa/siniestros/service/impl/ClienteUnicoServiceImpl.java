package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.CuEpsDTO;
import co.com.segurosalfa.siniestros.dto.CuEstadoCivilDTO;
import co.com.segurosalfa.siniestros.dto.GnrPersonaClienteDTO;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.sipren.common.util.ClienteRestGenerico;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClienteUnicoServiceImpl implements IClienteUnicoService {

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
		try {
			response = service.executeApi(null, HttpMethod.GET,
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U).getValor(),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U_PERSONA).getValor()
							.replace("{numPersona}", numPersona),
					5000);
			String json = mapper.writeValueAsString(response);
			return mapper.readValue(json, ClienteUnicoDTO.class);
		} catch (Exception e) {
			log.error("Error consumiendo servicio de cliente unico por número de persona", e);
			return null;
		}
	}

	@Override
	public ClienteUnicoDTO consumirRestClienteUnico(String tipoDocumento, String numeroDocumento)
			throws ServiceException, SiprenException, JsonProcessingException {
		Object response;
		try {
			response = service.executeApi(null, HttpMethod.GET,
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U).getValor(),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U_AFIL).getValor()
							.replace("{tipoDoc}", tipoDocumento).replace("{doc}", numeroDocumento),
					5000);
			String json = mapper.writeValueAsString(response);
			return mapper.readValue(json, ClienteUnicoDTO.class);
		} catch (Exception e) {
			log.error("Error consumiendo servicio de cliente unico por tipo y número de documento", e);
			return null;
		}
	}

	@Override
	public List<GnrPersonaClienteDTO> consumirRestClienteUnicoRango(Integer docIni, Integer docFin)
			throws ServiceException, SiprenException, JsonProcessingException {
		Object response;
		try {
			response = service.executeApi(null, HttpMethod.GET,
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U).getValor(),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U_RANGO).getValor()
							.replace("{docIni}", String.valueOf(docIni)).replace("{docFin}", String.valueOf(docFin)),
					5000);
			String json = mapper.writeValueAsString(response);
			return mapper.readValue(json, new TypeReference<List<GnrPersonaClienteDTO>>() {
			});
		} catch (Exception e) {
			log.error("Error consumiendo servicio de cliente unico por rangos", e);
			return null;
		}
	}

	@Override
	public CuEpsDTO consultarEps(String codigo) throws ServiceException, SiprenException, JsonProcessingException {
		Object response;
		try {
			response = service.executeApi(null, HttpMethod.GET,
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U).getValor(),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U_EPS).getValor()
							.replace("{id}", String.valueOf(codigo)),
					5000);
			String json = mapper.writeValueAsString(response);
			return mapper.readValue(json, CuEpsDTO.class);
		} catch (Exception e) {
			log.error("Error consumiendo servicio de cliente unico para eps", e);
			return null;
		}
	}

	@Override
	public CuEstadoCivilDTO consultarEstadoCivil(String codigo) {
		Object response;
		try {
			response = service.executeApi(null, HttpMethod.GET,
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U).getValor(),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_CLIENTE_U_EST_CIVIL).getValor()
							.replace("{id}", String.valueOf(codigo)),
					5000);
			String json = mapper.writeValueAsString(response);
			return mapper.readValue(json, CuEstadoCivilDTO.class);
		} catch (Exception e) {
			log.error("Error consumiendo servicio de cliente unico para estado civil", e);
			return null;
		}
	}

}

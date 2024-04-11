package co.com.starwars.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.starwars.api.dto.ResponseStarWarsApi;
import co.com.starwars.api.exception.ExceptionStarWarsApiResponse;
import co.com.starwars.api.exception.ServiceException;
import co.com.starwars.api.service.IClientStarWarsApiService;
import co.com.starwars.api.util.ClientRestGeneric;
import co.com.starwars.api.util.GeneralParameterUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ClientStarWarsApi implements IClientStarWarsApiService {

	@Autowired
	ClientRestGeneric service;

	@Autowired
	ObjectMapper mapper;

	@Override
	public ResponseStarWarsApi getFilmById(int id) throws ServiceException {
		Object response;
		try {
			response = service.executeApi(null, HttpMethod.GET,
					GeneralParameterUtil.BASE_URL_STAR_WARS_API,
					GeneralParameterUtil.STAR_WARS_API_FILMS.concat("/").concat(String.valueOf(id)),
					5000);
			String json = mapper.writeValueAsString(response);
			return mapper.readValue(json, ResponseStarWarsApi.class);
		} catch (Exception e) {
			log.error(String.format("Error getFilmById: %s", validarError(e)));
			return null;
		}
	}

	private String validarError(Exception e) {
		try {
			String json = mapper.writeValueAsString(e.getMessage());
			return mapper.readValue(json, ExceptionStarWarsApiResponse.class).getDetail();
		} catch (JsonProcessingException e1) {
			return e.getMessage();
		}
	}

}

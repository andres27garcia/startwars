package co.com.starwars.api.util;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import co.com.starwars.api.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ClientRestGeneric {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	HttpServletRequest request;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object executeApi(Object dto, HttpMethod httpMethod, String endpoint, String path, Integer timeOut)
			throws ServiceException {
		try {
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entityReq = null;

			if (null == dto) {
				entityReq = new HttpEntity(headers);
			} else {
				entityReq = new HttpEntity<Object>(dto, headers);
			}

			restTemplate = new RestTemplate(clientHttpRequestFactory(timeOut));
			ResponseEntity<Object> response = restTemplate.exchange(endpoint + path, httpMethod, entityReq,
					Object.class);

			if (null != response.getBody()) {
				return response.getBody();
			}

		} catch (HttpClientErrorException e) {
			throw new ServiceException(e.getResponseBodyAsString());
		} catch (Exception e) {
			throw new ServiceException(
					new ApiError("Error al consumir el " + " " + endpoint + path + "Error:" + e.getMessage(),
							e.getMessage(), "ERROR"));
		} 
		
		return null;
	}

	public Object uploadFile(File dto, String endpoint, String path, Integer timeOut) throws ServiceException {


		try {
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			FileSystemResource value = new FileSystemResource(dto);
			map.add("archivo", value);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<LinkedMultiValueMap<String, Object>> entityReq = new HttpEntity<LinkedMultiValueMap<String, Object>>(
					map, headers);
			restTemplate = new RestTemplate(clientHttpRequestFactory(timeOut));
			ResponseEntity<String> response = restTemplate.exchange(endpoint + path, HttpMethod.POST, entityReq,
					String.class);

			if (null != response.getBody())
				return response.getBody();

		} catch (HttpClientErrorException e) {
			throw new ServiceException(e.getResponseBodyAsString());
		} catch (Exception e) {
			throw new ServiceException(
					new ApiError("Error al consumir el " + " " + endpoint + path + "Error:" + e.getMessage(),
							e.getMessage(), "ERROR"));
		} 
		
		return null;
	}

	public ClientHttpRequestFactory clientHttpRequestFactory(Integer timeOut) {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(timeOut);
		return factory;
	}

}

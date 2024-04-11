package co.com.starwars.api.exception;

import java.io.Serializable;

import co.com.starwars.api.util.ApiError;

public class ServiceException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
	}

	private ApiError apiError;

	public ServiceException(String text, Exception e) {
		super(text, e);
	}

	public ServiceException(String text) {
		super(text);
	}

	public ServiceException(ApiError apiError) {
		super(apiError.getMessageUser());
		this.apiError = apiError;
	}

	public ServiceException(ApiError apiError, String message) {
		super(message);
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}

}

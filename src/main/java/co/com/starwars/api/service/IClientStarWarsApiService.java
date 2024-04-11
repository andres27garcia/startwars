package co.com.starwars.api.service;

import co.com.starwars.api.dto.ResponseStarWarsApi;
import co.com.starwars.api.exception.ServiceException;

public interface IClientStarWarsApiService {
	 ResponseStarWarsApi getFilmById(int id) throws ServiceException;
}

package co.com.starwars.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.starwars.api.entity.Film;
import co.com.starwars.api.repo.IFilmsRepo;
import co.com.starwars.api.repo.IGenericRepo;
import co.com.starwars.api.service.IFilmService;

@Service
public class FilmImpl extends CRUDImpl<Film, Integer> implements IFilmService {

	@Autowired
	private IFilmsRepo repo;

	@Override
	protected IGenericRepo<Film, Integer> getRepo() {
		return repo;
	}

}
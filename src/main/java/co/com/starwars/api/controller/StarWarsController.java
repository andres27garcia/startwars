package co.com.starwars.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.starwars.api.dto.ResponseApi;
import co.com.starwars.api.dto.ResponseStarWarsApi;
import co.com.starwars.api.entity.Film;
import co.com.starwars.api.exception.ModelNotFoundException;
import co.com.starwars.api.exception.ServiceException;
import co.com.starwars.api.service.IClientStarWarsApiService;
import co.com.starwars.api.service.IFilmService;
import co.com.starwars.api.util.Messages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/films")
public class StarWarsController {

	@Autowired
	IFilmService service;
	@Autowired
	IClientStarWarsApiService starWarsApiService;
	@Autowired
	HttpServletRequest request;
	@Autowired
	ModelMapper modelMapper;

	@GetMapping("/{id}")
    public ResponseEntity<ResponseApi> findById(@PathVariable("id") int id) throws ServiceException {
        Film film = service.listarPorId(id);
        if (film == null) {
            film = getDataApi(id);
        }
        ResponseApi response = modelMapper.map(film, ResponseApi.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Film getDataApi(int id) throws ServiceException {
    	ResponseStarWarsApi responseStarWarsApi = starWarsApiService.getFilmById(id);
        if (responseStarWarsApi == null) {
            throw new ModelNotFoundException(Messages.ERROR_NO_DATA);
        }

        Film film = modelMapper.map(responseStarWarsApi, Film.class);
        film.setNum_film(id); 
        service.registrar(film);
        return film;
    }

	@PutMapping
	public ResponseEntity<ResponseApi> modify(@Valid @RequestBody ResponseApi dto) throws ServiceException {

		Film objSave = service.modificar(modelMapper.map(dto, Film.class));
		ResponseApi obj = modelMapper.map(objSave, ResponseApi.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) throws ServiceException {
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
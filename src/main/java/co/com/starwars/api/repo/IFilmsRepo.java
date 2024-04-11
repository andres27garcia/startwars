package co.com.starwars.api.repo;

import org.springframework.stereotype.Repository;
import co.com.starwars.api.entity.Film;

@Repository
public interface IFilmsRepo extends IGenericRepo<Film, Integer> {

}
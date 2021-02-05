package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import co.com.segurosalfa.siniestros.entity.SnrProcesoJuridico;

public interface ISnrProcesoJuridicoRepo extends IGenericRepo<SnrProcesoJuridico, Integer> {

	@Query("SELECT pj FROM SnrProcesoJuridico pj JOIN "
			+ "pj.tramite t JOIN t.siniestro s JOIN s.persona p "
			+ "WHERE p.numPersona = :numPersona ")
	public List<SnrProcesoJuridico> listarPorPersona(Long numPersona);
}

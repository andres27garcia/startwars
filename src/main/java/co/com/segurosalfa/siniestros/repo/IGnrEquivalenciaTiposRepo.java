package co.com.segurosalfa.siniestros.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.GnrEquivalenciaTipos;

public interface IGnrEquivalenciaTiposRepo extends IGenericRepo<GnrEquivalenciaTipos, Integer> {

	@Query("FROM GnrEquivalenciaTipos g WHERE g.codTipoIdentificacion = :codigo")
	public GnrEquivalenciaTipos obtenerEquivalenciaAfp(@Param("codigo") String codigo);

}
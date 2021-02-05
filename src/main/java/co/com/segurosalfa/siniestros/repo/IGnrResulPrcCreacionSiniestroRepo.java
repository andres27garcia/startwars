package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;

@Repository
public interface IGnrResulPrcCreacionSiniestroRepo extends IGenericRepo<SnrResulPrcCreacionSiniestro, Integer> {

	@Query("FROM GnrResulPrcCreacionSiniestro gr WHERE gr.flgProceso = :proceso")
	public List<SnrResulPrcCreacionSiniestro> consultarPorProceso(@Param("proceso") String proceso);

}
package co.com.segurosalfa.siniestros.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrParametrica;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface IGnrParametricasRepo extends IGenericRepo<SnrParametrica, Integer>{

	@Query("FROM GnrParametricas gp where gp.nombre= :nombre")
	SnrParametrica parametroXNombre(@Param("nombre") String nombre) throws SiprenException;
	
}
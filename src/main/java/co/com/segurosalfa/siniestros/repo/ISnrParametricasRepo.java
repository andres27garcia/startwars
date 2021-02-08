package co.com.segurosalfa.siniestros.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrParametrica;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface ISnrParametricasRepo extends IGenericRepo<SnrParametrica, Integer>{

	@Query("FROM SnrParametrica gp where gp.nombre= :nombre")
	SnrParametrica parametroXNombre(@Param("nombre") String nombre) throws SiprenException;
	
}
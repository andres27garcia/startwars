package co.com.segurosalfa.siniestros.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrCargueArchivos;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface ISnrCargueArchivosRepo extends IGenericRepo<SnrCargueArchivos, Integer> {

	@Query("FROM SnrCargueArchivos se where se.idTipoCargue = :tipoCargue AND se.estado='EN PROCESO'")
	SnrCargueArchivos getCargueActivo(@Param("tipoCargue") String tipo) throws SiprenException;
}
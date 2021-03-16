package co.com.segurosalfa.siniestros.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrHilAportante;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface ISnrHilAportanteRepo extends IGenericRepo<SnrHilAportante, Long>{

	@Query("FROM SnrHilAportante hi where hi.nidNumeroDocumento = :numNit")
	SnrHilAportante consultarPorNit(@Param("numNit") Long numNit) throws SiprenException;
}
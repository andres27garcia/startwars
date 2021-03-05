package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrEstado;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface ISnrEstadoRepo extends IGenericRepo<SnrEstado, Integer> {

	@Query("FROM SnrEstado se where se.tipo = :tipo")
	List<SnrEstado> listarPorTipo(@Param("tipo") String tipo) throws SiprenException;
}
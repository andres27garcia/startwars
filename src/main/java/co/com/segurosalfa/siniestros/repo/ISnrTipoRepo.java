package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrTipo;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface ISnrTipoRepo extends IGenericRepo<SnrTipo, Integer> {

	@Query("FROM SnrTipo st where st.clase = :clase")
	List<SnrTipo> listarPorClase(@Param("clase") String clase) throws SiprenException;
}
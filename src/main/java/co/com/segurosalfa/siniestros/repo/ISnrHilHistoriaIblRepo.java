package co.com.segurosalfa.siniestros.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.com.segurosalfa.siniestros.entity.SnrHilHistoriaIbl;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrHilHistoriaIblRepo extends IGenericRepo<SnrHilHistoriaIbl, Long>{
	
	@Query("FROM SnrHilHistoriaIbl hi where hi.persona = :numPersona")
	List<SnrHilHistoriaIbl> listarPorPersona(@Param("numPersona") Long numPersona) throws SiprenException;
	
	@Transactional
	@Modifying
	@Query("DELETE FROM SnrHilHistoriaIbl hi where hi.persona = :numPersona and hi.fecInicialCot < :fecha")
	void depurarHistoriaLaboral(@Param("numPersona") Long numPersona, @Param("fecha") LocalDate fecha) throws SiprenException;

}

package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrHilHistoriaIbl;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrHilHistoriaIblRepo extends IGenericRepo<SnrHilHistoriaIbl, Long>{
	
	@Query("FROM SnrHilHistoriaIbl hi where hi.persona = :numPersona")
	List<SnrHilHistoriaIbl> listarPorPersona(@Param("numPersona") Long numPersona) throws SiprenException;

}

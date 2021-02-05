package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrComentarioTramite;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrComentarioTramiteRepo extends IGenericRepo<SnrComentarioTramite, Long>{
	
	@Query("FROM SnrComentarioTramite o where o.tramite.idTramite = :numTramite")
	List<SnrComentarioTramite> listarDatosPorTramite(@Param("numTramite") Long numTramite) throws SiprenException;

}

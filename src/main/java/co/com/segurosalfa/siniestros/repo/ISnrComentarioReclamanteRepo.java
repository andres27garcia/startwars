package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrComentarioReclamante;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrComentarioReclamanteRepo extends IGenericRepo<SnrComentarioReclamante, Long>{
	
	@Query("FROM SnrComentarioReclamante o where o.reclamante.id = :idReclamante")
	List<SnrComentarioReclamante> listarDatosPorReclamante(@Param("idReclamante") Long idReclamante) throws SiprenException;

}

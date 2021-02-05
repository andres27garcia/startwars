package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrComentarioProcesoJur;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrComentarioProcesoJuridicoRepo extends IGenericRepo<SnrComentarioProcesoJur, Long> {

	@Query("FROM SnrComentarioProcesoJur o where o.procesoJuridico.idProcesoJuridico = :idProcesoJur")
	List<SnrComentarioProcesoJur> listarPorProcesoJuridico(@Param("idProcesoJur") Long idProcesoJur)
			throws SiprenException;

}

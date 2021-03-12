package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;

@Repository
public interface ISnrResulPrcCreacionSiniestroRepo extends IGenericRepo<SnrResulPrcCreacionSiniestro, Integer> {

	@Query("FROM SnrResulPrcCreacionSiniestro gr WHERE gr.flgProceso = :proceso")
	List<SnrResulPrcCreacionSiniestro> consultarPorProceso(@Param("proceso") String proceso);

	@Query(value = "SELECT COUNT(1) TOTAL_REGISTROS_PROCESAR FROM SNR_TMP_SOLICITUDES_AFP WHERE FLG_PROCESO = 'A'", nativeQuery = true)
	Integer registProcesar();

}
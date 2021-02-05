package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrDatoTramite;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrDatoTramiteRepo extends IGenericRepo<SnrDatoTramite, Long>, JpaSpecificationExecutor<SnrDatoTramite>{
	
	@Query("FROM SnrDatoTramite dt where dt.siniestro.idSiniestro = :numSiniestro")
	List<SnrDatoTramite> listarDatosXSiniestro(@Param("numSiniestro") Long numSiniestro) throws SiprenException;
	
	@Query("FROM SnrDatoTramite dt where dt.siniestro.persona = :numPersona")
	List<SnrDatoTramite> listarDatosPorPersona(@Param("numPersona") Long numPersona) throws SiprenException;
	
	@Query("SELECT MAX(dt.idTramite) FROM SnrDatoTramite dt where dt.siniestro.idSiniestro = :numSiniestro")
	Long ultimoTramiteXSiniestro(@Param("numSiniestro") Long numSiniestro) throws SiprenException;
		
		
	@Transactional
	@Modifying
	@Query(value = "UPDATE SNR_DATO_TRAMITE SET ID_ESTADO_TRAMITE =:codEstado WHERE ID_TRAMITE =:numTramite", nativeQuery = true)
	void actualizaEstadoTramite(@Param("numTramite") Long numTramite, @Param("codEstado") Integer codEstado)
			throws SiprenException;

}

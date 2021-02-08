package co.com.segurosalfa.siniestros.repo;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrDatoBasicoPrevisional;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrDatoBasicoPrevisionalRepo extends IGenericRepo<SnrDatoBasicoPrevisional, Long>,
 JpaSpecificationExecutor<SnrDatoBasicoPrevisional> {

	@Procedure(procedureName = "SINIESTROS.PKG_CREACION_SINIESTRO.PRC_CREAR_SINIESTRO_CARGUE")
	void crearSiniestroCargue(@Param("usuario") String usuario, @Param("proceso") String proceso);

	@Procedure(procedureName = "SINIESTROS.PKG_CREACION_SINIESTRO.PRC_ELIMINAR_INFO_PORVENIR_CARGUE")
	void limpiarTemporalesCargue(@Param("usuario") String usuario, @Param("proceso") String proceso);
	
	@Query("SELECT MAX(o.siniestro.idSiniestro) FROM SnrDatoBasicoPrevisional o where o.siniestro.persona = :numPersona")
	Long consultaUltSiniestroPorAfiliado(@Param("numPersona") Long numPersona) throws SiprenException;
	
	@Procedure(procedureName = "SINIESTROS.PKG_CREACION_SINIESTRO.PRC_CONSULTA_INFO_PORVENIR_CARGUE")
	void consultaPorvenirPorAfiliado(@Param("usuario") String usuario, @Param("proceso") String proceso,
			@Param("documento") Long documento, @Param("tipoDoc") String tipoDoc, @Param("tipoSolicitud") Integer tipoSolicitud,
			@Param("fechaproceso") Date fechaProceso) throws SiprenException;
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE SNR_DATO_BASICO SET ID_ESTADO_SINIESTRO =:codEstado WHERE ID_SINIESTRO =:numSiniestro", nativeQuery = true)
	void actualizaEstadoSiniestro(@Param("numSiniestro") Long numSiniestro, @Param("codEstado") Integer codEstado)
			throws SiprenException;
	
	@Procedure(procedureName = "SINIESTROS.PKG_EQUIVALENCIAS.fPolizaAfp")
	String consultaNumPoliza(@Param("pfecpoliza") Date fecSiniestro) throws SiprenException;
	
}
package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.segurosalfa.siniestros.entity.SnrParametrica;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface ISnrParametricasRepo extends IGenericRepo<SnrParametrica, Integer> {

	@Query("FROM SnrParametrica gp where gp.nombre= :nombre")
	SnrParametrica parametroPorNombre(@Param("nombre") String nombre) throws SiprenException;

	@Modifying
	@Query(value = "UPDATE SNR_PARAMETRICA SET VALOR = :codFecCorte WHERE NOMBRE = 'CONS_PRC_AUT_TRAM_FEC_CORTE'", nativeQuery = true)
	public Integer actualizarParamFecCorte(@Param("codFecCorte") Integer fecCorte);

	@Procedure(procedureName = "PKG_ADMINISTRACION_JOBS.PRC_CREAR_JOB")
	public Void programarJob(@Param("idJob") String idJob, @Param("fecEjecucion") String fecEjecucion,
			@Param("usuario") String usuario, @Param("email") String email, @Param("frecuencia") Integer frecuencia,
			@Param("detalleFrec") String detalleFrec, @Param("accionJob") String accionJob);

	@Procedure(procedureName = "PKG_ADMINISTRACION_JOBS.PRC_CANCELA_JOB")
	public Integer cancelarJob(@Param("idJob") String idJob, @Param("usuario") String usuario);

	@Query(value = "SELECT LJ.ID_JOB,LJ.NOMBRE,HJ.ID_FRECUENCIA PERIODICIDAD,NVL(TO_CHAR(HJ.FEC_EJECUCION, 'YYYY/MM/DD HH24:MI'), ' ') FEC_ULTIMA_EJECUCION,CASE SJ.STATE WHEN 'SCHEDULED' THEN 'Pendiente de Ejecutar' WHEN 'RUNNING' THEN 'En Ejecuci\\u00f3n'  WHEN 'FAILED' THEN 'Finalizado con Errores' WHEN 'SUCCEEDED' THEN 'Finalizado sin Errores' WHEN 'COMPLETED' THEN 'Finalizado sin Errores' WHEN 'STOPPED' THEN 'Detenido' ELSE 'Cancelado' END ESTADO FROM ( SELECT DISTINCT(ID_JOB) ID_JOB,'NOMBRE' NOMBRE FROM SNR_HISTORICO_JOB) LJ LEFT JOIN SNR_HISTORICO_JOB HJ ON (LJ.ID_JOB = HJ.ID_JOB) LEFT JOIN SYS.ALL_SCHEDULER_JOBS SJ ON (LJ.ID_JOB = SJ.JOB_NAME) WHERE HJ.ID_HISTORICO_JOB =(SELECT MAX(T.ID_HISTORICO_JOB) HISTORICO_JOB FROM SNR_HISTORICO_JOB T WHERE T.ID_JOB = LJ.ID_JOB);", nativeQuery = true)
	public List<Object[]> listarProcesos();

	@Query(value = "SELECT LJ.ID_JOB,LJ.NOMBRE,HJ.ID_FRECUENCIA PERIODICIDAD,NVL(TO_CHAR(HJ.FEC_EJECUCION, 'YYYY/MM/DD HH24:MI'), ' ') FECHA_INICIO,NVL(TO_CHAR(HJ.FECHA_PERIODO_CORTE, 'YYYY/MM/DD HH24:MI'), ' ') PERIODO_CORTE,NVL(TO_CHAR(HJ.FECHA_CANCELACION, 'YYYY/MM/DD HH24:MI'), ' ') FECHA_CANCELACION,NVL(HJ.USUARIO_MODIFICACION, ' ') USUARIO_INICIO,NVL(HJ.USUARIO_CANCELACION, ' ') USUARIO_CANCELACION FROM (SELECT DISTINCT(ID_JOB) ID_JOB,'NOMBRE' NOMBRE FROM SNR_HISTORICO_JOB) LJ LEFT JOIN SNR_HISTORICO_JOB HJ ON (LJ.ID_JOB = HJ.ID_JOB) LEFT JOIN SYS.ALL_SCHEDULER_JOBS SJ ON (LJ.ID_JOB = SJ.JOB_NAME) WHERE HJ.ID_JOB = :idProceso AND HJ.ID_HISTORICO_JOB =( SELECT MAX(T.ID_HISTORICO_JOB) HISTORICO_JOB FROM SNR_HISTORICO_JOB T WHERE T.ID_JOB = :idProceso)", nativeQuery = true)
	public List<Object[]> detalleProceso(@Param("idProceso") String idProceso);

	@Query(value = "SELECT LJ.ID_JOB,LJ.NOMBRE,HJ.ID_FRECUENCIA PERIODICIDAD,NVL(TO_CHAR(HJ.FEC_EJECUCION, 'YYYY/MM/DD HH24:MI'), '') FECHA_INICIO,	NVL(TO_CHAR(HJ.FECHA_PERIODO_CORTE, 'YYYY/MM/DD HH24:MI'), '') PERIODO_CORTE,NVL(TO_CHAR(HJ.FECHA_CANCELACION, 'YYYY/MM/DD HH24:MI'), '') FECHA_CANCELACION,NVL(HJ.USUARIO_MODIFICACION, '') USUARIO_INICIO,NVL(HJ.USUARIO_CANCELACION, ' ') USUARIO_CANCELACION FROM (	SELECT	DISTINCT(ID_JOB) ID_JOB,'NOMBRE' NOMBRE	FROM SNR_HISTORICO_JOB) LJ LEFT JOIN SNR_HISTORICO_JOB HJ ON (LJ.ID_JOB = HJ.ID_JOB) LEFT JOIN SYS.ALL_SCHEDULER_JOBS SJ ON (LJ.ID_JOB = SJ.JOB_NAME) WHERE	HJ.ID_JOB = :idProceso AND HJ.FECHA_CANCELACION IS NOT NULL ORDER BY HJ.FECHA_CANCELACION DESC", nativeQuery = true)
	public List<Object[]> historicoProceso(@Param("idProceso") String idProceso);

}
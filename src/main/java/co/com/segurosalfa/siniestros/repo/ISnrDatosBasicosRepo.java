package co.com.segurosalfa.siniestros.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrDatoBasico;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrDatosBasicosRepo extends IGenericRepo<SnrDatoBasico, Long> {

	@Procedure(procedureName = "SINIESTROS.PKG_CREACION_SINIESTRO.PRC_CREAR_SINIESTRO_CARGUE")
	void crearSiniestroCargue(@Param("usuario") String usuario, @Param("proceso") String proceso);

	@Procedure(procedureName = "SINIESTROS.PKG_CREACION_SINIESTRO.PRC_ELIMINAR_INFO_PORVENIR_CARGUE")
	void limpiarTemporalesCargue(@Param("usuario") String usuario, @Param("proceso") String proceso);

	@Procedure(procedureName = "SINIESTROS.PKG_CREACION_SINIESTRO.PRC_CONSULTA_INFO_PORVENIR_CARGUE")
	void consultaPorvenirPorAfiliado(@Param("usuario") String usuario, @Param("proceso") String proceso,
			@Param("documento") Long documento, @Param("tipoDoc") String tipoDoc,
			@Param("tipoSolicitud") Integer tipoSolicitud, @Param("fechaproceso") Date fechaProceso)
			throws SiprenException;

}
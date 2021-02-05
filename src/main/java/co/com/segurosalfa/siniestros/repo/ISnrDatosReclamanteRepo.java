package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.dto.ReprocesoReclamantesDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoReclamante;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrDatosReclamanteRepo extends IGenericRepo<SnrDatoReclamante, Long> {

	@Query("FROM SnrDatoReclamante o where o.tramite.idTramite = :numTramite")
	List<SnrDatoReclamante> listarPorTramite(@Param("numTramite") Long numTramite) throws SiprenException;

	@Query("FROM SnrDatoReclamante o where o.tramite.idTramite = :numTramite and o.persona.numPersona = :numPersona")
	SnrDatoReclamante listarPorTramitePersona(@Param("numTramite") Long numTramite, @Param("numPersona") Long numPersona) throws SiprenException;

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO SNR_DATOS_RECLAMANTES (ID_TRAMITE, NUM_PERSONA, COD_ESTADO_RECLAMANTE, ID_RECLAMANTE ) VALUES(:numTramite, :numPersona, :codEstado, SQ_SNR_DATOS_RECLAMANTES.NEXTVAL)", nativeQuery = true)
	void insertarReclamante(@Param("numTramite") Long numTramite,
			@Param("numPersona") Long numPersona, @Param("codEstado") Integer codEstado) throws SiprenException;

	@Procedure(procedureName = "NUEVO_SIPREN.PKG_PROCESO_RECLAMANTES.PRC_CREA_DATOS_RECLAMANTES")
	void crearDatosReclamante(@Param("siniestro") Long siniestro,@Param("tramite") Long tramite,
			@Param("solicitudAfp") Integer solicitudAfp, 
			@Param("usuario") String usuario, @Param("origen") String origen);

	@Query("select new co.com.siniestros.dto.ReprocesoReclamantesDTO(s.persona.tipoDocumento.nombre,s.persona.numIdentificacion, dt.idTramite, td.nombre, gp.numIdentificacion, gp.numPersona)"  
			+ " FROM SnrDatoTramite dt  "
			+ " LEFt JOIN SnrDatoReclamante sr ON sr.tramite.idTramite = dt.idTramite "
			+ " LEFT JOIN GnrPersonaCliente gp ON gp.numPersona = sr.persona.numPersona "
			+ " LEFT JOIN SnrDatosBasicos s ON s.idSiniestro = dt.siniestro.idSiniestro "
			+ " LEFT JOIN GnrTiposDocumentos td ON td.id = gp.tipoDocumento.id"
		    + " WHERE dt.idTramite = :tramite AND gp.numIdentificacion = :documento")
	List<ReprocesoReclamantesDTO> consultaReprocesoReclamante(@Param("tramite") Long tramite,
			@Param("documento") Integer documento) throws SiprenException;

}

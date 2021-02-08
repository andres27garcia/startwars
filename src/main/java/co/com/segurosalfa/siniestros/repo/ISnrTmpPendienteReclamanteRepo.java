package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrTmpPendienteReclamante;

public interface ISnrTmpPendienteReclamanteRepo extends IGenericRepo<SnrTmpPendienteReclamante, Long>{

	@Query("select new co.com.siniestros.dto.ProcesarPendientesDTO(pr.idPendienteReclamante, pr.codTipoIdentificacion, gps.numIdentificacion, pr.nidNumeroIdentificacion, pr.idSiniestro, pr.idTramite, dt.fecRadicacionAlfa) "
			+ "FROM SnrTmpPendienteReclamante pr "
			+ "JOIN SnrDatosTramites dt ON pr.idTramite = dt.idTramite "		
			+ "JOIN SnrDatosBasicos s ON pr.idSiniestro = s.idSiniestro "
			+ "JOIN GnrPersonaCliente gps ON gps = s.persona order by pr.idPendienteReclamante asc")
	public List<ProcesarPendientesDTO> listarPendientesreclamante();
	

	
	@Query("select distinct new co.com.siniestros.dto.ProcesarPendientesDTO(pr.idPendienteReclamante, pr.codTipoIdentificacion, gps.numIdentificacion, pr.nidNumeroIdentificacion, pr.idSiniestro, pr.idTramite, dt.fecRadicacionAlfa,"
			+ "pr.primerNombre, pr.segundoNombre, pr.primerApellido, pr.segundoApellido, dt.idSolicitudAfp, gp.numPersona, er.id, td.nombre) "
			+ "FROM SnrTmpPendienteReclamante pr "
			+ "LEFT JOIN SnrDatosTramites dt ON pr.idTramite = dt.idTramite "
			+ "LEFT JOIN GnrPersonaCliente gp ON gp.numIdentificacion = pr.nidNumeroIdentificacion "
			+ "LEFT JOIN SnrDatosReclamantes r ON r.persona.numPersona = gp.numPersona "
			+ "LEFT JOIN SnrEstadosReclamante er ON er = r.estadoReclamante "
			+ "LEFT JOIN SnrDatosBasicos s ON pr.idSiniestro = s.idSiniestro "		
			+ "LEFT JOIN GnrPersonaCliente gps ON gps = s.persona "
			+ "JOIN GnrTiposDocumentos td ON td.id = pr.codTipoIdentificacion "
			+ "where pr.codTipoIdentificacion = :tipoDoc and pr.nidNumeroIdentificacion = :documento ")
	public List<ProcesarPendientesDTO> consultarReclamantePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
}

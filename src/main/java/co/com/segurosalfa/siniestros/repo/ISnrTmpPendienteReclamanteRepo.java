package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrTmpPendienteReclamante;

public interface ISnrTmpPendienteReclamanteRepo extends IGenericRepo<SnrTmpPendienteReclamante, Long>{
					  
	@Query("select new co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO(pr.idPendienteReclamante, pr.codTipoIdentificacion, s.persona, pr.nidNumeroIdentificacion, pr.idSiniestro, pr.idTramite, dt.fecRadicacionAlfa) "
			+ "FROM SnrTmpPendienteReclamante pr "
			+ "JOIN SnrDatoTramite dt ON pr.idTramite = dt.idTramite "		
			+ "JOIN SnrDatoBasico s ON pr.idSiniestro = s.idSiniestro "
			+ "order by pr.idPendienteReclamante asc")
	public List<ProcesarPendientesDTO> listarPendientesreclamante();
	

	
	@Query("select distinct new co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO(pr.idPendienteReclamante, pr.codTipoIdentificacion, pr.nidNumeroIdentificacion, pr.idSiniestro, pr.idTramite, dt.fecRadicacionAlfa,"
			+ "pr.primerNombre, pr.segundoNombre, pr.primerApellido, pr.segundoApellido, dt.idSolicitudAfp, s.persona, er.id, st.nombre) "
			+ "FROM SnrTmpPendienteReclamante pr "
			+ "LEFT JOIN SnrDatoTramite dt ON pr.idTramite = dt.idTramite "
			+ "LEFT JOIN SnrDatoReclamante r ON r.tramite = dt "
			+ "LEFT JOIN SnrEstado er ON er = r.estadoReclamante "
			+ "LEFT JOIN SnrDatoBasico s ON pr.idSiniestro = s.idSiniestro "		
			+ "LEFT JOIN SnrTipo st ON st.codigo  = pr.codTipoIdentificacion "
			+ "LEFT JOIN SnrTipoEquivalencia ste ON ste.codEquivalencia = pr.codTipoIdentificacion "
			+ "where pr.codTipoIdentificacion = :tipoDoc "
			+ "and pr.nidNumeroIdentificacion = :documento "
			+ "AND st.clase = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SNR_TIPO_IDENTIF "
			+ "AND ste.tipo = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SNR_TIPO_EQUIV_T_IDENT ")
	public List<ProcesarPendientesDTO> consultarReclamantePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
}

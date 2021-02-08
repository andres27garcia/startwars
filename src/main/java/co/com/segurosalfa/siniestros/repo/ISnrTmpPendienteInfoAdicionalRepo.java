package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrTmpPendInfoAdicional;

public interface ISnrTmpPendienteInfoAdicionalRepo extends IGenericRepo<SnrTmpPendInfoAdicional, Long>{
	
	@Query("select new co.com.siniestros.dto.ProcesarPendientesDTO(pa.idInfoAdicional, pa.nidNumeroIdentificacion, pa.idSiniestro, pa.idTramite, dt.fecRadicacionAfp, pa.tipoRolPersona, pa.codTipoIdentificacion, sin.persona.numPersona) "
			+ "FROM SnrTmpPendInfoAdicional pa "
			+ "LEFT JOIN SnrDatoTramite dt ON pa.idTramite = dt.idTramite "	
			+ "LEFT JOIN SnrDatoBasico sin ON sin.idSiniestro = pa.idSiniestro "	
			+ "order by pa.idInfoAdicional asc")
	List<ProcesarPendientesDTO> listarPendientesInfoAdicional();
	
	@Query("select new co.com.siniestros.dto.ProcesarPendientesDTO(pa.idInfoAdicional, pa.nidNumeroIdentificacion, pa.idSiniestro, pa.idTramite, dt.fecRadicacionAfp, pa.tipoRolPersona,"
			+ "pa.genero, pa.codEstadoCivil, pa.fechaNacimiento, pa.codEps, pa.fechaMuerte, pa.codTipoIdentificacion) "
			+ "FROM SnrTmpPendInfoAdicional pa "
			+ "LEFT JOIN SnrDatoTramite dt ON pa.idTramite = dt.idTramite "
			+ "WHERE pa.codTipoIdentificacion = :tipoDoc and pa.nidNumeroIdentificacion = :documento")
	List<ProcesarPendientesDTO> consultarPendientesInfoAdicionalPorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);

}

package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrTmpPendInfoAdicional;

public interface ISnrTmpPendienteInfoAdicionalRepo extends IGenericRepo<SnrTmpPendInfoAdicional, Long>{
	
	@Query("select new co.com.siniestros.dto.ProcesarPendientesDTO(pa.idInfoAdicional, td.nombre, pa.nidNumeroIdentificacion, pa.idSiniestro, pa.idTramite, dt.fecRadicacionAfp, pa.tipoRolPersona, pa.codTipoIdentificacion, sin.persona.numIdentificacion) "
			+ "FROM SnrTmpPendInfoAdicional pa "
			+ "LEFT JOIN SnrDatosTramites dt ON pa.idTramite = dt.idTramite "	
			+ "LEFT JOIN SnrDatosBasicos sin ON sin.idSiniestro = pa.idSiniestro "	
			+ "JOIN GnrTiposDocumentos td ON td.id = pa.codTipoIdentificacion order by pa.idInfoAdicional asc")
	List<ProcesarPendientesDTO> listarPendientesInfoAdicional();
	
	@Query("select new co.com.siniestros.dto.ProcesarPendientesDTO(pa.idInfoAdicional, td.nombre, pa.nidNumeroIdentificacion, pa.idSiniestro, pa.idTramite, dt.fecRadicacionAfp, pa.tipoRolPersona,"
			+ "pa.genero, pa.codEstadoCivil, ge.nombre, pa.fechaNacimiento, pa.codEps, e.razonSocial, pa.fechaMuerte, pa.codTipoIdentificacion) "
			+ "FROM SnrTmpPendInfoAdicional pa "
			+ "LEFT JOIN SnrDatosTramites dt ON pa.idTramite = dt.idTramite "
			+ "JOIN GnrTiposDocumentos td ON td.id = pa.codTipoIdentificacion "
			+ "LEFT JOIN GnrEps e ON e.idEps = pa.codEps "
			+ "LEFT JOIN GnrEstadosCiviles ge ON ge.id = pa.codEstadoCivil "
			+ "WHERE pa.codTipoIdentificacion = :tipoDoc and pa.nidNumeroIdentificacion = :documento")
	List<ProcesarPendientesDTO> consultarPendientesInfoAdicionalPorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);

}

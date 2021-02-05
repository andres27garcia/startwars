package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrTmpPendienteAfiliado;

public interface IPendienteAfiliadoRepo extends IGenericRepo<SnrTmpPendienteAfiliado, Long>{
		

	@Query("select new co.com.siniestros.dto.ProcesarPendientesDTO(pa.idPendienteAfiliado, pa.codTipoDocumento, td.nombre, pa.numeroDocumento, ts.nombre, pa.idSolicitudAfp, pa.fecSolicitud, pa.codTipoSolicitudAfp, ge.abreviaturaTipoIdAfp) FROM SnrTmpPendienteAfiliado pa LEFT JOIN SnrTipoSolicitudAfp ts ON pa.codTipoSolicitudAfp = ts.codTipoSolicitudAfp"
			+ " JOIN GnrTiposDocumentos td ON td.id = pa.codTipoDocumento "
			+ " JOIN GnrEquivalenciaTipos ge ON ge.codTipoIdentificacion = pa.codTipoDocumento "
			+ "order by pa.idPendienteAfiliado asc")
	public List<ProcesarPendientesDTO> listarPendientesAfiliados();
	
	@Query("select new co.com.siniestros.dto.ProcesarPendientesDTO(pa.idPendienteAfiliado, pa.codTipoDocumento, td.nombre, pa.numeroDocumento, ts.nombre, pa.idSolicitudAfp, pa.fecSolicitud, pa.primerNombre, pa.segundoNombre, pa.primerApellido, pa.segundoApellido, pa.codTipoSolicitudAfp, ge.abreviaturaTipoIdAfp) FROM SnrTmpPendienteAfiliado pa LEFT JOIN SnrTipoSolicitudAfp ts ON pa.codTipoSolicitudAfp = ts.codTipoSolicitudAfp"
			+ " JOIN GnrTiposDocumentos td ON td.id = pa.codTipoDocumento "
			+ " JOIN GnrEquivalenciaTipos ge ON ge.codTipoIdentificacion = pa.codTipoDocumento "
			+ "where pa.codTipoDocumento = :tipoDoc and pa.numeroDocumento = :documento")
	public List<ProcesarPendientesDTO> consultarPendientePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
	
	@Transactional
	@Modifying
	@Query("delete from SnrTmpPendienteAfiliado pa where pa.codTipoDocumento = :tipoDoc and pa.numeroDocumento = :documento")
	public void eliminarPendientePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
		
}

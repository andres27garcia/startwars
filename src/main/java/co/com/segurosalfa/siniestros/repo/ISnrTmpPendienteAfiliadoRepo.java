package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrTmpPendienteAfiliado;

public interface ISnrTmpPendienteAfiliadoRepo extends IGenericRepo<SnrTmpPendienteAfiliado, Long>{
		
						
	@Query("select new co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO(pa.idPendienteAfiliado, pa.codTipoDocumento, pa.numeroDocumento, ts.nombre, pa.idSolicitudAfp, pa.fecSolicitud, pa.codTipoSolicitudAfp) "
			+ " FROM SnrTmpPendienteAfiliado pa LEFT JOIN SnrTipo ts ON pa.codTipoSolicitudAfp = ts.codigo WHERE ts.clase = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SOLICITUD_AFP "
			+ "order by pa.idPendienteAfiliado asc")
	public List<ProcesarPendientesDTO> listarPendientesAfiliados();
	
	@Query("select new co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO(pa.idPendienteAfiliado, pa.codTipoDocumento,  pa.numeroDocumento, ts.nombre, pa.idSolicitudAfp, pa.fecSolicitud, "
			+ "pa.primerNombre, pa.segundoNombre, pa.primerApellido, pa.segundoApellido, pa.codTipoSolicitudAfp) "
			+ "FROM SnrTmpPendienteAfiliado pa LEFT JOIN SnrTipo ts ON pa.codTipoSolicitudAfp = ts.codigo "
			+ "where pa.codTipoDocumento = :tipoDoc and pa.numeroDocumento = :documento and ts.clase = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SOLICITUD_AFP ")
	public List<ProcesarPendientesDTO> consultarPendientePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
	
	@Transactional
	@Modifying
	@Query("delete from SnrTmpPendienteAfiliado pa where pa.codTipoDocumento = :tipoDoc and pa.numeroDocumento = :documento")
	public void eliminarPendientePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
		
}

package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrTmpPendienteAfiliado;

public interface ISnrTmpPendienteAfiliadoRepo extends IGenericRepo<SnrTmpPendienteAfiliado, Long>{
		
						
	@Query("select new co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO(pa.idPendienteAfiliado, pa.codTipoDocumento, pa.numeroDocumento, ts.nombre, "
			+ " pa.idSolicitudAfp, pa.fecSolicitud, pa.codTipoSolicitudAfp, ste.abreviaturaTipoIdAfp, st.nombre) "
			+ " FROM SnrTmpPendienteAfiliado pa LEFT JOIN SnrTipo ts ON pa.codTipoSolicitudAfp = ts.codigo "
			+ " LEFT JOIN SnrTipo st ON st.codigo  = pa.codTipoDocumento "
			+ " LEFT JOIN SnrTipoEquivalencia ste ON ste.codEquivalencia = pa.codTipoDocumento"
			+ " WHERE ts.clase = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SOLICITUD_AFP "
			+ " AND st.clase = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SNR_TIPO_IDENTIF "
			+ " AND ste.tipo = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SNR_TIPO_EQUIV_T_IDENT "
			+ "order by pa.idPendienteAfiliado asc")
	public List<ProcesarPendientesDTO> listarPendientesAfiliados();
	
	@Query("select new co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO(pa.idPendienteAfiliado, pa.codTipoDocumento,  pa.numeroDocumento, ts.nombre, pa.idSolicitudAfp, pa.fecSolicitud, "
			+ "pa.primerNombre, pa.segundoNombre, pa.primerApellido, pa.segundoApellido, pa.codTipoSolicitudAfp, ste.abreviaturaTipoIdAfp, st.nombre) "
			+ "FROM SnrTmpPendienteAfiliado pa "
			+ "LEFT JOIN SnrTipo ts ON pa.codTipoSolicitudAfp = ts.codigo "
			+ "LEFT JOIN SnrTipo st ON st.codigo  = pa.codTipoDocumento "
			+ "LEFT JOIN SnrTipoEquivalencia ste ON ste.codEquivalencia = pa.codTipoDocumento "
			+ "WHERE pa.codTipoDocumento = :tipoDoc and pa.numeroDocumento = :documento "
			+ "AND ts.clase = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SOLICITUD_AFP "
			+ "AND st.clase = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SNR_TIPO_IDENTIF "
			+ "AND ste.tipo = co.com.sipren.common.util.ParametroGeneralUtil.GRAL_SNR_TIPO_EQUIV_T_IDENT ")
	public List<ProcesarPendientesDTO> consultarPendientePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
	
	@Transactional
	@Modifying
	@Query("delete from SnrTmpPendienteAfiliado pa where pa.codTipoDocumento = :tipoDoc and pa.numeroDocumento = :documento")
	public void eliminarPendientePorCedula(@Param("tipoDoc") Integer tipoDoc, @Param("documento") Long documento);
		
}

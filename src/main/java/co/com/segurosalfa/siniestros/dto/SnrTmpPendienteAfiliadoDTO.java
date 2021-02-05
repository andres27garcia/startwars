package co.com.segurosalfa.siniestros.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrTmpPendienteAfiliadoDTO {

	private Integer codTipoDocumento;
	private Long numeroDocumento;
	private Integer codTipoSolicitudAfp;
	private Integer idSolicitudAfp;
	private Date fecSolicitud;	

}

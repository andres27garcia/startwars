package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrTmpPendienteReclamanteDTO {

	private Integer codTipoIdentificacion;
	private Long nidNumeroOdentificacion;
	private Long idSiniestro;
	private Long idTramite;
}

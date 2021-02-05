package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrTmpPendInfoAdicionalDTO {

	private Integer codTipoIdentificacion;
	private Long nidNumeroIdentificacion;
	private String tipoRolPersona;
	private Long idSiniestro;
	private Long idTramite;
	private Long numPersonaAfiliado;	

}

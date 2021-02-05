package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiltroSiniestrosDTO {

	private Long idSiniestro;
	private Long numPersona;
	private Integer numPoliza;
	private Integer numIdentificacion;
	private Integer origen;
	private Integer estado;
	private String fecSiniestroInicial;
	private String fecSiniestroFinal;

}

package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrCargueArchivosDTO {

	private Integer id;
	private String tipoCargue;
	private String idTipoCargue;
	private String usuario;
	private String estado;
	private String avance;
	private String subTipoCargue;
	private LocalDateTime fecEjecucion;

}

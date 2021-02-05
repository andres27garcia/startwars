package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrComentarioTramiteDTO {

	private Long idComentario;
	private LocalDate fecComentario;
	private String usuarioComentario;
	private String descripcionComentario;
	private SnrDatoTramiteDTO tramite;

}

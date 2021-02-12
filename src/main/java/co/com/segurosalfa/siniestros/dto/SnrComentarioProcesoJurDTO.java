package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrComentarioProcesoJurDTO {

	private Long idComentarioJur;
	private SnrProcesoJuridicoDTO procesoJuridico;
	private LocalDate fecComentario;
	private String usuarioComentario;
	private String descripcionComentario;

}
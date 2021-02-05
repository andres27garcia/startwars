package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrComentarioProcesoJurDTO {

	private Integer idComentarioJur;
	private SnrProcesoJuridicoDTO procesoJuridico;
	private LocalDateTime fecComentario;
	private String usuarioComentario;
	private String descripcionComentario;

}
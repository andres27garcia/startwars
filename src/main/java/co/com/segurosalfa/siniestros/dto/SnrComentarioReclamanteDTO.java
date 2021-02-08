package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrComentarioReclamanteDTO implements Serializable {

	private static final long serialVersionUID = 6035131253468059707L;

	private Long idComentario;
	private SnrDatoReclamanteDTO reclamante;
	private LocalDateTime fecComentario;
	@Size(max = 20, message = "El campo USUARIO_COMENTARIO no soporta mas de 20 caracteres")
	private String usuarioComentario;
	@Size(max = 1000, message = "El campo DESCRIPCION_COMENTARIO no soporta mas de 1000 caracteres")
	private String descripcionComentario;

}

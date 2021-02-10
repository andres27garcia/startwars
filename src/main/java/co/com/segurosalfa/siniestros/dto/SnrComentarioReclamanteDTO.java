package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.Valid;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrComentarioReclamanteDTO implements Serializable {

	private static final long serialVersionUID = 6035131253468059707L;

	private Long idComentario;
	@Valid
	@NotNull
	private SnrDatoReclamanteDTO reclamante;
	private LocalDate fecComentario;
	private String usuarioComentario;
	private String descripcionComentario;

}

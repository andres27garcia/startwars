package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrHilAportanteDTO implements Serializable {

	private static final long serialVersionUID = -6095779731639659803L;

	private Integer idAportante;
	private Long tipoIdentificacion;
	private Integer nidNumeroDocumento;
	private String nombreAportante;

}
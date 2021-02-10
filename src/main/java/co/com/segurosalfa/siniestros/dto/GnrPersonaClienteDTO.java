package co.com.segurosalfa.siniestros.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GnrPersonaClienteDTO {

	@NotNull(message = "El campo numPersona es obligatorio")
	private Long numPersona;
	private GnrTipoDocumentoDTO tipoDocumento;
	private Integer numIdentificacion;
}

package co.com.segurosalfa.siniestros.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoReclamanteDTO{

	@NotNull(message = "El campo id reclamante es obligatorio")
	private Long idReclamante;
	private SnrDatoTramiteDTO tramite;
	private GnrPersonaClienteDTO persona;
	private SnrEstadoDTO estadoReclamante;

}
package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoReclamanteDTO{

	private Long idReclamante;
	private SnrDatoTramiteDTO tramite;
	private GnrPersonaClienteDTO persona;
	private SnrEstadoDTO estadoReclamante;

}
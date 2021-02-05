package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoReclamanteDTO{

	private Long id;
	private SnrDatoTramiteDTO tramite;
	private Long persona;
	private SnrEstadoDTO estadoReclamante;

}
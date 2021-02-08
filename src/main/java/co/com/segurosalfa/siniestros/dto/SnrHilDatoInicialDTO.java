package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrHilDatoInicialDTO {

	private Integer idDetalleHil;
	private Long persona;
	private Integer peridoCotizacion;
	private LocalDateTime fecInicialCot;
	private LocalDateTime fecFinalCot;
	private SnrHilAportanteDTO aportante;
	private Integer vlrSalario;
	private Integer numDiasCot;
	private String blnMensualizado;
	private SnrHilOrigenHistoriaDTO origenesHil;
	private String blnRegistroInvalido;

}
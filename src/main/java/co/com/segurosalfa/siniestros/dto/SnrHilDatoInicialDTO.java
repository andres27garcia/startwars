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
	private HilAportantesDTO aportante;
	private Integer vlrSalario;
	private Integer numDiasCot;
	private String blnMensualizado;
	private HilOrigenesHistoriasDTO origenesHil;
	private String blnRegistroInvalido;

}
package co.com.segurosalfa.siniestros.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrHilDatoInicialDTO {

	private Long idDetalleHil;
	@NotNull(message = "El campo persona no puede ser nulo o vacio")
	private Long persona;
	private Integer peridoCotizacion;
	private LocalDate fecInicialCot;
	private LocalDate fecFinalCot;
	@NotNull(message = "El campo aportante no puede ser nulo o vacio")
	private SnrHilAportanteDTO aportante;
	@NotNull(message = "El campo vlrSalario no puede ser nulo o vacio")
	private BigDecimal vlrSalario;
	@NotNull(message = "El campo numDiasCot no puede ser nulo o vacio")
	private Integer numDiasCot;
	@Size(max = 1, message = "El campo blnMensualizado no soporta mas de 1 caracter")
	@NotNull(message = "El campo blnMensualizado no puede ser nulo o vacio")
	private String blnMensualizado;
	@NotNull(message = "El campo origenesHil no puede ser nulo o vacio")
	private SnrHilOrigenHistoriaDTO origenesHil;
	@Size(max = 1, message = "El campo blnRegistroInvalido no soporta mas de 1 caracter")
	private String blnRegistroInvalido;

}
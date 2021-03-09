package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiltroSiniestrosDTO {

	private FiltroGenericoDTO siniestro;
	private FiltroGenericoDTO persona;
	private FiltroGenericoDTO poliza;
	private FiltroGenericoDTO identificacion;
	private Integer origen;
	private Integer estado;
	private LocalDate fecSiniestroInicial;
	private LocalDate fecSiniestroFinal;

}

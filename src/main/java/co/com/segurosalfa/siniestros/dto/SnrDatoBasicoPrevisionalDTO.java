package co.com.segurosalfa.siniestros.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoBasicoPrevisionalDTO {

	private Integer id;
	private SnrDatoBasicoDTO siniestro;
	private Integer numCalifVinc;
	private Integer numProcJurVinc;
	private Integer vlrBonoPensional;
	private Long vlrCitaIndividual;
	private Integer vlrBonoPenCtaInd;
	private Integer vlrUltIngBaseCot;
	private Integer ultPeriodoCot;
	private Integer ultPeriodoRec;
	private Integer numTramiteIncVinc;
	private Integer numCalcVinc;
	private Integer numReservaVinc;
	private Integer numPagoVinc;
	private String causaOrigen;
	private BigDecimal vlrSalarioBase;
	private String blnCotizaba;
	private ClienteUnicoDTO clienteUnico;

}
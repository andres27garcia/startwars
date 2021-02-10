package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoBasicoDTO  {

	private Long idSiniestro;
	private GnrPersonaClienteDTO persona;
	private Integer numPoliza;
	private SnrOrigenDTO origen;
	private SnrEstadoDTO estado;
	private LocalDate fecSiniestro;
	private SnrCausaSiniestroDTO causaSiniestro;
	private Integer numCalifVinculacion;
	private Integer numProcJurVinc;
	private Integer vlrBonoPensional;
	private Integer vlrCitaIndividual;
	private Integer vlrBonoPenCtaInd;
	private Integer vlrUltIngBaseCot;
	private Integer ultPeriodoCot;
	private Integer ultPeriodoRec;
	private Integer numTramiteIncVinc;
	private Integer numCalcVinc;
	private Integer numReservaVinc;
	private Integer numPagoVinc;
	private ClienteUnicoDTO clienteUnico;

}

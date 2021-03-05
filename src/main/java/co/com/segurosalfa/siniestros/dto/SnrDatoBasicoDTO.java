package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoBasicoDTO  {

	private Long idSiniestro;
	private GnrPersonaClienteDTO persona;
	@NotNull(message = "El campo numPoliza puede ser nulo o vacio")
	private Integer numPoliza;
	private SnrOrigenDTO origen;
	@NotNull(message = "El campo estado no puede ser nulo o vacio")
	private SnrEstadoDTO estado;
	private LocalDate fecSiniestro;
	private SnrCausaSiniestroDTO causaSiniestro;
	private Integer numCalifVinculacion;
	@NotNull(message = "El campo NUM_PROC_JUR_VINC no puede ser nulo o vacio")
	private Integer numProcJurVinc;
	@NotNull(message = "El campo VLR_BONO_PENSIONAL no puede ser nulo o vacio")
	private Integer vlrBonoPensional;
	@NotNull(message = "El campo VLR_CTA_INDIVIDUAL no puede ser nulo o vacio")
	private Integer vlrCitaIndividual;
	@NotNull(message = "El campo VLR_BONO_PEN_CTA_IND no puede ser nulo o vacio")
	private Integer vlrBonoPenCtaInd;
	@NotNull(message = "El campo VLR_ULT_ING_BASE_COT no puede ser nulo o vacio")
	private Integer vlrUltIngBaseCot;
	private Integer ultPeriodoCot;
	private Integer ultPeriodoRec;
	private Integer numTramiteIncVinc;
	private Integer numCalcVinc;
	private Integer numReservaVinc;
	private Integer numPagoVinc;
	private ClienteUnicoDTO clienteUnico;

}

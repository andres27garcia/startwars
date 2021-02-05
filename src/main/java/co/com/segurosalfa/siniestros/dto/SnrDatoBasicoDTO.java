package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoBasicoDTO implements Serializable {

	private static final long serialVersionUID = -4287044578831976985L;
	private Long idSiniestro;
	private Long persona;
	private Integer numPoliza;
	private ListadoDTO origen;
	private ListadoDTO estado;
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

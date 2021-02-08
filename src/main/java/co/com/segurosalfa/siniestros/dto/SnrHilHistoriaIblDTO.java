package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrHilHistoriaIblDTO implements Serializable {

	private static final long serialVersionUID = 5001281684268955019L;

	private Long idHistoriaHil;
	private Long persona;
	private LocalDateTime fecInicialCot;
	private LocalDateTime fecFinalCot;
	private Long vlrSalario;
	private Integer numDiasTrabajados;
	private Integer numSemanas;
	private Integer vlrFactor;
	private SnrHilOrigenHistoriaDTO origenesHil;

}

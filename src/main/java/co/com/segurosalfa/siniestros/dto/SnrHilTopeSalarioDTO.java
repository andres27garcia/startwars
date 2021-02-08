package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrHilTopeSalarioDTO implements Serializable {

	private static final long serialVersionUID = -4679106343264545148L;
	private Long idTopeSalario;
	private LocalDate fecVigenciaInicial;
	private LocalDate fecVigenciaFinal;
	private Long numSalarioMinimo;

}

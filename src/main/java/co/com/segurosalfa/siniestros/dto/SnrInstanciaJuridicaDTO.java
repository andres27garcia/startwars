package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrInstanciaJuridicaDTO {

	private SnrInstanciaJuridicaIdDTO id;
	private SnrTipoDTO fallo;
	private LocalDate fecFallo;
	

}

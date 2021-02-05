package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrInstanciaJuridicaIdDTO {

	private SnrProcesoJuridicoDTO procesoJuridico;
	private SnrTipoDTO instanciaJuridica;
}

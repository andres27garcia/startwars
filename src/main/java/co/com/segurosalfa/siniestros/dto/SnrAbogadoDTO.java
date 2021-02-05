package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SnrAbogadoDTO {

	private Integer idAbogado;
	private Integer tipoDocumento;
	private Integer numIdAbogado;
	private String nombreAbogado;

}
package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CuEpsDTO {

	private Integer idEps;
	private Integer nidIdentificacion;
	private Integer nidConsecutivo;
	private String codSuperBancaria;
	private String blnIss;
	private String codEpsMps;
	private String codEstado;
	private String codRegimen;
	private String razonSocial;
}

package co.com.segurosalfa.siniestros.dto;

import lombok.Data;

@Data
public class ComparacionPersonaDTO {

	private String descripcion;
	private String clienteUnico;
	private String afp;
	private boolean esDiferente;

}

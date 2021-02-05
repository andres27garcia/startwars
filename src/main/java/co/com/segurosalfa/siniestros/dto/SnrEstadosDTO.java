package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrEstadosDTO {

	private int id;
	private String codigo;
	private String clase;
	private String nombre;

}
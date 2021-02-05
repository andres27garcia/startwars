package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrEstadoDTO {

	private int id;
	private String codigo;
	private String clase;
	private String nombre;

	public SnrEstadoDTO(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

}
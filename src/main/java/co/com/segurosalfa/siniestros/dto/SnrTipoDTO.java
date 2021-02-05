package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrTipoDTO {

	private int id;
	private Integer codigo;
	private String clase;
	private String nombre;

	public SnrTipoDTO(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

}
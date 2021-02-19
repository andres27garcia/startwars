package co.com.segurosalfa.siniestros.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrEstadoDTO {

	private int id;
	@NotNull(message = "El campo CODIGO no puede ser nulo o vacio")
	private String codigo;
	@NotNull(message = "El campo CLASE no puede ser nulo o vacio")
	private String tipo;
	@NotNull(message = "El campo NOMBRE no puede ser nulo o vacio")
	private String nombre;
	private String descripcion;

	public SnrEstadoDTO(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

}
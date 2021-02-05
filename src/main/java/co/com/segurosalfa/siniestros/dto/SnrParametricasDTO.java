package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrParametricasDTO {

	private Integer nidParametrica;
	private String nombre;
	private String descripcion;
	private String valor;
	private String proyecto;

}
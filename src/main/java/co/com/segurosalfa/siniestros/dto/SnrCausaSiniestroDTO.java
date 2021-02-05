package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrCausaSiniestroDTO {

	private Integer id;
	private Integer codCausaSiniestroFasecolda;
	private String nombre;

	public SnrCausaSiniestroDTO(Integer id, Integer codCausaSiniestroFasecolda, String nombre) {
		super();
		this.id = id;
		this.codCausaSiniestroFasecolda = codCausaSiniestroFasecolda;
		this.nombre = nombre;
	}

}
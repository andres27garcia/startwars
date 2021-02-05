package co.com.segurosalfa.siniestros.dto;

import lombok.Data;

@Data
public class ListadoReclamantesDTO {

	private Long idReclamnte;
	private Long idtramite;
	private Long persona;
	private String nombres;
	private String estadoReclamante;
	private Integer codEstadoReclamante;
	private String codInv;

}

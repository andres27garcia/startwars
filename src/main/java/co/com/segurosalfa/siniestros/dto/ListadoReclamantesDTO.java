package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ListadoReclamantesDTO implements Serializable {

	private static final long serialVersionUID = 1760141477151804824L;
	private Long idReclamnte;
	private Long idtramite;
	private Long numPersona;
	private String nombres;
	private String estadoReclamante;
	private Integer codEstadoReclamante;
	private String codInv;

}

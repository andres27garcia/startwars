package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReprocesoReclamantesDTO implements Serializable {

	private static final long serialVersionUID = -7761191201631071064L;
	private String tipoDocAfiliado;
	private Integer documentoAfil;
	private Long idTramite;
	private String tipoDocReclamante;
	private Integer documentoReclamante;
	private String nombreReclamante;
	private Long numPersonaReclamante;	
	private Long numPersonaAfiliado;

	public ReprocesoReclamantesDTO(Long numPersonaAfiLiado, Long idTramite,			
			Long numPersonaReclamante) {
		super();
		this.numPersonaAfiliado = numPersonaAfiLiado;
		this.idTramite = idTramite;
		this.numPersonaReclamante = numPersonaReclamante;
	}

	public void setNombreReclamante(ClienteUnicoDTO cu) {

		StringBuilder sb = new StringBuilder();

		sb.append(cu.getPrimerNombre()).append(" ");
		sb.append(cu.getSegundoNombre()).append(" ");
		sb.append(cu.getPrimerApell()).append(" ");
		sb.append(cu.getSegundoApell());

		this.nombreReclamante = sb.toString();
	}

}

package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

public class ReprocesoReclamantesDTO implements Serializable {

	private static final long serialVersionUID = -7761191201631071064L;
	private String tipoDocAfiliado;
	private Integer documentoAfil;
	private Long idTramite;
	private String tipoDocReclamante;
	private Integer documentoReclamante;
	private String nombreReclamante;
	private Long numPersonaReclamante;

	public ReprocesoReclamantesDTO(String tipoDocAfiliado, Integer documentoAfil, Long idTramite,
			String tipoDocReclamante, Integer documentoReclamante, Long numPersonaReclamante) {
		super();
		this.tipoDocAfiliado = tipoDocAfiliado;
		this.documentoAfil = documentoAfil;
		this.idTramite = idTramite;
		this.tipoDocReclamante = tipoDocReclamante;
		this.documentoReclamante = documentoReclamante;
		this.numPersonaReclamante = numPersonaReclamante;
	}

	public String getTipoDocAfiliado() {
		return tipoDocAfiliado;
	}

	public void setTipoDocAfiliado(String tipoDocAfiliado) {
		this.tipoDocAfiliado = tipoDocAfiliado;
	}

	public Integer getDocumentoAfil() {
		return documentoAfil;
	}

	public void setDocumentoAfil(Integer documentoAfil) {
		this.documentoAfil = documentoAfil;
	}

	public Long getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}

	public String getTipoDocReclamante() {
		return tipoDocReclamante;
	}

	public void setTipoDocReclamante(String tipoDocReclamante) {
		this.tipoDocReclamante = tipoDocReclamante;
	}

	public Integer getDocumentoReclamante() {
		return documentoReclamante;
	}

	public void setDocumentoReclamante(Integer documentoReclamante) {
		this.documentoReclamante = documentoReclamante;
	}

	public String getNombreReclamante() {
		return nombreReclamante;
	}

	public void setNombreReclamante(ClienteUnicoDTO cu) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(cu.getPrimerNombre()).append(" ");
		sb.append(cu.getSegundoNombre()).append(" ");
		sb.append(cu.getPrimerApell()).append(" ");
		sb.append(cu.getSegundoApell());
		
		this.nombreReclamante = sb.toString();
	}

	public Long getNumPersonaReclamante() {
		return numPersonaReclamante;
	}

	public void setNumPersonaReclamante(Long numPersonaReclamante) {
		this.numPersonaReclamante = numPersonaReclamante;
	}

}

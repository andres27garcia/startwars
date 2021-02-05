package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CargueSiniestrosDTO implements Serializable {

	private static final long serialVersionUID = 8816545179972987616L;

	private String estadoRegistro;
	private String detalleError;
	private String numeroRegistro;
	@Size(min = 1, message = "se requiere campo tipoDocumento")
	private String tipoDocumento;
	@Size(min = 5, message = "se requiere campo documento")
	private String documento;
	private String tipoSolicitud;
	private String fechaInicioConsulta;

	public CargueSiniestrosDTO(String estadoRegistro, String detalleError, String numeroRegistro, String tipoDocumento,
			String documento, String tipoSolicitud, String fechaInicioConsulta) {
		super();
		this.estadoRegistro = estadoRegistro;

		if (detalleError.equalsIgnoreCase("null"))
			detalleError = "";

		this.detalleError = detalleError;
		this.numeroRegistro = numeroRegistro;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.tipoSolicitud = tipoSolicitud;
		this.fechaInicioConsulta = fechaInicioConsulta;
	}

	public CargueSiniestrosDTO() {
		super();
	}

	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public String getDetalleError() {
		return detalleError;
	}

	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getFechaInicioConsulta() {
		return fechaInicioConsulta;
	}

	public void setFechaInicioConsulta(String fechaInicioConsulta) {
		this.fechaInicioConsulta = fechaInicioConsulta;
	}

}

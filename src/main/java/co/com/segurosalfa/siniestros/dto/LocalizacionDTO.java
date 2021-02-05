package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LocalizacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codTipoLocalizacion;
	private String tipoLocalizacion;
	private String email;
	private String direccion;
	private String numeroTelefono;
	private Long numeroCelular;
	private String blnEnvioCorrespondencia;
	private Integer codTipoTelefono;
	private String codPais;
	private String codCiudad;
	private String codDepartamento;
	private String blnDireccionConfirmada;

}
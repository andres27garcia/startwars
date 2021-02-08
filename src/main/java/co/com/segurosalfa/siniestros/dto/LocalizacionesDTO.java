package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LocalizacionesDTO implements Serializable {

	private static final long serialVersionUID = 6162493568064562797L;

	private String numTelefonico;
	private String direccion;
	private String codCiudad;
	private String codDepartamento;

}

package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "gnr_parametricas")
public class SnrParametrica implements Serializable {

	private static final long serialVersionUID = -2538217260641616473L;

	@Id
	@Column(name = "ID_PARAMETRICA")
	private Integer nidParametrica;

	@Size(max = 50, message = "El campo NOMBRE no soporta mas de 50 caracteres")
	@Column(name = "NOMBRE")
	private String nombre;

	@Size(max = 200, message = "El campo DESCRIPCION no soporta mas de 200 caracteres")
	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Size(max = 500, message = "El campo VALOR no soporta mas de 500 caracteres")
	@Column(name = "VALOR")
	private String valor;

	@Size(max = 50, message = "El campo PROYECTO no soporta mas de 50 caracteres")
	@Column(name = "PROYECTO")
	private String proyecto;

}
package co.com.segurosalfa.siniestros.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "SNR_ESTADO")
@Entity
@NoArgsConstructor
public class SnrEstado {

	@Id
	@NotNull(message = "El campo ID no puede ser nulo o vacio")
	@Column(name = "ID_ESTADO", precision = 3, scale = 0)
	private int id;
	@NotNull(message = "El campo CODIGO no puede ser nulo o vacio")
	@Column(name = "CODIGO", length = 50, nullable = false)
	private String codigo;
	@NotNull(message = "El campo CLASE no puede ser nulo o vacio")
	@Column(name = "TIPO", length = 50, nullable = false)
	private String tipo;
	@NotNull(message = "El campo NOMBRE no puede ser nulo o vacio")
	@Column(name = "NOMBRE", length = 50, nullable = false)
	private String nombre;
	@Column(name = "DESCRIPCION")
	private String descripcion;

}
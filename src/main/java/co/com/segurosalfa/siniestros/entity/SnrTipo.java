package co.com.segurosalfa.siniestros.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "SNR_TIPO")
@Entity
@NoArgsConstructor
public class SnrTipo {

	@Id
	@NotNull(message = "El campo ID no puede ser nulo o vacio")
	@Column(name = "ID_TIPO")
	private int id;
	@NotNull(message = "El campo NOMBRE no puede ser nulo o vacio")
	@Column(name = "CODIGO", length = 50, nullable = false)
	private Integer codigo;
	@NotNull(message = "El campo NOMBRE no puede ser nulo o vacio")
	@Column(name = "CLASE", length = 50, nullable = false)
	private String clase;
	@NotNull(message = "El campo NOMBRE no puede ser nulo o vacio")
	@Column(name = "NOMBRE", length = 50, nullable = false)
	private String nombre;
	
}
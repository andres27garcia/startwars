package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_ORIGEN")
public class SnrOrigen implements Serializable {

	private static final long serialVersionUID = 7968346985237802657L;

	@Id
	@Column(name = "COD_ORIGEN_SINIESTRO")
	private Integer id;

	@NotNull(message = "El campo NOMBRE no puede ser nulo o vacio")
	@Size(max = 50, message = "El campo NOMBRE no soporta mas de 50 caracteres")
	@Column(name = "NOMBRE")
	private String nombre;

}
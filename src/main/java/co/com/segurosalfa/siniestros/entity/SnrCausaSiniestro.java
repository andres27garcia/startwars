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
@Table(name = "SNR_CAUSA_SINIESTRO")
public class SnrCausaSiniestro implements Serializable {

	private static final long serialVersionUID = 9050018921401069289L;

	@Id
	@Column(name = "COD_CAUSA_SINIESTRO")
	private Integer id;

	@Column(name = "COD_CAUSA_SINIESTRO_FASECOLDA")
	private Integer codCausaSiniestroFasecolda;

	@Size(max = 50, message = "El campo NOMBRE no soporta mas de 50 caracteres")
	@Column(name = "NOMBRE")
	private String nombre;

}
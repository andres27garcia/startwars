package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_INSTANCIA_JURIDICA")
public class SnrInstanciaJuridica implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SnrInstanciaJuridicaId id;
	@NotNull(message = "El campo ID_TIPO_FALLO no puede ser nulo o vacio")
	@JoinColumn(name = "ID_TIPO_FALLO")
	private SnrTipo fallo;
	@NotNull(message = "El campo FECHA_FALLO no puede ser nulo o vacio")
	@Column(name = "FECHA_FALLO")
	private LocalDate fecFallo;

}

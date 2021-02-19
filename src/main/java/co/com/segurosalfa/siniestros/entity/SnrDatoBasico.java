package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_DATO_BASICO")
public class SnrDatoBasico implements Serializable  {

	private static final long serialVersionUID = 5961287001133777541L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID_SINIESTRO")
	private Long idSiniestro;

	@Column(name = "NUM_PERSONA")
	private Long persona;

	@ManyToOne
	@JoinColumn(name = "COD_ORIGEN_SINIESTRO")
	private SnrOrigen origen;
	
	@NotNull(message = "El campo FECHA_SINIESTRO no puede ser nulo o vacio")
	@Column(name = "FECHA_SINIESTRO")
	private LocalDate fecSiniestro;
	
	@ManyToOne
	@JoinColumn(name = "COD_CAUSA_SINIESTRO")
	private SnrCausaSiniestro causaSiniestro;

	@ManyToOne
	@JoinColumn(name = "ID_ESTADO_SINIESTRO")
	private SnrEstado estado;
	
	@ManyToOne
	@JoinColumn(name = "COD_RAMO")
	private SnrRamo ramo;

	@Column(name = "NUM_POLIZA", scale = 2, precision = 0)
	private Integer numPoliza;

}
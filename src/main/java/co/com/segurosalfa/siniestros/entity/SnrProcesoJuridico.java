package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "SNR_PROCESO_JURIDICO")
public class SnrProcesoJuridico implements Serializable {

	private static final long serialVersionUID = 6172614970633251423L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID_PROCESO_JURIDICO")
	private Integer idProcesoJuridico;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_PROCESO_JUR")
	private SnrTipo tipoProcesoJuridico;

	@ManyToOne
	@JoinColumn(name = "ID_TRAMITE")
	private SnrDatoTramite tramite;

	@NotNull(message = "El campo FECHA_PROCESO_JURIDICO no puede ser nulo o vacio")
	@Column(name = "FECHA_PROCESO_JURIDICO")
	private LocalDate fecProcesoJuridico;

	@NotNull(message = "El campo NOMBRE_ACTOR no puede ser nulo o vacio")
	@Column(name = "NOMBRE_ACTOR")
	private String nombreActor;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_PRETENSION")
	private SnrTipo pretencion;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CONTINGENCIA")
	private SnrTipo contingencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CALIFICACION_CLASE")
	private SnrTipo clasificacionClase;

	@ManyToOne
	@JoinColumn(name = "ID_ESTADO_PROCESO_JUR")
	private SnrEstado estadoJuridico;

	@ManyToOne
	@JoinColumn(name = "ID_ABOGADO")
	private SnrAbogado idAbogado;
	
	@Column(name = "BLN_CONFLICTO_BENEFICIARIOS")
	private String blnConflicBeneficiarios;
	
	@Column(name = "BLN_FIDELIDAD")
	private String blnFidelidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_JUZGADO")
	private SnrTipo juzgado;

}
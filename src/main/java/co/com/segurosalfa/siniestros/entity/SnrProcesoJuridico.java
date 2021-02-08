package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_PROCESO_JURIDICO")
public class SnrProcesoJuridico implements Serializable {

	private static final long serialVersionUID = 6172614970633251423L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROCESO_JURIDICO")
	private Integer idProcesoJuridico;

	@ManyToOne
	@JoinColumn(name = "COD_TIPO_PROCESO_JUR", insertable = false, updatable = false, nullable = false)
	private SnrTipo tipoProcesoJuridico;

	@ManyToOne
	@JoinColumn(name = "ID_TRAMITE", insertable = false, updatable = false, nullable = false)
	private SnrDatoTramite tramite;

	@NotNull(message = "El campo FECHA_PROCESO_JURIDICO no puede ser nulo o vacio")
	@Column(name = "FECHA_PROCESO_JURIDICO")
	private LocalDateTime fecProcesoJuridico;

	@NotNull(message = "El campo NOMBRE_ACTOR no puede ser nulo o vacio")
	@Column(name = "NOMBRE_ACTOR")
	private String nombreActor;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_PRETENSION", insertable = false, updatable = false, nullable = false)
	private SnrTipo pretencion;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CONTINGENCIA", insertable = false, updatable = false, nullable = false)
	private SnrTipo contingencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CALIFICACION_CLASE", insertable = false, updatable = false, nullable = false)
	private SnrTipo clasificacionClase;

	@ManyToOne
	@JoinColumn(name = "ID_ESTADO_PROCESO_JUR", insertable = false, updatable = false, nullable = false)
	private SnrEstado estadoJuridico;

	@ManyToOne
	@JoinColumn(name = "ID_ABOGADO", insertable = false, updatable = false, nullable = false)
	private SnrAbogado idAbogado;
	
	@Column(name = "BLN_CONFLICTO_BENEFICIARIOS")
	private String blnConflicBeneficiarios;
	
	@Column(name = "BLN_FIDELIDAD")
	private String blnFidelidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_JUZGADO", insertable = false, updatable = false, nullable = false)
	private SnrTipo juzgado;

}
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
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_DATO_TRAMITE")
public class SnrDatoTramite implements Serializable  {

	private static final long serialVersionUID = -4146111320078878948L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID_TRAMITE")
	private Long idTramite;

	@ManyToOne
	@JoinColumn(name = "ID_SINIESTRO")
	private SnrDatoBasico siniestro;	

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_TRAMITE")
	private SnrTipo tipoTramite;

	@ManyToOne
	@JoinColumn(name = "ID_ESTADO_TRAMITE")
	private SnrEstado estadoTramite;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_CLASIF_JUR")
	private SnrTipo clasificacionJur;

	@NotNull(message = "El campo ID_SOLICITUD_AFP no puede ser nulo o vacio")
	@Column(name = "ID_SOLICITUD_AFP")
	private Integer idSolicitudAfp;
	
	@Size(max = 2, message = "El campo BLN_COT_FECHA_SNR no soporta mas de 20 caracteres")
	@Column(name = "BLN_COT_FECHA_SNR")
	private String blnCotFechaSnr;
	
	@NotNull(message = "El campo FECHA_RAD_AFP no puede ser nulo o vacio")
	@Column(name = "FECHA_RAD_AFP")
	private LocalDate fecRadicacionAfp;
	
	@Column(name = "FEC_ACT_SOLICITUD_AFP")
	private LocalDate fecActSolicitudAfp;
	
	@Size(max = 50, message = "El campo RESULTADO_SOLICITUD_AFP no soporta mas de 50 caracteres")
	@Column(name = "RESULTADO_SOLICITUD_AFP")
	private String resultadoSolicitudAfp;
	
	@Column(name = "FEC_RADICACION_ALFA")
	private LocalDate fecRadicacionAlfa;
	
	@Column(name = "FEC_PAGO")
	private LocalDate fecPago;

}
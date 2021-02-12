package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_RESULT_PRC_CREACION_SINIESTRO")
public class SnrResulPrcCreacionSiniestro implements Serializable {

	private static final long serialVersionUID = 252318993525881429L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqPrcAutoCreacSini")
	@SequenceGenerator(name = "idSeqPrcAutoCreacSini", sequenceName = "SEQ_GNR_RESULT_PRC_CREACION_SINIESTRO", schema = "NUEVO_SIPREN", allocationSize = 1)
	@Column(name = "ID_REGISTRO")
	private Integer idRegistro;

	@Column(name = "TIPO_IDENTIFICACION_AFIL")
	private String tipoIdent;

	@Column(name = "NRO_IDENTIFICACION_AFIL")
	private Long nroIdent;

	@Column(name = "PRIMER_APELLIDO_AFIL")
	private String primerApell;

	@Column(name = "SEGUNDO_APELLIDO_AFIL")
	private String segundoApell;

	@Column(name = "PRIMER_NOMBRE_AFIL")
	private String primerNombre;

	@Column(name = "SEGUNDO_NOMBRE_AFIL")
	private String segundoNombre;

	@Column(name = "NRO_SOLICITUD_AFP")
	private Integer nroSolicitud;

	@Column(name = "FEC_RADICACION_AFP")
	private String fecRadicAFP;

	@Column(name = "TIPO_SOLICITUD")
	private String tipoSolicitud;

	@Column(name = "PROCESADA")
	private String procesada;

	@Column(name = "COD_ESTADO")
	private String codEstado;

	@Column(name = "DESC_ESTADO")
	private String descEstado;

	@Column(name = "NRO_PERSONA_CLIENTE")
	private Long nroPersonaCliente;

	@Column(name = "NRO_SINIESTRO")
	private Long nroSiniestro;

	@Column(name = "NRO_TRAMITE_SINIESTRO")
	private Long nroTramiteSiniestro;

	@Column(name = "ORIGEN_SINIESTRO")
	private String origenSiniestro;

	@Column(name = "FEC_ACT_SOLICITUD_AFP")
	private LocalDate fecActSolicAfp;

	@Column(name = "RESULTADO_SOLICITUD_AFP")
	private String resultadoSolicAfp;

	@Column(name = "FLG_PROCESO")
	private String flgProceso;

}
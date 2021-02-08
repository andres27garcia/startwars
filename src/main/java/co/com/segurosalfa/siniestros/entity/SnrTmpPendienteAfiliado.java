package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_TMP_PENDIENTE_AFILIADO")
public class SnrTmpPendienteAfiliado implements Serializable {

	private static final long serialVersionUID = -2182708787467590931L;

	@Id
	@Column(name = "ID_PENDIENTE_AFILIADO", insertable = false, updatable = false)
	private Long idPendienteAfiliado;
	@Column(name = "COD_TIPO_IDENTIFICACION", insertable = false, updatable = false)
	private Integer codTipoDocumento;
	@Column(name = "NID_NUMERO_IDENTIFICACION", insertable = false, updatable = false)
	private Long numeroDocumento;
	@Column(name = "COD_TIPO_SOLICITUD_AFP", insertable = false, updatable = false)
	private Integer codTipoSolicitudAfp;
	@Column(name = "ID_SOLICITUD_AFP", insertable = false, updatable = false)
	private Integer idSolicitudAfp;
	@Column(name = "FEC_SOLICITUD", insertable = false, updatable = false)
	private LocalDate fecSolicitud;
	@Column(name = "PRIMER_NOMBRE_AFP", insertable = false, updatable = false)
	private String primerNombre;
	@Column(name = "SEGUNDO_NOMBRE_AFP", insertable = false, updatable = false)
	private String segundoNombre;
	@Column(name = "PRIMER_APELLIDO_AFP", insertable = false, updatable = false)
	private String primerApellido;
	@Column(name = "SEGUNDO_APELLIDO_AFP", insertable = false, updatable = false)
	private String segundoApellido;

}

package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_TMP_PEND_INFO_ADICIONAL")
public class SnrTmpPendInfoAdicional implements Serializable {

	private static final long serialVersionUID = -8532535451001413497L;

	@Id
	@Column(name = "ID_INFO_ADICIONAL", insertable = false, updatable = false)
	private Long idInfoAdicional;
	@Column(name = "COD_TIPO_IDENTIFICACION", insertable = false, updatable = false)
	private Integer codTipoIdentificacion;
	@Column(name = "NID_NUMERO_IDENTIFICACION", insertable = false, updatable = false)
	private Long nidNumeroIdentificacion;
	@Column(name = "TIPO_ROL_PERSONA", insertable = false, updatable = false)
	private String tipoRolPersona;
	@Column(name = "ID_SINIESTRO", insertable = false, updatable = false)
	private Long idSiniestro;
	@Column(name = "ID_TRAMITE", insertable = false, updatable = false)
	private Long idTramite;
	@Column(name = "FEC_NACIMIENTO_AFP", insertable = false, updatable = false)
	private Date fechaNacimiento;
	@Column(name = "GENERO_AFP", insertable = false, updatable = false)
	private String genero;
	@Column(name = "COD_ESTADO_CIVIL_AFP", insertable = false, updatable = false)
	private Integer codEstadoCivil;
	@Column(name = "COD_EPS_AFP", insertable = false, updatable = false)
	private Integer codEps;
	@Column(name = "FEC_MUERTE_AFP", insertable = false, updatable = false)
	private Date fechaMuerte;
	

}

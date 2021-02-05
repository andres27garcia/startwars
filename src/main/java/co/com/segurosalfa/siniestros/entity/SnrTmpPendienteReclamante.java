package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_TMP_PENDIENTE_RECLAMANTE")
public class SnrTmpPendienteReclamante implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PENDIENTE_RECLAMANTE")
	private Long idPendienteReclamante;
	@Column(name = "COD_TIPO_IDENTIFICACION", insertable = false, updatable = false)
	private Integer codTipoIdentificacion;
	@Column(name = "NID_NUMERO_IDENTIFICACION", insertable = false, updatable = false)
	private Long nidNumeroIdentificacion;
	@Column(name = "ID_SINIESTRO", insertable = false, updatable = false)
	private Long idSiniestro;
	@Column(name = "ID_TRAMITE", insertable = false, updatable = false)
	private Long idTramite;
	@Column(name = "PRIMER_NOMBRE_AFP", insertable = false, updatable = false)
	private String primerNombre;
	@Column(name = "SEGUNDO_NOMBRE_AFP", insertable = false, updatable = false)
	private String segundoNombre;
	@Column(name = "PRIMER_APELLIDO_AFP", insertable = false, updatable = false)
	private String primerApellido;
	@Column(name = "SEGUNDO_APELLIDO_AFP", insertable = false, updatable = false)
	private String segundoApellido;

}

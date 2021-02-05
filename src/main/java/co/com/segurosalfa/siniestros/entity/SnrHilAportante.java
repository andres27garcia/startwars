package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_HIL_APORTANTE")
public class SnrHilAportante implements Serializable {

	private static final long serialVersionUID = -4583317355721584895L;

	@Id
	@Column(name = "ID_APORTANTE")
	private Integer idAportante;

	@ManyToOne
	@Column(name = "COD_TIPO_IDENTIFICACION")
	private Long tipoIdentificacion;

	@Column(name = "NID_NUMERO_IDENTIFICACION")
	private Integer nidNumeroDocumento;

	@Size(max = 200, message = "El campo NOMBRE_APORTANTE no soporta mas de 200 caracteres")
	@Column(name = "NOMBRE_APORTANTE")
	private String nombreAportante;

}
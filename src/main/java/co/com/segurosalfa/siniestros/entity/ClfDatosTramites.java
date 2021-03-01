package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "CLF_DATOS_TRAMITES")
public class ClfDatosTramites implements Serializable {

	private static final long serialVersionUID = -2223604213097853050L;

	@Id
	@Column(name = "NUM_PERSONA")
	private Integer numPersona;

	@Column(name = "ID_TRAMITE")
	private Long idTramite;

	@Column(name = "COD_TIPO_TRAMITE")
	private Integer codTipoTramite;

}

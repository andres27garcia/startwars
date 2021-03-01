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
@Table(name = "CLF_DATOS_DICTAMENES")
public class ClfDatosDictamenes implements Serializable {

	private static final long serialVersionUID = 3153221350379361319L;

	@Id
	@Column(name = "ID_DICTAMEN")
	private Integer idDictamen;

	@Column(name = "ID_TRAMITE")
	private Long idTramite;

	@Column(name = "PCJ_PCL")
	private Double pcjPcl;

}

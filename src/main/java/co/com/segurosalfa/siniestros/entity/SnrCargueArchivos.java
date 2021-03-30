package co.com.segurosalfa.siniestros.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_CARGUE_ARCHIVO")
public class SnrCargueArchivos {

	@Id
	@Column(name = "ID_CARGUE_ARCHIVO")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;
	@Column(name = "TIPO_CARGUE")
	private String tipoCargue;
	@Column(name = "ID_TIPO_CARGUE")
	private String idTipoCargue;
	@Column(name = "USUARIO")
	private String usuario;
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "AVANCE")
	private String avance;
	@Column(name = "SUB_TIPO_CARGUE")
	private String subTipoCargue;
	@Column(name = "FEC_EJECUCION")
	private LocalDateTime fecEjecucion;

}

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
@Table(name = "SNR_TIPO_EQUIVALENCIA")
@Entity
public class SnrTipoEquivalencia implements Serializable{
	
	private static final long serialVersionUID = 2245104338646266830L;
	@Id
	@Column(name = "ID_EQUIVALENCIA")
	private Long idEquivalencia;
	@Column(name = "TIPO")
	private String tipo;
	@Column(name = "COD_EQUIVALENCIA")
	private String codEquivalencia;
	@Column(name = "COD_EQUIVALENCIA_AFP")
	private String codEquivalenciaAfp;
	@Column(name = "ABREVIATURA_TIPO_ID_AFP")
	private String abreviaturaTipoIdAfp;

}

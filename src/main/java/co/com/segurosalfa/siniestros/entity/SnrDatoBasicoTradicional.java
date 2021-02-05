package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_DATO_BASICO_TRADICIONAL")
public class SnrDatoBasicoTradicional implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID_DATO_BASICO_TRADICIONAL")
	private Long id;	
	@OneToOne
	@JoinColumn(name = "ID_SINIESTRO", insertable = false, updatable = false, nullable = false)
	private SnrDatoBasico siniestro;
	@Column(name = "LUGAR_SINIESTRO")
	private String lugarSiniestro;
	@Column(name = "NUM_SINIESTRO")
	private Long numSiniestro;
	@Column(name = "VLR_AVISO")
	private BigDecimal vlrAviso;
	@Column(name = "AMPARO_AFECTADO")
	private String amparoAfec;
	@Size(max = 50, message = "El campo DIRECCION_RIESGO_AFECTADO no soporta mas de 50 caracteres")
	@Column(name = "DIRECCION_RIESGO_AFECTADO")
	private String dirRiesgoAfec;
	@Size(max = 50, message = "El campo PROVEEDOR no soporta mas de 50 caracteres")
	@Column(name = "PROVEEDOR")
	private String proveedor;
	@Size(max = 50, message = "El campo SINIESTRO_CON_REASEGURO no soporta mas de 50 caracteres")
	@Column(name = "SINIESTRO_CON_REASEGURO")
	private String siniestroReaseg;
	@Column(name = "VLR_PAGO")
	private BigDecimal vlrPago;                          

}

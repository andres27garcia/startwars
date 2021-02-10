package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_HIL_DATO_INICIAL")
public class SnrHilDatoInicial implements Serializable {

	private static final long serialVersionUID = 3613721204879668086L;

	@Id
	@Column(name = "ID_DETALLE_HIL")
	private Integer idDetalleHil;

	@Column(name = "NUM_PERSONA")
	private Long persona;

	@NotNull(message = "El campo PERIODO_COTIZACION no puede ser nulo o vacio")
	@Column(name = "PERIODO_COTIZACION")
	private Integer peridoCotizacion;

	@NotNull(message = "El campo FECHA_INICIAL_COT no puede ser nulo o vacio")
	@Column(name = "FECHA_INICIAL_COT")
	private LocalDate fecInicialCot;

	@NotNull(message = "El campo FECHA_FINAL_COT no puede ser nulo o vacio")
	@Column(name = "FECHA_FINAL_COT")
	private LocalDate fecFinalCot;

	@ManyToOne
	@JoinColumn(name = "ID_APORTANTE", insertable = false, updatable = false)
	private SnrHilAportante aportante;

	@NotNull(message = "El campo VLR_SALARIO no puede ser nulo o vacio")
	@Column(name = "VLR_SALARIO")
	private Integer vlrSalario;

	@NotNull(message = "El campo NUM_DIAS_COT no puede ser nulo o vacio")
	@Column(name = "NUM_DIAS_COT")
	private Integer numDiasCot;

	@NotNull(message = "El campo BLN_MENSUALIZADO no puede ser nulo o vacio")
	@Size(max = 1, message = "El campo BLN_MENSUALIZADO no soporta mas de 1 caracteres")
	@Column(name = "BLN_MENSUALIZADO", columnDefinition = "char")
	private String blnMensualizado;

	@ManyToOne
	@JoinColumn(name = "COD_ORIGEN_HIL", insertable = false, updatable = false)
	private SnrHilOrigenHistoria origenesHil;
	
	@Size(max = 1, message = "El campo BLN_REGISTRO_INVALIDO no soporta mas de 1 caracter")
	@Column(name = "BLN_REGISTRO_INVALIDO")
	private String blnRegistroInvalido;

}
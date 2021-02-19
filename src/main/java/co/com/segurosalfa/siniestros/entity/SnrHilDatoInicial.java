package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_HIL_DATO_INICIAL")
public class SnrHilDatoInicial implements Serializable {

	private static final long serialVersionUID = 3613721204879668086L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqHilDatosIniciales")
	@SequenceGenerator(name = "idSeqHilDatosIniciales", sequenceName = "SQ_HIL_DATOS_INICIALES", schema = "SINIESTROS", allocationSize = 1)
	@Column(name = "ID_DETALLE_HIL")
	private Long idDetalleHil;

	@Column(name = "NUM_PERSONA")
	private Long persona;

	@Column(name = "PERIODO_COTIZACION")
	private Integer peridoCotizacion;

	@Column(name = "FECHA_INICIAL_COT")
	private LocalDate fecInicialCot;

	@Column(name = "FECHA_FINAL_COT")
	private LocalDate fecFinalCot;

	@ManyToOne
    @JoinColumn(name = "ID_APORTANTE")
	private SnrHilAportante aportante;

	@Column(name = "VLR_SALARIO")
	private BigDecimal vlrSalario;

	@Column(name = "NUM_DIAS_COT")
	private Integer numDiasCot;

	@Column(name = "BLN_MENSUALIZADO", columnDefinition = "char")
	private String blnMensualizado;

	@ManyToOne
	@JoinColumn(name = "COD_ORIGEN_HIL")
	private SnrHilOrigenHistoria origenesHil;
	
	@Column(name = "BLN_REGISTRO_INVALIDO")
	private String blnRegistroInvalido;

}
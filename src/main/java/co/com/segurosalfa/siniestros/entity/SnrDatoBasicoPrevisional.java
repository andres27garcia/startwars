package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "SNR_DATO_BASICO_PREVISIONAL")
public class SnrDatoBasicoPrevisional implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqDatoBasicoPrev")
	@SequenceGenerator(name = "idSeqDatoBasicoPrev", sequenceName = "SEQ_SNR_DATO_BAS_PREVISIONAL", schema = "SINIESTROS", allocationSize = 1)
	@Column(name = "ID_DATO_BASICO_PREVISIONAL")
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "ID_SINIESTRO")
	private SnrDatoBasico siniestro;
	
	@Column(name = "NUM_CALIF_VINC")
	private BigDecimal numCalifVinc;
	
	@Column(name = "NUM_PROC_JUR_VINC")
	private Long numProcJurVinc;
	
	@Column(name = "VLR_BONO_PENSIONAL")
	private BigDecimal vlrBonoPensional;
	
	@Column(name = "VLR_CTA_INDIVIDUAL")
	private Long vlrCitaIndividual;

	@Column(name = "VLR_BONO_PEN_CTA_IND")
	private Integer vlrBonoPenCtaInd;
	
	@Column(name = "VLR_ULT_ING_BASE_COT")
	private Integer vlrUltIngBaseCot;
	
	@Column(name = "ULT_PERIODO_COT")
	private Integer ultPeriodoCot;
	
	@Column(name = "ULT_PERIODO_REC")
	private Integer ultPeriodoRec;
	
	@Column(name = "NUM_TRAMITE_INC_VINC")
	private Integer numTramiteIncVinc;
	
	@Column(name = "NUM_CALC_VINC")
	private Integer numCalcVinc;
	
	@Column(name = "NUM_RESERVA_VINC")
	private Integer numReservaVinc;
	
	@Column(name = "NUM_PAGO_VINC")
	private Integer numPagoVinc;
	
	@Column(name = "COD_CAUSA_ORIGEN")
	private String causaOrigen;
	
	@Column(name = "VLR_SALARIO_BASE")
	private BigDecimal vlrSalarioBase;
	
	@Column(name = "BLN_COTIZABA")
	private String blnCotizaba;

}

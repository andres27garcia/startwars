package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
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

@Data
@Entity
@Table(name = "SNR_HIL_HISTORIA_IBL")
public class SnrHilHistoriaIbl implements Serializable {

	private static final long serialVersionUID = 4615228930397381254L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqHilHistoriasibl")
	@SequenceGenerator(name = "idSeqHilHistoriasibl", sequenceName = "SQ_SNR_HIL_HISTORIAS_IBL", schema = "SINIESTROS")
	@Column(name = "ID_HISTORIA_IBL")
	private Long idHistoriaHil;

	@Column(name = "NUM_PERSONA")
	private Long persona;

	@Column(name = "FEC_INICIAL")
	private LocalDate fecInicialCot;

	@Column(name = "FEC_FINAL")
	private LocalDate fecFinalCot;

	@Column(name = "VLR_SALARIO")
	private Long vlrSalario;

	@Column(name = "NUM_DIAS_TRABAJADOS")
	private Integer numDiasTrabajados;

	@Column(name = "NUM_SEMANAS")
	private Integer numSemanas;

	@Column(name = "VLR_FACTOR")
	private Integer vlrFactor;

	@ManyToOne
	@JoinColumn(name = "COD_ORIGEN_HISTORIA", nullable = false, insertable = false, updatable = false)
	private SnrHilOrigenHistoria origenesHil;

	
}

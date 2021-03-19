package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

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
@Table(name = "SNR_DATO_RECLAMANTE")
public class SnrDatoReclamante implements Serializable {

	private static final long serialVersionUID = 1059261110948124507L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqReclamante")
	@SequenceGenerator(name = "idSeqReclamante", sequenceName = "SQ_SNR_DATOS_RECLAMANTES", allocationSize = 1)
	@Column(name = "ID_RECLAMANTE")
	private Long idReclamante;

	@ManyToOne
	@JoinColumn(name = "ID_TRAMITE")
	private SnrDatoTramite tramite;

	@Column(name = "num_persona")
	private Long numPersona;

	@ManyToOne
	@JoinColumn(name = "ID_ESTADO_RECLAMANTE")
//	@JoinColumnsOrFormulas({
//		@JoinColumnOrFormula(formula=@JoinFormula(value = "RECLAMANTE", referencedColumnName = "tipo")),
//		@JoinColumnOrFormula(column = @JoinColumn(name = "ID_ESTADO_RECLAMANTE", referencedColumnName = "codigo"))
//	})
	private SnrEstado estadoReclamante;

}
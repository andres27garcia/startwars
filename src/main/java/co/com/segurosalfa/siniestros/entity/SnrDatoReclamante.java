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
	@SequenceGenerator(name = "idSeqReclamante", sequenceName = "SQ_SNR_DATOS_RECLAMANTES", schema = "NUEVO_SIPREN")
	@Column(name = "ID_RECLAMANTE")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_TRAMITE", insertable = false, updatable = false)
	private SnrDatoTramite tramite;

	@Column(name = "num_persona")
	private Long persona;

	@ManyToOne
	@JoinColumn(name = "ID_ESTADO_RECLAMANTE", insertable = false, updatable = false)
	private SnrEstado estadoReclamante;

}
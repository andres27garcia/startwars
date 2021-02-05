package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "SNR_COMENTARIO_RECLAMANTE")
@Entity
public class SnrComentarioReclamante implements Serializable {

	private static final long serialVersionUID = -589761722849109533L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqComentariosReclamantes")
	@SequenceGenerator(name = "idSeqComentariosReclamantes", sequenceName = "SQ_SNR_COMENTARIOS_RECLAMANTES", schema = "NUEVO_SIPREN")
	@Column(name = "ID_COMENTARIO_RECLAMANTE")
	private Long idComentario;

	@ManyToOne
	@JoinColumn(name = "ID_RECLAMANTE", nullable = false)
	private SnrDatoReclamante reclamante;

	@Column(name = "FECHA_COMENTARIO")
	private LocalDateTime fecComentario;

	@Size(max = 20, message = "El campo USUARIO_COMENTARIO no soporta mas de 20 caracteres")
	@Column(name = "USUARIO_COMENTARIO")
	private String usuarioComentario;

	@Size(max = 1000, message = "El campo DESCRIPCION_COMENTARIO no soporta mas de 1000 caracteres")
	@Column(name = "DESCRIPCION_COMENTARIO")
	private String descripcionComentario;

}

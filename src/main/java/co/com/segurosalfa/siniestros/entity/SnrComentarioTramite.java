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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_COMENTARIO_TRAMITE")
public class SnrComentarioTramite implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqComentariosSiniestros")
	@SequenceGenerator(name = "idSeqComentariosSiniestros", sequenceName = "SQ_SNR_COMENTARIOS_TRAMITES", schema = "SINIESTROS", allocationSize = 1)
	@Column(name = "ID_COMENTARIO_TRAMITE")
	private Long idComentario;

	@NotNull(message = "El campo FECHA_COMENTARIO no puede ser nulo o vacio")
	@Column(name = "FECHA_COMENTARIO")
	private LocalDate fecComentario;

	@Size(max = 20, message = "El campo USUARIO_COMENTARIO no soporta mas de 20 caracteres")
	@Column(name = "USUARIO_COMENTARIO")
	private String usuarioComentario;

	@ManyToOne
	@JoinColumn(name = "ID_TRAMITE")
	private SnrDatoTramite tramite;

	@Size(max = 4000, message = "El campo DESCRIPCION_COMENTARIO no soporta mas de 4000 caracteres")
	@Column(name = "DESCRIPCION_COMENTARIO")
	private String descripcionComentario;

}

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
@Entity
@NoArgsConstructor
@Table(name = "SNR_COMENTARIO_PROCESO_JUR")
public class SnrComentarioProcesoJur implements Serializable {

	private static final long serialVersionUID = 6513116650649018289L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqComentariosProcJur")
	@SequenceGenerator(name = "idSeqComentariosProcJur", sequenceName = "SQ_SNR_PROCESOS_JURIDICOS", schema = "SINIESTROS", allocationSize = 1)
	@Column(name = "ID_COMENTARIO_PROCESO_JUR")
	private Long idComentarioJur;

	@ManyToOne
	@JoinColumn(name = "ID_PROCESO_JURIDICO")
	private SnrProcesoJuridico procesoJuridico;

	@NotNull(message = "El campo FECHA_COMENTARIO no puede ser nulo o vacio")
	@Column(name = "FECHA_COMENTARIO")
	private LocalDate fecComentario;

	@NotNull(message = "El campo USUARIO_COMENTARIO no puede ser nulo o vacio")
	@Size(max = 20, message = "El campo USUARIO_COMENTARIO no soporta mas de 20 caracteres")
	@Column(name = "USUARIO_COMENTARIO")
	private String usuarioComentario;

	@NotNull(message = "El campo DESCRIPCION_COMENTARIO no puede ser nulo o vacio")
	@Size(max = 4000, message = "El campo DESCRIPCION_COMENTARIO no soporta mas de 4000 caracteres")
	@Column(name = "DESCRIPCION_COMENTARIO")
	private String descripcionComentario;

}
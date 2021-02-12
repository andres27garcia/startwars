package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SNR_ABOGADO")
public class SnrAbogado implements Serializable {

	private static final long serialVersionUID = 6895052447379863147L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqAbogados")
	@SequenceGenerator(name = "idSeqAbogados", sequenceName = "SQ_SNR_ABOGADOS", schema = "SINIESTROS", allocationSize = 1)
	@Column(name = "ID_ABOGADO")
	private Integer idAbogado;

	@NotNull(message = "El campo COD_TIPO_IDENTIFICACION no puede ser nulo o vacio")
	@Column(name = "COD_TIPO_IDENTIFICACION")
	private Integer codTipoDocumento;

	@NotNull(message = "El campo NUM_ID_ABOGADO no puede ser nulo o vacio")
	@Column(name = "NUM_ID_ABOGADO")
	private Integer numIdAbogado;

	@NotNull(message = "El campo NOMBRE_ABOGADO no puede ser nulo o vacio")
	@Size(max = 320, message = "El campo NOMBRE_ABOGADO no soporta mas de 320 caracteres")
	@Column(name = "NOMBRE_ABOGADO")
	private String nombreAbogado;

}
package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_HIL_TOPE_SALARIO")
public class SnrHilTopeSalario implements Serializable {

	private static final long serialVersionUID = 9128462273472729548L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TOPE_SALARIO")
	private Long idTopeSalario;

	@Column(name = "FECHA_VIGENCIA_INICIAL")
	private LocalDate fecVigenciaInicial;

	@Column(name = "FECHA_VIGENCIA_FINAL")
	private LocalDate fecVigenciaFinal;

	@Column(name = "NUM_SALARIOS_MINIMOS")
	private Long numSalarioMinimo;
	
}

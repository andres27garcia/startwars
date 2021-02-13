package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProgramacionJobDTO implements Serializable {

	private static final long serialVersionUID = -304171957528481128L;

	private String codJob;
	private String fechaEjecucion;
	private String usuario;
	private String email;
	private Integer codPeriodicidad;
	private Integer codFecCorte;
	private String detalleFrecuencia;
	private String acccionJob;

	public ProgramacionJobDTO(String codJob, String fechaEjecucion, String usuario, String email,
			Integer codPeriodicidad, Integer codFecCorte) {
		this.codJob = codJob;
		this.fechaEjecucion = fechaEjecucion;
		this.usuario = usuario;
		this.email = email;
		this.codPeriodicidad = codPeriodicidad;
		this.codFecCorte = codFecCorte;
	}
}

package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcesosDTO implements Serializable {

	private static final long serialVersionUID = -6926381718601587232L;

	private Integer idProceso;
	private String nombreProceso;
	private String periodicidad;
	private String fecFinEjecucion;
	private String estadoProceso;
	private String idPeriodicidad;
	private String fecInicioProceso;
	private String fecCancelacion;
	private String periodoCorte;
	private String usuarioInicio;
	private String usuarioCancelacion;
	private String avance;

}

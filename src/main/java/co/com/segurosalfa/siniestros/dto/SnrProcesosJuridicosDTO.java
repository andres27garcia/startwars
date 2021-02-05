package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrProcesosJuridicosDTO {

	private Integer idProcesoJuridico;
	private SnrTipoDTO tipoProcesoJuridico;
	private SnrDatosTramiteDTO tramite;
	private LocalDateTime fecProcesoJuridico;
	private String nombreActor;
	private SnrTipoDTO pretencion;
	private SnrTipoDTO contingencia;
	private SnrTipoDTO clasificacionClase;
	private SnrEstadosDTO estadoJuridico;
	private String juzgado;
	private Integer idAbogado;
	private String blnConflictoBeneficiarios;
	private String blnFidelidad;

}
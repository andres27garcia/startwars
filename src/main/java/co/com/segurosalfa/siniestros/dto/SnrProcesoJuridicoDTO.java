package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrProcesoJuridicoDTO {

	private Integer idProcesoJuridico;
	private SnrTipoDTO tipoProcesoJuridico;
	private SnrDatoTramiteDTO tramite;
	private LocalDate fecProcesoJuridico;
	private String nombreActor;
	private SnrTipoDTO pretencion;
	private SnrTipoDTO contingencia;
	private SnrTipoDTO clasificacionClase;
	private SnrEstadoDTO estadoJuridico;
	private SnrAbogadoDTO idAbogado;
	private String blnConflicBeneficiarios;
	private String blnFidelidad;
	private SnrTipoDTO juzgado;

}
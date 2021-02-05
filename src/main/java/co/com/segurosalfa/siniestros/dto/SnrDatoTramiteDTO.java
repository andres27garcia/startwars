package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoTramiteDTO {

	private Long idTramite;
	private SnrDatoBasicoDTO siniestro;
	private Integer numPoliza;
	private SnrTipoDTO tipoTramite;
	private SnrEstadoDTO estadoTramite;
	private SnrTipoDTO clasificacionJur;
	private Integer idSolicitudAfp;
	private String blnCotFechaSnr;
	private LocalDate fecRadicacionAfp;
	private LocalDate fecActSolicitudAfp;
	private String resultadoSolicitudAfp;
	private LocalDate fecRadicacionAlfa;
	private LocalDate fecPago;
	
	
}
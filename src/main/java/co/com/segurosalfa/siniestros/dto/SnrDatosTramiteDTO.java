package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatosTramiteDTO {

	private Integer idTramite;
	private SnrDatoBasicoDTO siniestro;
	private Integer numPoliza;
	private SnrTipoDTO tramite;
	private SnrEstadosDTO estadoTramite;
	private SnrClaseJuridicoDTO clasificacionJur;
	private LocalDateTime fecRadicacionAseguradora;
	private Integer idSolicitudAfp;
	private String cotFechaSnr;

}
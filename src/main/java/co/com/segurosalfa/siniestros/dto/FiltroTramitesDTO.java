package co.com.segurosalfa.siniestros.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiltroTramitesDTO {

	private FiltroGenericoDTO persona;
	private FiltroGenericoDTO identificacion;
	private FiltroGenericoDTO tramite;
	private Integer tipoTramite;
	private Integer subtipoTramite;
	private Integer estadoTramite;
	private LocalDate fecRadicacionAlfaIni;
	private LocalDate fecRadicacionAlfaFin;
	private FiltroGenericoDTO solicitudAfp;
	private Integer clasificacionJur;

}

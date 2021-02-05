package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiltroTramitesDTO {


	private Long numPersona;
	private Integer numIdentificacion;
	private Long idTramite;
	private Integer tipoTramite;
	private Integer subtipoTramite;
	private Integer estadoTramite;
	private String fecRadicacionAlfaIni;
	private String fecRadicacionAlfaFin;
	private Integer idSolicitudAfp;
	private Integer clasificacionJur;

}

package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrResulPrcCreacionSiniestroDTO {

	private Integer idRegistro;
	private String tipoIdent;
	private Long nroIdent;
	private String primerApell;
	private String segundoApell;
	private String primerNombre;
	private String segundoNombre;
	private Integer nroSolicitud;
	private String fecRadicAFP;
	private String tipoSolicitud;
	private String procesada;
	private String codEstado;
	private String descEstado;
	private Long nroPersonaCliente;
	private Long nroSiniestro;
	private Long nroTramiteSiniestro;
	private String estadoRegistro;
	private String detalleError;
	private Long nroCalculo;
    private String nroMovReserva;

	public SnrResulPrcCreacionSiniestroDTO(String tipoIdent, Long nroIdent, String primerApell, String segundoApell,
			String primerNombre, String segundoNombre, Integer nroSolicitud, String fecRadicAFP, String tipoSolicitud,
			String procesada, String codEstado, String descEstado, Long nroPersonaCliente, Long nroSiniestro,
			Long nroTramiteSiniestro) {
		this.tipoIdent = tipoIdent;
		this.nroIdent = nroIdent;
		this.primerApell = primerApell;
		this.segundoApell = segundoApell;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.nroSolicitud = nroSolicitud;
		this.fecRadicAFP = fecRadicAFP;
		this.tipoSolicitud = tipoSolicitud;
		this.procesada = procesada;
		this.codEstado = codEstado;
		this.descEstado = descEstado;
		this.nroPersonaCliente = nroPersonaCliente;
		this.nroSiniestro = nroSiniestro;
		this.nroTramiteSiniestro = nroTramiteSiniestro;
	}

}
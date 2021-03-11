package co.com.segurosalfa.siniestros.dto;

import java.util.Date;

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
    private String origenSiniestro;
    private Date fecActSolicAfp;
    private String resultadoSolicAfp;
    private String flgProceso;
    private String estadoRegistro;
    private String detalleError;
    private Long nroCalculo;
    private String nroMovReserva;

}
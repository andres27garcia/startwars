package co.com.segurosalfa.siniestros.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AfiliadoDTO {

	private String codTipoDocumento;
	private String usuario;
	private Long numeroDocumento;
	private String primerApellido;
	private String primerNombre;
	private String segundoApellido;
	private String segundoNombre;
	private Date fecNacimiento;
	private String genero;
	private Long nroCuentaAfp;
	private Long vlrSalarioBaseAfp;
	private Integer ultimoPeriodoAfp;
	private Date fecIngresoAfp;
	private String codEstadoCivil;
	private String condicionInv;
	private String nacionalidad;
	private Date fecExpDocumento;
	private String eps;
	private String codOcupacion;
	private Date fecMuerte;
	private Long numPersona;
	private String rol;
	private List<LocalizacionesDTO> localizaciones;

}

package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.com.sipren.common.util.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class ProcesarPendientesDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer idTipoDocumento;
	private String tipoDocumento;
	private Long identificacionAfiliado;
	private String tipoSolicitudAfp;
	private Integer idSolicitudAfp;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaSolicitud;
	private Long identificacionReclamante;
	private Long idSiniestro;
	private Long idTramite;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaRadicacionAlfa;
	private String tipoRolPersona;
	private Long numPersona;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private List<ComparacionPersonaDTO> listComparaciones;
	private LocalDate fecNacimiento;
	private String codEstadoCivil;
	private String estadoCivilDesc;
	private String genero;
	private Long documento;
	private Integer ocupacion;
	private String ocupacionDesc;
	private Integer eps;
	private String epsDesc;
	private String fecMuerte;
	private Integer estadoReclamante;
	private String usuario;
	private Integer codTipoSolicitudAfp;
	private LocalDate fecSolicitud;
	private String tipoDocAfp;

	/**
	 * Constructor utilizado para consulta por documento y tipo de
	 * SnrTmpPendienteAfiliado
	 * 
	 * @param tipoDocumento
	 * @param identificacionAfiliado
	 * @param tipoSolicitudAfp
	 * @param idSolicitudAfp
	 * @param fechaSolicitud
	 */
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, Long identificacionAfiliado, String tipoSolicitudAfp,
			Integer idSolicitudAfp, LocalDate fechaSolicitud, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, Integer codTipoSolicitudAfp, String tipoDocAfp,
			String tipoDocumento) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
		// salia del campo nombre de GnrTiposDocumentos
		this.tipoDocumento = tipoDocumento;
		this.identificacionAfiliado = identificacionAfiliado;
		if (Objects.nonNull(tipoSolicitudAfp))
			this.tipoSolicitudAfp = tipoSolicitudAfp;
		else
			this.tipoSolicitudAfp = "";
		this.idSolicitudAfp = idSolicitudAfp;
		if (Objects.nonNull(fechaSolicitud))
			this.fechaSolicitud = fechaSolicitud;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.codTipoSolicitudAfp = codTipoSolicitudAfp;
		// Campo salia de GnrEquivalenciaTipo
		this.tipoDocAfp = tipoDocAfp;
	}

	/**
	 * 
	 * Constructor utilizado para consulta de lista SnrTmpPendienteAfiliado
	 * 
	 * @param idTipoDocumento
	 * @param tipoDocumento
	 * @param identificacionAfiliado
	 * @param tipoSolicitudAfp
	 * @param idSolicitudAfp
	 * @param fechaSolicitud
	 */
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, Long identificacionAfiliado, String tipoSolicitudAfp,
			Integer idSolicitudAfp, LocalDate fechaSolicitud, Integer codTipoSolicitudAfp, String tipoDocAfp,
			String tipoDocumento) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
//		salia del campo nombre de GnrTiposDocumentos
		this.tipoDocumento = tipoDocumento;
		this.identificacionAfiliado = identificacionAfiliado;
		if (Objects.nonNull(tipoSolicitudAfp))
			this.tipoSolicitudAfp = tipoSolicitudAfp;
		else
			this.tipoSolicitudAfp = "";
		this.idSolicitudAfp = idSolicitudAfp;
		if (Objects.nonNull(fechaSolicitud))
			this.fechaSolicitud = fechaSolicitud;
		this.codTipoSolicitudAfp = codTipoSolicitudAfp;
//		Campo salia de GnrEquivalenciaTipo
		this.tipoDocAfp = tipoDocAfp;
	}

	/**
	 * Constructor utilizado para consulta por documento y tipo de
	 * SnrTmpPendienteReclamante
	 * 
	 * @param identificacionAfiliado
	 * @param identificacionReclamante
	 * @param idSiniestro
	 * @param idTramite
	 * @param fechaRadicacionAlfa
	 */
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, Long identificacionReclamante, Long idSiniestro,
			Long idTramite, LocalDate fechaRadicacionAlfa, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, Integer solicitudAfp, Long numPersona,
			Integer estadoReclamante, String tipoDocumento) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
		this.identificacionReclamante = identificacionReclamante;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = fechaRadicacionAlfa;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.idSolicitudAfp = solicitudAfp;
		this.estadoReclamante = estadoReclamante;
		this.tipoDocumento = tipoDocumento;
		this.numPersona = numPersona;
	}

	/**
	 * Constructor utilizado para consulta de lista SnrTmpPendienteReclamante
	 * 
	 * @param identificacionAfiliado
	 * @param identificacionReclamante
	 * @param idSiniestro
	 * @param idTramite
	 * @param fechaRadicacionAlfa
	 */
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, Long numPersona, Long identificacionReclamante,
			Long idSiniestro, Long idTramite, LocalDate fechaRadicacionAlfa) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
		this.identificacionReclamante = identificacionReclamante;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = fechaRadicacionAlfa;
		this.numPersona = numPersona;
	}

	/**
	 * 
	 * Constructor Utilizado para consulta de listado SnrTmpPendienteInfoAdicional
	 * 
	 * @param tipoDocumento
	 * @param identificacionAfiliado
	 * @param identificacionReclamante
	 * @param idSiniestro
	 * @param idTramite
	 * @param fechaRadicacionAlfa
	 * @param tipoRolPersona
	 */
	public ProcesarPendientesDTO(Long id, Long documento, Long idSiniestro, Long idTramite,
			LocalDate fechaRadicacionAlfa, String tipoRolPersona, Integer idTipoDocumento, Long numPersona) {
		super();
		this.id = id;
		// Este campo salia de GnrTiposDocumentos
		this.documento = documento;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = fechaRadicacionAlfa;
		this.tipoRolPersona = tipoRolPersona;
		this.idTipoDocumento = idTipoDocumento;
		// Este campo se obtenia de la relación del siniestro con gnrPersona
		// this.identificacionAfiliado = documentoAfiliado.longValue();
		this.numPersona = numPersona;
	}

	/**
	 * 
	 * Constructor utilizado para consulta de SnrTmpPendienteInfoAdicional por
	 * documento
	 * 
	 * @param tipoDocumento
	 * @param identificacionAfiliado
	 * @param identificacionReclamante
	 * @param idSiniestro
	 * @param idTramite
	 * @param fechaRadicacionAlfa
	 * @param tipoRolPersona
	 * @param genero
	 * @param codEstadoCivil
	 * @param fechaNacimiento
	 */
	public ProcesarPendientesDTO(Long id, Long documento, Long idSiniestro, Long idTramite,
			LocalDate fechaRadicacionAlfa, String tipoRolPersona, String genero, Integer codEstadoCivil,
			LocalDate fechaNacimiento, Integer eps, Date fechaMuerte, Integer idTipoDocumento) {
		super();
		this.id = id;
		this.documento = documento;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = fechaRadicacionAlfa;
		this.tipoRolPersona = tipoRolPersona;
		this.genero = genero;
		if (Objects.nonNull(codEstadoCivil))
			this.codEstadoCivil = String.valueOf(codEstadoCivil);
		this.estadoCivilDesc = estadoCivilDesc != null ? estadoCivilDesc : "";
		this.fecNacimiento = fechaNacimiento;
		this.eps = eps;
		this.ocupacionDesc = ocupacionDesc != null ? ocupacionDesc : "";
		if (Objects.nonNull(fechaMuerte))
			this.fecMuerte = DateUtil.convertDateToString(fechaMuerte);
		this.idTipoDocumento = idTipoDocumento;
	}

}

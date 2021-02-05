package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class ProcesarPendientesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	private Long id;
	private Integer idTipoDocumento;
	private String tipoDocumento;
	private Long identificacionAfiliado;
	private String tipoSolicitudAfp;
	private Integer idSolicitudAfp;
	private LocalDate fechaSolicitud;
	private Long identificacionReclamante;
	private Long idSiniestro;
	private Long idTramite;
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
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, String tipoDocumento, Long identificacionAfiliado,
			String tipoSolicitudAfp, Integer idSolicitudAfp, LocalDate fechaSolicitud, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido, Integer codTipoSolicitudAfp,
			String tipoDocAfp) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
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
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, String tipoDocumento, Long identificacionAfiliado,
			String tipoSolicitudAfp, Integer idSolicitudAfp, LocalDate fechaSolicitud, Integer codTipoSolicitudAfp,
			String tipoDocAfp) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
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
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, Integer identificacionAfiliado,
			Long identificacionReclamante, Long idSiniestro, Long idTramite, LocalDate fechaRadicacionAlfa,
			String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			Integer solicitudAfp, Long numPersona, Integer estadoReclamante, String tipoDocumento) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
		if (Objects.nonNull(identificacionAfiliado))
			this.identificacionAfiliado = identificacionAfiliado.longValue();
		else
			this.identificacionAfiliado = (long) 0;
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
		this.numPersona = numPersona;
		this.estadoReclamante = estadoReclamante;
		this.tipoDocumento = tipoDocumento;
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
	public ProcesarPendientesDTO(Long id, Integer idTipoDocumento, Integer identificacionAfiliado,
			Long identificacionReclamante, Long idSiniestro, Long idTramite, LocalDate fechaRadicacionAlfa) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
		if (Objects.nonNull(identificacionAfiliado))
			this.identificacionAfiliado = identificacionAfiliado.longValue();
		else
			this.identificacionAfiliado = (long) 0;
		this.identificacionReclamante = identificacionReclamante;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = fechaRadicacionAlfa;
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
	public ProcesarPendientesDTO(Long id, String tipoDocumento, Long documento, Long idSiniestro, Long idTramite,
			LocalDate fechaRadicacionAlfa, String tipoRolPersona, Integer idTipoDocumento,
			Integer documentoAfiliado) {
		super();
		this.id = id;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = fechaRadicacionAlfa;
		this.tipoRolPersona = tipoRolPersona;
		this.idTipoDocumento = idTipoDocumento;
		this.identificacionAfiliado = documentoAfiliado.longValue();
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
	public ProcesarPendientesDTO(Long id, String tipoDocumento, Long documento, Long idSiniestro, Long idTramite,
			LocalDate fechaRadicacionAlfa, String tipoRolPersona, String genero, Integer codEstadoCivil,
			String estadoCivilDesc, LocalDate fechaNacimiento, Integer ocupacion, String ocupacionDesc, Integer eps,
			String epsDesc, Date fechaMuerte, Integer idTipoDocumento) {
		super();
		this.id = id;
		this.tipoDocumento = tipoDocumento;
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
		this.epsDesc = epsDesc;
		this.ocupacion = ocupacion;
		this.ocupacionDesc = ocupacionDesc != null ? ocupacionDesc : "";
		if (Objects.nonNull(fechaMuerte))
			this.fecMuerte = sdf.format(fechaMuerte);
		this.idTipoDocumento = idTipoDocumento;
	}

}

package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	@JsonFormat(pattern = "yyyy/MM/dd")
	private String fechaSolicitud;
	private Long identificacionReclamante;
	private Long idSiniestro;
	private Long idTramite;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date fechaRadicacionAlfa;
	private String tipoRolPersona;
	private Long numPersona;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private List<ComparacionPersonaDTO> listComparaciones;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date fecNacimiento;
	private String codEstadoCivil;
	private String estadoCivilDesc;
	private String genero;
	private Long documento;
	private Integer eps;
	private String epsDesc;
	private String fecMuerte;
	private Integer estadoReclamante;
	private String usuario;
	private Integer codTipoSolicitudAfp;
	private Date fecSolicitud;
	private String tipoDocAfp;

	public ProcesarPendientesDTO() {
		super();
	}

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
			String tipoSolicitudAfp, Integer idSolicitudAfp, Date fechaSolicitud, String primerNombre,
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
			this.fechaSolicitud = sdf.format(fechaSolicitud);
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
			String tipoSolicitudAfp, Integer idSolicitudAfp, Date fechaSolicitud, Integer codTipoSolicitudAfp,
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
			this.fechaSolicitud = sdf.format(fechaSolicitud);
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
			Long identificacionReclamante, Long idSiniestro, Long idTramite, LocalDateTime fechaRadicacionAlfa,
			String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			Integer solicitudAfp, Long numPersona, Integer estadoReclamante, String tipoDocumento) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
		if (Objects.nonNull(identificacionAfiliado))
			this.identificacionAfiliado = identificacionAfiliado.longValue();
		else
			this.identificacionAfiliado = Long.valueOf(0);
		this.identificacionReclamante = identificacionReclamante;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = this.convertToDate(fechaRadicacionAlfa);
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
			Long identificacionReclamante, Long idSiniestro, Long idTramite, LocalDateTime fechaRadicacionAlfa) {
		super();
		this.id = id;
		this.idTipoDocumento = idTipoDocumento;
		if (Objects.nonNull(identificacionAfiliado))
			this.identificacionAfiliado = identificacionAfiliado.longValue();
		else
			this.identificacionAfiliado = Long.valueOf(0);
		this.identificacionReclamante = identificacionReclamante;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = this.convertToDate(fechaRadicacionAlfa);
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
			LocalDateTime fechaRadicacionAlfa, String tipoRolPersona, Integer idTipoDocumento,
			Integer documentoAfiliado) {
		super();
		this.id = id;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = this.convertToDate(fechaRadicacionAlfa);
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
			LocalDateTime fechaRadicacionAlfa, String tipoRolPersona, String genero, Integer codEstadoCivil,
			String estadoCivilDesc, Date fechaNacimiento, Integer eps, String epsDesc, Date fechaMuerte,
			Integer idTipoDocumento) {
		super();
		this.id = id;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.idSiniestro = idSiniestro;
		this.idTramite = idTramite;
		if (Objects.nonNull(fechaRadicacionAlfa))
			this.fechaRadicacionAlfa = this.convertToDate(fechaRadicacionAlfa);
		this.tipoRolPersona = tipoRolPersona;
		this.genero = genero;
		if (Objects.nonNull(codEstadoCivil))
			this.codEstadoCivil = String.valueOf(codEstadoCivil);
		this.estadoCivilDesc = estadoCivilDesc != null ? estadoCivilDesc : "";
		this.fecNacimiento = fechaNacimiento;
		this.eps = eps;
		this.epsDesc = epsDesc;
		if (Objects.nonNull(fechaMuerte))
			this.fecMuerte = sdf.format(fechaMuerte);
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getIdentificacionAfiliado() {
		return identificacionAfiliado;
	}

	public void setIdentificacionAfiliado(Long identificacionAfiliado) {
		this.identificacionAfiliado = identificacionAfiliado;
	}

	public Long getIdentificacionReclamante() {
		return identificacionReclamante;
	}

	public void setIdentificacionReclamante(Long identificacionReclamante) {
		this.identificacionReclamante = identificacionReclamante;
	}

	public String getTipoSolicitudAfp() {
		return tipoSolicitudAfp;
	}

	public void setTipoSolicitudAfp(String tipoSolicitudAfp) {
		this.tipoSolicitudAfp = tipoSolicitudAfp;
	}

	public Integer getIdSolicitudAfp() {
		return idSolicitudAfp;
	}

	public void setIdSolicitudAfp(Integer idSolicitudAfp) {
		this.idSolicitudAfp = idSolicitudAfp;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Long getIdSiniestro() {
		return idSiniestro;
	}

	public void setIdSiniestro(Long idSiniestro) {
		this.idSiniestro = idSiniestro;
	}

	public Long getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}

	public Date getFechaRadicacionAlfa() {
		return fechaRadicacionAlfa;
	}

	public void setFechaRadicacionAlfa(Date fechaRadicacionAlfa) {
		this.fechaRadicacionAlfa = fechaRadicacionAlfa;
	}

	public String getTipoRolPersona() {
		return tipoRolPersona;
	}

	public void setTipoRolPersona(String tipoRolPersona) {
		this.tipoRolPersona = tipoRolPersona;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public List<ComparacionPersonaDTO> getListComparaciones() {
		return listComparaciones;
	}

	public void setListComparaciones(List<ComparacionPersonaDTO> listComparaciones) {
		this.listComparaciones = listComparaciones;
	}

	public Date getFecNacimiento() {
		return fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public String getCodEstadoCivil() {
		return codEstadoCivil;
	}

	public void setCodEstadoCivil(String codEstadoCivil) {
		this.codEstadoCivil = codEstadoCivil;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Long getDocumento() {
		return documento;
	}

	public void setDocumento(Long documento) {
		this.documento = documento;
	}

	public Integer getEps() {
		return eps;
	}

	public void setEps(Integer eps) {
		this.eps = eps;
	}

	public String getFecMuerte() {
		return fecMuerte;
	}

	public void setFecMuerte(String fecMuerte) {
		this.fecMuerte = fecMuerte;
	}

	public Long getNumPersona() {
		return numPersona;
	}

	public void setNumPersona(Long numPersona) {
		this.numPersona = numPersona;
	}

	public Integer getEstadoReclamante() {
		return estadoReclamante;
	}

	public void setEstadoReclamante(Integer estadoReclamante) {
		this.estadoReclamante = estadoReclamante;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	private Date convertToDate(LocalDateTime dateToConvert) {
		return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodTipoSolicitudAfp() {
		return codTipoSolicitudAfp;
	}

	public void setCodTipoSolicitudAfp(Integer codTipoSolicitudAfp) {
		this.codTipoSolicitudAfp = codTipoSolicitudAfp;
	}

	public Date getFecSolicitud() {
		return fecSolicitud;
	}

	public void setFecSolicitud(Date fecSolicitud) {
		this.fecSolicitud = fecSolicitud;
	}

	public String getTipoDocAfp() {
		return tipoDocAfp;
	}

	public void setTipoDocAfp(String tipoDocAfp) {
		this.tipoDocAfp = tipoDocAfp;
	}

	public String getEpsDesc() {
		return epsDesc;
	}

	public void setEpsDesc(String epsDesc) {
		this.epsDesc = epsDesc;
	}

	public String getEstadoCivilDesc() {
		return estadoCivilDesc;
	}

	public void setEstadoCivilDesc(String estadoCivilDesc) {
		this.estadoCivilDesc = estadoCivilDesc;
	}

}

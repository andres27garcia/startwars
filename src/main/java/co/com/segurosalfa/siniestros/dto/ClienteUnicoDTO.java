package co.com.segurosalfa.siniestros.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ClienteUnicoDTO {

	private String nroCuentaInd;
	private String cedula;
	private String tipoDoc;
	private String primerApell;
	private String segundoApell;
	private String primerNombre;
	private String segundoNombre;
	private String fecNacimiento;
	private String genero;
	private BigDecimal salarioBase;
	private String direccion;
	private String codCiudad;
	private String telefono;
	private String apartAereo;
	private String ocupacion;
	private String estadoPers;
	private String subEstadPers;
	private String uppCap;
	private String fecIngreso;
	private String monCap;
	private BigDecimal ultSalarReal;
	private BigDecimal sldoTtalPesos;
	private String nroPinPoint;
	private String eps;
	private String codEstadoCivil;
	private String nacionalidad;
	private String condicionInvalidez;
	private String codOcupacion;
	private String fecExpDocumento;
	private String fecMuerte;
	private String estadoCivil;
	private String tipoDocumento;
	private String usuario;
	private Long nroCuentaAfp;
	private Long vlrSalarioBaseAfp;
	private Integer ultimoPeriodoAfp;
	private String fecIngresoAfp;
	private Long numPersona;
	private List<LocalizacionDTO> localizaciones;

	public String getFecMuerte() {

		return fecMuerte;
	}

	public void setFecMuerte(String fecMuerte) {
		this.fecMuerte = fecMuerte;
	}

	public String getEps() {

		if (eps == null)
			eps = "";

		return eps;
	}

	public void setEps(String eps) {
		if (Objects.nonNull(eps) && "-1".equals(eps))
			this.eps = "";
		else
		    this.eps = eps;
	}

	public String getCodEstadoCivil() {

		if (codEstadoCivil == null)
			codEstadoCivil = "";

		return codEstadoCivil;
	}

	public void setCodEstadoCivil(String codEstadoCivil) {
		if (Objects.nonNull(codEstadoCivil) && "-1".equals(codEstadoCivil))
			this.codEstadoCivil = "";
		else
			this.codEstadoCivil = codEstadoCivil;
	}

	public String getNacionalidad() {

		if (nacionalidad == null)
			nacionalidad = "";

		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		if (Objects.nonNull(nacionalidad) && "-1".equals(nacionalidad))
			this.nacionalidad = "";
		else
			this.nacionalidad = nacionalidad;
	}

	public String getCondicionInvalidez() {

		if (condicionInvalidez == null)
			condicionInvalidez = "";

		return condicionInvalidez;
	}

	public void setCondicionInvalidez(String condicionInvalidez) {
		if (Objects.nonNull(condicionInvalidez) && "-1".equals(condicionInvalidez))
			this.condicionInvalidez = "";
		else
			this.condicionInvalidez = condicionInvalidez;
	}

	public String getCodOcupacion() {

		if (codOcupacion == null)
			codOcupacion = "";

		return codOcupacion;
	}

	public void setCodOcupacion(String codOcupacion) {
		if (Objects.nonNull(codOcupacion) && "-1".equals(codOcupacion))
			this.codOcupacion = "";
		else
			this.codOcupacion = codOcupacion;
	}

	public String getFecExpDocumento() {

		return fecExpDocumento;
	}

	public void setFecExpDocumento(String fecExpDocumento) {
		this.fecExpDocumento = fecExpDocumento;
	}

	public ClienteUnicoDTO() {
		super();
	}

	public String getNroCuentaInd() {
		if (nroCuentaInd == null) {
			nroCuentaInd = "";
		}

		return nroCuentaInd;
	}

	public void setNroCuentaInd(String nroCuentaInd) {
		this.nroCuentaInd = nroCuentaInd;
	}

	public String getCedula() {
		if (cedula == null)
			cedula = "";

		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTipoDoc() {
		if (tipoDoc == null)
			tipoDoc = "";

		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getPrimerApell() {
		if (primerApell == null)
			primerApell = "";

		return primerApell;
	}

	public void setPrimerApell(String primerApell) {
		if (Objects.nonNull(primerApell) && "NA".equals(primerApell))
			primerApell = "";
		else
		    this.primerApell = primerApell;
	}

	public String getSegundoApell() {
		if (segundoApell == null)
			segundoApell = "";

		return segundoApell;
	}

	public void setSegundoApell(String segundoApell) {
		if (Objects.nonNull(segundoApell) && "NA".equals(segundoApell))
			segundoApell = "";
		else
		    this.segundoApell = segundoApell;
	}

	public String getPrimerNombre() {
		if (primerNombre == null)
			primerNombre = "";
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		if (Objects.nonNull(primerNombre) && "NA".equals(primerNombre))
			primerNombre = "";
		else
		    this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		if (segundoNombre == null)
			segundoNombre = "";

		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		if (Objects.nonNull(segundoNombre) && "NA".equals(segundoNombre))
			segundoNombre = "";
		else
		   this.segundoNombre = segundoNombre;
	}

	public String getFecNacimiento() {

		return fecNacimiento;
	}

	public void setFecNacimiento(String fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public String getGenero() {
		if (genero == null)
			genero = "";

		return genero;
	}

	public void setGenero(String genero) {
		if (Objects.nonNull(genero) && "-1".equals(genero))
			this.genero = "";
		else
			this.genero = genero;
	}

	public BigDecimal getSalarioBase() {
		if (salarioBase == null)
			salarioBase = BigDecimal.ZERO;

		return salarioBase;
	}

	public void setSalarioBase(BigDecimal salarioBase) {
		this.salarioBase = salarioBase;
	}

	public String getDireccion() {
		if (direccion == null)
			direccion = "";

		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodCiudad() {
		if (codCiudad == null)
			codCiudad = "";

		return codCiudad;
	}

	public void setCodCiudad(String codCiudad) {
		this.codCiudad = codCiudad;
	}

	public String getTelefono() {
		if (telefono == null)
			telefono = "";

		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getApartAereo() {
		if (apartAereo == null)
			apartAereo = "";

		return apartAereo;
	}

	public void setApartAereo(String apartAereo) {
		if (Objects.nonNull(apartAereo) && "-1".equals(apartAereo))
			this.apartAereo = "";
		else
			this.apartAereo = apartAereo;
	}

	public String getOcupacion() {
		if (ocupacion == null)
			ocupacion = "";

		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getEstadoPers() {
		if (estadoPers == null)
			estadoPers = "";

		return estadoPers;
	}

	public void setEstadoPers(String estadoPers) {
		this.estadoPers = estadoPers;
	}

	public String getSubEstadPers() {
		if (subEstadPers == null)
			subEstadPers = "";

		return subEstadPers;
	}

	public void setSubEstadPers(String subEstadPers) {
		this.subEstadPers = subEstadPers;
	}

	public String getUppCap() {
		if (uppCap == null)
			uppCap = "";

		return uppCap;
	}

	public void setUppCap(String uppCap) {
		this.uppCap = uppCap;
	}

	public String getFecIngreso() {
		if (fecIngreso == null)
			fecIngreso = "";

		return fecIngreso;
	}

	public void setFecIngreso(String fecIngreso) {
		this.fecIngreso = fecIngreso;
	}

	public String getMonCap() {
		if (monCap == null)
			monCap = "";

		return monCap;
	}

	public void setMonCap(String monCap) {
		this.monCap = monCap;
	}

	public BigDecimal getUltSalarReal() {
		if (ultSalarReal == null)
			ultSalarReal = BigDecimal.ZERO;

		return ultSalarReal;
	}

	public void setUltSalarReal(BigDecimal ultSalarReal) {
		this.ultSalarReal = ultSalarReal;
	}

	public BigDecimal getSldoTtalPesos() {
		if (sldoTtalPesos == null)
			sldoTtalPesos = BigDecimal.ZERO;

		return sldoTtalPesos;
	}

	public void setSldoTtalPesos(BigDecimal sldoTtalPesos) {
		this.sldoTtalPesos = sldoTtalPesos;
	}

	public String getNroPinPoint() {
		if (nroPinPoint == null)
			nroPinPoint = "";

		return nroPinPoint;
	}

	public void setNroPinPoint(String nroPinPoint) {
		this.nroPinPoint = nroPinPoint;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<LocalizacionDTO> getLocalizaciones() {
		return localizaciones;
	}

	public void setLocalizaciones(List<LocalizacionDTO> localizaciones) {
		this.localizaciones = localizaciones;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getNroCuentaAfp() {
		return nroCuentaAfp;
	}

	public void setNroCuentaAfp(Long nroCuentaAfp) {
		this.nroCuentaAfp = nroCuentaAfp;
	}

	public Long getVlrSalarioBaseAfp() {
		return vlrSalarioBaseAfp;
	}

	public void setVlrSalarioBaseAfp(Long vlrSalarioBaseAfp) {
		this.vlrSalarioBaseAfp = vlrSalarioBaseAfp;
	}

	public Integer getUltimoPeriodoAfp() {
		return ultimoPeriodoAfp;
	}

	public void setUltimoPeriodoAfp(Integer ultimoPeriodoAfp) {
		this.ultimoPeriodoAfp = ultimoPeriodoAfp;
	}

	public String getFecIngresoAfp() {
		return fecIngresoAfp;
	}

	public void setFecIngresoAfp(String fecIngresoAfp) {
		this.fecIngresoAfp = fecIngresoAfp;
	}

	public Long getNumPersona() {
		return numPersona;
	}

	public void setNumPersona(Long numPersona) {
		this.numPersona = numPersona;
	}

	@Override
	public String toString() {
		return "ClienteUnicoDTO [nroCuentaInd=" + nroCuentaInd + ", cedula=" + cedula + ", tipoDoc=" + tipoDoc
				+ ", primerApell=" + primerApell + ", segundoApell=" + segundoApell + ", primerNombre=" + primerNombre
				+ ", segundoNombre=" + segundoNombre + ", fecNacimiento=" + fecNacimiento + ", genero=" + genero
				+ ", salarioBase=" + salarioBase + ", direccion=" + direccion + ", codCiudad=" + codCiudad
				+ ", telefono=" + telefono + ", apartAereo=" + apartAereo + ", ocupacion=" + ocupacion + ", estadoPers="
				+ estadoPers + ", subEstadPers=" + subEstadPers + ", uppCap=" + uppCap + ", fecIngreso=" + fecIngreso
				+ ", monCap=" + monCap + ", ultSalarReal=" + ultSalarReal + ", sldoTtalPesos=" + sldoTtalPesos
				+ ", nroPinPoint=" + nroPinPoint + ", eps=" + eps + ", codEstadoCivil=" + codEstadoCivil
				+ ", nacionalidad=" + nacionalidad + ", condicionInvalidez=" + condicionInvalidez + ", codOcupacion="
				+ codOcupacion + ", fecExpDocumento=" + fecExpDocumento + ", fecMuerte=" + fecMuerte + ", estadoCivil="
				+ estadoCivil + ", tipoDocumento=" + tipoDocumento + ", usuario=" + usuario + ", nroCuentaAfp="
				+ nroCuentaAfp + ", vlrSalarioBaseAfp=" + vlrSalarioBaseAfp + ", ultimoPeriodoAfp=" + ultimoPeriodoAfp
				+ ", fecIngresoAfp=" + fecIngresoAfp + ", numPersona=" + numPersona + ", localizaciones="
				+ localizaciones + "]";
	}

}

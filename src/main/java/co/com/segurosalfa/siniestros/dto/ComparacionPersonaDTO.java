package co.com.segurosalfa.siniestros.dto;

public class ComparacionPersonaDTO {

	private String descripcion;
	private String clienteUnico;
	private String afp;
	private boolean esDiferente;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClienteUnico() {
		return clienteUnico;
	}

	public void setClienteUnico(String clienteUnico) {
		this.clienteUnico = clienteUnico;
	}

	public String getAfp() {
		return afp;
	}

	public void setAfp(String afp) {
		this.afp = afp;
	}

	public boolean isEsDiferente() {
		return esDiferente;
	}

	public void setEsDiferente(boolean esDiferente) {
		this.esDiferente = esDiferente;
	}

}

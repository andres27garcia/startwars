package co.com.segurosalfa.siniestros.exception;

public class ModeloNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3560838655515585192L;

	public ModeloNotFoundException(String mensaje) {
		super(mensaje);
	}
}

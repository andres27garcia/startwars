package co.com.starwars.api.exception;

public class ModelNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3560838655515585192L;

	public ModelNotFoundException(String mensaje) {
		super(mensaje);
	}
}






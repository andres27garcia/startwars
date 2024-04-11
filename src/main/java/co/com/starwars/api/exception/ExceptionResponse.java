package co.com.starwars.api.exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponse {

	private LocalDateTime fecha;
	private String mensaje;
	private String detalles;

	public ExceptionResponse(LocalDateTime fechaHora, String mensaje, String detalles) {
		this.fecha = fechaHora;
		this.mensaje = mensaje;
		this.detalles = detalles;
	}

}

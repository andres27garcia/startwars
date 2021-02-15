package co.com.segurosalfa.siniestros.controller;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *** HealthCheckController clase controlador que verifica que la aplicación se encuentre arriba.
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/health")
public class HealthCheckController {

	@GetMapping
	public ResponseEntity<Object> healthService() {
		String message = String.format("Service Siniestros is up! %s", LocalDateTime.now());
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}

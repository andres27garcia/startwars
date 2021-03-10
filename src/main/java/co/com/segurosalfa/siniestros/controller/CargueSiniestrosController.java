package co.com.segurosalfa.siniestros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.impl.CargueArchivosImpl;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ** CargueSiniestrosController clase controlador que administra las peticiones
 * para la v1 CargueSiniestros
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 */
@RestController
@RequestMapping("/v1/carguesSiniestros")
public class CargueSiniestrosController {

	@Autowired
	CargueArchivosImpl service;

	/**
	 * cargar archivo xls para creación de siniestros por cargue.
	 * 
	 * @param file
	 * @param usuario
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operacion para el cargue de siniestros para su creacion automatica", notes = "La operación realiza el cague en el gestor de archivos y posteriormente realiza su sincronizacion con la BD")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping(value = "/enviarArchivo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Void> guardarArchivo(@RequestParam("adjunto") String file,
			@RequestParam(name = "usuario") String usuario) throws SiprenException {

		service.procesarSolicitud(file, usuario);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
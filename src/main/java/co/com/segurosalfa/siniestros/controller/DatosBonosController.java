package co.com.segurosalfa.siniestros.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.dto.SnrDatoBonoDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBono;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IDatoBonoService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ** DatosBonosController clase controlador que administra las peticiones para
 * la v1 de bonos
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/v1/bonos")
@XRayEnabled
public class DatosBonosController {

	@Autowired
	IDatoBonoService service;

	@Autowired
	ModelMapper modelMapper;

	/**
	 * Obtiene Bono pensional para una persona
	 * 
	 * @param numPersona
	 * @return
	 */
	@ApiOperation(value = "Operación de servicio que consulta el bono pensional para una persona", notes = "La operación retorna el bono pensional para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{numPersona}")
	public ResponseEntity<SnrDatoBonoDTO> obtenerPorPersona(@PathVariable("numPersona") Long numPersona) {
		SnrDatoBonoDTO obj = modelMapper.map(service.obtenerBonosPersona(numPersona), SnrDatoBonoDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	/**
	 * Actualiza Bono pensional para una persona
	 * 
	 * @param numPersona
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que actualiza el bono pensional para una persona", notes = "La operación actualiza el bono pensional para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<Void> actualizaBono(@RequestBody SnrDatoBonoDTO obj) throws SiprenException {
		SnrDatoBono ent = service.obtenerBonosPersona(obj.getNumPersona());
		if (ent == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}

		service.modificar(this.modelMapper.map(obj, SnrDatoBono.class));

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Crea Bono pensional para una persona.
	 *
	 * @param obj the obj
	 * @return the response entity
	 * @throws SiprenException the sipren exception
	 */
	@ApiOperation(value = "Operación de servicio que crea el bono pensional para una persona", notes = "La operación crea el bono pensional para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<Void> creaBono(@RequestBody SnrDatoBonoDTO obj) throws SiprenException {

		service.registrar(this.modelMapper.map(obj, SnrDatoBono.class));

		return new ResponseEntity<>(HttpStatus.OK);
	}
}

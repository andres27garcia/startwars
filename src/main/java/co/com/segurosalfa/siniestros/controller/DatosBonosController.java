package co.com.segurosalfa.siniestros.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.segurosalfa.siniestros.dto.SnrDatoBonoDTO;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.service.IDatoBonoService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ** DatosBonosController clase controlador que administra las peticiones
 * para la v1 de bonos
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/v1//bonos")
public class DatosBonosController {

	@Autowired
	private IDatoBonoService service;

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
}

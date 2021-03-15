package co.com.segurosalfa.siniestros.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.segurosalfa.siniestros.dto.SnrHilHistoriaIblDTO;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IHilHistoriaIblService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *** HistoriasIBLHIController clase controlador que administra las peticiones
 * para la v1 de HistoriasIBL
 *
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/v1/historiasLaboralesIBL")
//@XRayEnabled
public class HistoriasIBLHIController {
	
	@Autowired
	private IHilHistoriaIblService service;
	

	/**
	 * Lista la historia IBL asociada a una persona.
	 * 
	 * @param numPersona
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que consulta el detalle de una historia IBL por persona", notes = "La operación retorna una historia laboral IBL para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/consultaPersona/{numPersona}")
	public ResponseEntity<List<SnrHilHistoriaIblDTO>> listarPorPersona(@PathVariable("numPersona") Long numPersona)
			throws SiprenException {
		List<SnrHilHistoriaIblDTO> obj = service.listarPorPersona(numPersona);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	/**
	 * Elimina los periodos anteriores a una fecha asociados a una persona.
	 * 
	 * @param hilHistoriaIblDTO
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que elimina los periodos inferiores a una fecha dada", notes = "La operación elimina registros de una historia laboral IBL para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/depurarHistoria")
	public ResponseEntity<Boolean> depurarHistoriaLaboral(@RequestBody @Valid SnrHilHistoriaIblDTO hilHistoriaIblDTO) throws SiprenException{
		Boolean respuesta = service.depurarHistoriaLaboral(hilHistoriaIblDTO);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}

package co.com.segurosalfa.siniestros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.segurosalfa.siniestros.entity.SnrHilHistoriaIbl;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IHilHistoriaIblService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/historiasLaboralesIBL")
public class HistoriasIBLHIController {
	
	@Autowired
	private IHilHistoriaIblService service;

	@ApiOperation(value = "Operación de servicio que consulta el detalle de una historia IBL por persona", notes = "La operación retorna una historia laboral IBL para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/consultaPersona/{numPersona}")
	public ResponseEntity<List<SnrHilHistoriaIbl>> listarPorPersona(@PathVariable("numPersona") Long numPersona)
			throws SiprenException {
		List<SnrHilHistoriaIbl> obj = service.listarPorPersona(numPersona);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}

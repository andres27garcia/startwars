package co.com.segurosalfa.siniestros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.entity.ClfDatosDictamenes;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IDatosDictamenesService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/datosDictamenes")
@XRayEnabled
public class DatosDictamenesController {

	@Autowired
	IDatosDictamenesService service;

	@ApiOperation(value = "Operación de servicio que consulta un dictamen por el numero de tramite", notes = "La operación retorna el porcentaje de PCL para un tramite, 0.0 aplica cuando no se encuentran registros")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{numPersona}")
	public ResponseEntity<Double> listarPorId(@PathVariable("numPersona") Integer numPersona) throws SiprenException {

		Double pcjPcl;
		ClfDatosDictamenes obj = service.consultaPorPersona(numPersona);
		if (obj == null)
			pcjPcl = Double.valueOf("0.0");
		else
			pcjPcl = obj.getPcjPcl();

		return new ResponseEntity<>(pcjPcl, HttpStatus.OK);
	}
}

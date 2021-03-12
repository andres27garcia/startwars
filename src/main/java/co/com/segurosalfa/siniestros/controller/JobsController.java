package co.com.segurosalfa.siniestros.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.dto.ProcesosDTO;
import co.com.segurosalfa.siniestros.dto.ProgramacionJobDTO;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/v1/parametros/procesos")
@XRayEnabled
public class JobsController {

	@Autowired
	IParametricasService service;

	@ApiOperation(value = "Operaci\u00f3n de servicio que programa job a nivel de BD", notes = "La operaci�n no retorna informacion")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping(value = "/programarJob")
	public ResponseEntity<Void> programarJob(@Valid @RequestBody ProgramacionJobDTO dto) {

		Void result = service.programarJob(dto);
		return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Operaci\u00f3n de servicio que consulta el listado de procesos en BD", notes = "La operaci�n retorna todo el listado de procesos registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping(value = "/listadoProcesos")
	public ResponseEntity<List<ProcesosDTO>> listarProcesos() throws SiprenException {
		List<ProcesosDTO> lista = service.listarProcesos();

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operaci\u00f3n de servicio que consulta el detalle del proceso", notes = "La operaci�n retorna el detalle del proceso por idProceso")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping(value = "/{idProceso}")
	public ResponseEntity<List<ProcesosDTO>> detalleProceso(@PathVariable("idProceso") String idProceso)
			throws SiprenException {
		List<ProcesosDTO> lista = service.detalleProceso(idProceso);

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operaci\u00f3n de servicio que consulta el historico de modificaciones del proceso por idProceso", notes = "La operaci�n retorna el listado de modificaciones del proceso por idProceso")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping(value = "/historico/{idProceso}")
	public ResponseEntity<List<ProcesosDTO>> historicoProceso(@PathVariable("idProceso") String idProceso)
			throws SiprenException {
		List<ProcesosDTO> lista = service.historicoProceso(idProceso);

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operaci\u00f3n de servicio que cancela el job a nivel de BD", notes = "La operaci�n no retorna informacion")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping(value = "/cancelarJob")
	public ResponseEntity<Integer> cancelarJob(@Valid @RequestBody ProgramacionJobDTO dto) {

		Integer result = service.cancelarJob(dto);

		if (result == 0)
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_SERVER);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

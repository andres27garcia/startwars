package co.com.segurosalfa.siniestros.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import co.com.segurosalfa.siniestros.dto.SnrOrigenesDTO;
import co.com.segurosalfa.siniestros.entity.SnrOrigen;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IOrigenesService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/parametros/origenes")
public class OrigenesController {

	@Autowired
	private IOrigenesService service;
	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "operación de servicio que consulta el listado de los origenes", notes = "La operación retorna todas origenes registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrOrigenesDTO>> listar() throws SiprenException {
		List<SnrOrigenesDTO> lista = service.listar().stream().map(n -> this.modelMapper.map(n, SnrOrigenesDTO.class))
				.collect(Collectors.toList());
		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "operación de servicio que consulta los origenes de siniestro por ID", notes = "La operación retorna un origen de siniestro por ID registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrOrigenesDTO> listarPorId(@PathVariable("id") Integer id) throws SiprenException {
		SnrOrigenesDTO obj = this.modelMapper.map(service.listarPorId(id), SnrOrigenesDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "operación de servicio que registra un origenes", notes = "La operación registra el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrOrigenesDTO> registrar(@Valid @RequestBody SnrOrigenesDTO dto) throws SiprenException {
		SnrOrigen objSave = service.registrar(this.modelMapper.map(dto, SnrOrigen.class));
		SnrOrigenesDTO obj = this.modelMapper.map(objSave, SnrOrigenesDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "operación de servicio que actualiza un origenes", notes = "La operación actualiza el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrOrigenesDTO> modificar(@Valid @RequestBody SnrOrigenesDTO dto) throws SiprenException {
		SnrOrigen objSave = service.modificar(this.modelMapper.map(dto, SnrOrigen.class));
		SnrOrigenesDTO obj = this.modelMapper.map(objSave, SnrOrigenesDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}

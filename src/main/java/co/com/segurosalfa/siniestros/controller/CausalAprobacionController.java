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

import co.com.segurosalfa.siniestros.dto.SnrCausaSiniestroDTO;
import co.com.segurosalfa.siniestros.dto.SnrCausalAprobacionDTO;
import co.com.segurosalfa.siniestros.entity.SnrCausalAprobacion;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.ICausalAprobacionService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/parametros/causalAprobacion")
public class CausalAprobacionController {

	@Autowired
	private ICausalAprobacionService service;
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Listar todos los registros asociados
	 * 
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que consulta el listado de las causales de aprobación", notes = "La operación retorna todas las causales de aprobación registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrCausalAprobacionDTO>> listar() throws SiprenException {
		List<SnrCausalAprobacionDTO> lista = service.listar().stream()
				.map(n -> this.modelMapper.map(n, SnrCausalAprobacionDTO.class)).collect(Collectors.toList());
		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Listar por id.
	 * 
	 * @param id
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que consulta las causales de aprobacion por ID", notes = "La operación retorna una causal de aprobación por ID registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrCausalAprobacionDTO> listarPorId(@PathVariable("id") Long id) throws SiprenException {
		SnrCausalAprobacionDTO obj = this.modelMapper.map(service.listarPorId(id), SnrCausalAprobacionDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	/**
	 * Registrar CausalAprobacion
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que registra una nueva causal de aprobación", notes = "La operación registra el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrCausalAprobacionDTO> registrar(@Valid @RequestBody SnrCausaSiniestroDTO dto)
			throws SiprenException {
		SnrCausalAprobacion objSave = service.registrar(this.modelMapper.map(dto, SnrCausalAprobacion.class));
		SnrCausalAprobacionDTO obj = this.modelMapper.map(objSave, SnrCausalAprobacionDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	/**
	 * Modificar CausalAprobacion
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que actualiza una causal de aprobación", notes = "La operación actualiza el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrCausalAprobacionDTO> modificar(@Valid @RequestBody SnrCausalAprobacionDTO dto)
			throws SiprenException {
		SnrCausalAprobacion objSave = service.modificar(this.modelMapper.map(dto, SnrCausalAprobacion.class));
		SnrCausalAprobacionDTO obj = this.modelMapper.map(objSave, SnrCausalAprobacionDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}

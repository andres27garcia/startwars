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

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.dto.SnrCausaSiniestroDTO;
import co.com.segurosalfa.siniestros.entity.SnrCausaSiniestro;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.ICausaSiniestroService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ** CausasSiniestrosController clase controlador que administra las peticiones
 * para la v1 CausasSiniestros
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/v1/parametros/causasSiniestros")
@XRayEnabled
public class CausasSiniestrosController {

	@Autowired
	private ICausaSiniestroService service;
	@Autowired
	private ModelMapper modelMapper;

	
	/**
	 * Listar todos los registros asociados
	 * 
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que consulta el listado de las causas de siniestros", notes = "La operación retorna todas las causas de siniestros registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrCausaSiniestroDTO>> listar() throws SiprenException {
		List<SnrCausaSiniestroDTO> lista = service.listar().stream()
				.map(n -> this.modelMapper.map(n, SnrCausaSiniestroDTO.class)).collect(Collectors.toList());
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
	@ApiOperation(value = "operación de servicio que consulta las causas de siniestros por ID", notes = "La operación retorna una causa de siniestros por ID registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrCausaSiniestroDTO> listarPorId(@PathVariable("id") Integer id) throws SiprenException {
		SnrCausaSiniestroDTO obj = this.modelMapper.map(service.listarPorId(id), SnrCausaSiniestroDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	/**
	 * Registrar CausaSiniestro
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que registra una nueva causa de siniestro", notes = "La operación registra el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrCausaSiniestroDTO> registrar(@Valid @RequestBody SnrCausaSiniestroDTO dto)
			throws SiprenException {
		SnrCausaSiniestro objSave = service.registrar(this.modelMapper.map(dto, SnrCausaSiniestro.class));
		SnrCausaSiniestroDTO obj = this.modelMapper.map(objSave, SnrCausaSiniestroDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	/**
	 * Modificar CausaSiniestro
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que actualiza una causa de siniestro", notes = "La operación actualiza el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrCausaSiniestroDTO> modificar(@Valid @RequestBody SnrCausaSiniestroDTO dto)
			throws SiprenException {
		SnrCausaSiniestro objSave = service.modificar(this.modelMapper.map(dto, SnrCausaSiniestro.class));
		SnrCausaSiniestroDTO obj = this.modelMapper.map(objSave, SnrCausaSiniestroDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

}

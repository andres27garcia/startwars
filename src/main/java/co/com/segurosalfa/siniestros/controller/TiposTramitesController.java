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

import co.com.segurosalfa.siniestros.dto.SnrTipoDTO;
import co.com.segurosalfa.siniestros.entity.SnrTipo;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.ISnrTipoService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ** TiposTramitesController clase controlador que administra las peticiones
 * para la v1 de TiposTramites 
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/v1/parametros/tiposTramite")
//@XRayEnabled
public class TiposTramitesController {

	@Autowired
	private ISnrTipoService service;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Listar todos los registros asociados.
	 * 
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que consulta el listado de los tipos de tramites", notes = "La operación retorna todas los tipos de tramites registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrTipoDTO>> listar() throws SiprenException {
		List<SnrTipoDTO> lista = service.listar().stream().map(n -> this.modelMapper.map(n, SnrTipoDTO.class))
				.collect(Collectors.toList());
		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	
	/**
	 * Listar por Id.
	 * 
	 * @param id
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que consulta los tipos de tramite por ID", notes = "La operación retorna un tipo de tramite por ID registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrTipoDTO> listarPorId(@PathVariable("id") Integer id) throws SiprenException {
		SnrTipoDTO obj = this.modelMapper.map(service.listarPorId(id), SnrTipoDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	/**
	 * Registrar un Tipo
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que registra un tipo de tramite", notes = "La operación registra el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrTipoDTO> registrar(@Valid @RequestBody SnrTipoDTO dto) throws SiprenException {
		SnrTipo objSave = service.registrar(this.modelMapper.map(dto, SnrTipo.class));
		SnrTipoDTO obj = this.modelMapper.map(objSave, SnrTipoDTO.class);

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	/**
	 * Modificar un tipo
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "operación de servicio que actualiza un tipo de tramite", notes = "La operación actualiza el parametro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrTipoDTO> modificar(@Valid @RequestBody SnrTipoDTO dto) throws SiprenException {
		SnrTipo objSave = service.modificar(this.modelMapper.map(dto, SnrTipo.class));
		SnrTipoDTO obj = this.modelMapper.map(objSave, SnrTipoDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

}
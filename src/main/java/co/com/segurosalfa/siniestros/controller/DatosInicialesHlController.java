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

import co.com.segurosalfa.siniestros.dto.SnrHilDatoInicialDTO;
import co.com.segurosalfa.siniestros.entity.SnrHilDatoInicial;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IHilDatoInicialService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ** DatosInicialesHlController clase controlador que administra las peticiones
 * para la v1 de Datos Iniciales HL
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/v1/historiasLaborales")
//@XRayEnabled
public class DatosInicialesHlController {

	@Autowired
	private IHilDatoInicialService service;
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * listar todos los registros asociados a un numero de persona
	 *
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que consulta el listado de todos las historias laborales para una persona", notes = "La operación retorna todas las historias laborales registradas en la base de datos que coinciden con el numero de una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrHilDatoInicialDTO>> listar(@PathVariable("numPersona") Long numPersona) throws SiprenException {
		List<SnrHilDatoInicial> lista = service.listarPorPersona(numPersona);
		
		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
				
		List<SnrHilDatoInicialDTO> listaDto = lista.stream()
		.map(n -> this.modelMapper.map(n, SnrHilDatoInicialDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(listaDto, HttpStatus.OK);
	}
	
	/**
	 * listar todos los registros asociados 
	 *
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que consulta el listado de todos las historias laborales", notes = "La operación retorna todas las historias laborales registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrHilDatoInicialDTO>> listarPorPersona() throws SiprenException {
		List<SnrHilDatoInicialDTO> lista = service.listar() 
				.stream()
				.map(n -> this.modelMapper.map(n, SnrHilDatoInicialDTO.class)).collect(Collectors.toList());
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
	@ApiOperation(value = "Operación de servicio que consulta el detalle de una historia laboral por su ID", notes = "La operación retorna una historia laboral por su ID registrada en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrHilDatoInicialDTO> listarPorId(@PathVariable("id") Long id) throws SiprenException {
		SnrHilDatoInicialDTO obj = this.modelMapper.map(service.listarPorId(id), SnrHilDatoInicialDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
	}

	/**
	 * Registrar un periodo de historia laboral asociado a una persona
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que registra una nueva historia laboral", notes = "La operación registra la historia laboral en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrHilDatoInicialDTO> registrar(@Valid @RequestBody SnrHilDatoInicialDTO dto)
			throws SiprenException {
		SnrHilDatoInicial objSave = service.registrar(this.modelMapper.map(dto, SnrHilDatoInicial.class));
		SnrHilDatoInicialDTO obj = this.modelMapper.map(objSave, SnrHilDatoInicialDTO.class);

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	/**
	 * Modificar un periodo de historia laboral asociado a una persona
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que actualiza una nueva historia laboral", notes = "La operación actualiza una nueva historia laboral en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrHilDatoInicialDTO> modificar(@Valid @RequestBody SnrHilDatoInicialDTO dto)
			throws SiprenException {
		SnrHilDatoInicial objSave = service.modificar(this.modelMapper.map(dto, SnrHilDatoInicial.class));
		SnrHilDatoInicialDTO obj = this.modelMapper.map(objSave, SnrHilDatoInicialDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}

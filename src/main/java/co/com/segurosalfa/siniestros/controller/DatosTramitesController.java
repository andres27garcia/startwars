package co.com.segurosalfa.siniestros.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ActualizaEstadoSiniestroDTO;
import co.com.segurosalfa.siniestros.dto.FiltroTramitesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrComentarioTramiteDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoTramiteDTO;
import co.com.segurosalfa.siniestros.entity.SnrComentarioTramite;
import co.com.segurosalfa.siniestros.entity.SnrDatoTramite;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IComentarioTramiteService;
import co.com.segurosalfa.siniestros.service.IDatoTramiteService;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/datosTramites")
public class DatosTramitesController {

	@Autowired
	private IDatoTramiteService service;

	@Autowired
	private IComentarioTramiteService serviceComentarios;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los datos de tramites registrados", notes = "La operación retorna todos los datos de tramites registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrDatoTramiteDTO>> listar() throws SiprenException {
		List<SnrDatoTramiteDTO> lista = service.listar().stream().map(tr -> 
		modelMapper.map(tr, SnrDatoTramiteDTO.class)).collect(Collectors.toList());

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	

	@ApiOperation(value = "Operación de servicio que consulta un datos de tramite por ID", notes = "La operación retorna un datos de tramite por ID registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/consultaId/{id}")
	public ResponseEntity<SnrDatoTramiteDTO> listarPorId(@PathVariable("id") Long id) throws SiprenException {
		SnrDatoTramiteDTO obj = service.consultarPorId(id);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un datos de tramite por filtros", notes = "La operación retorna los tramites dependiendo de los campos seleccionados")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/paginadosPorFiltro")
	public ResponseEntity<ResponsePageableDTO> listarPorFiltro(@Valid @RequestBody FiltroTramitesDTO dto,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws JsonProcessingException, ServiceException, SiprenException {
		
		Pageable paging = PageRequest.of(page, size);
		ResponsePageableDTO response = service.listarPorFiltro(dto, paging);

		if (Objects.isNull(response))
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un datos de tramite por numero de siniestro", notes = "La operación retorna un datos de tramite por numero de siniestro registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/consultaSiniestro/{id}")
	public ResponseEntity<List<SnrDatoTramiteDTO>> listarPorSiniestro(@PathVariable("id") Long numSiniestro)
			throws SiprenException {
		List<SnrDatoTramiteDTO> obj = service.listarDatosXSiniestro(numSiniestro);
		if (obj == null || obj.isEmpty()) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un datos de tramite por numero de persona", notes = "La operación retorna un datos de tramite por numero de persona registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/consultaPersona/{numPersona}")
	public ResponseEntity<List<SnrDatoTramiteDTO>> listarPorPersona(@PathVariable("numPersona") Long numPersona)
			throws SiprenException {
		List<SnrDatoTramiteDTO> obj = service.listarDatosPorPersona(numPersona);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo tramite", notes = "La operación registra el tramite en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrDatoTramiteDTO> registrar(@Valid @RequestBody SnrDatoTramiteDTO dto)
			throws SiprenException {

		SnrDatoTramite obj = service.registrar(modelMapper.map(dto, SnrDatoTramite.class));

		obj.setIdTramite(service.ultimoTramiteXSiniestro(dto.getSiniestro().getIdSiniestro()));
		dto = modelMapper.map(obj, SnrDatoTramiteDTO.class);

		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza un tramite", notes = "La operación actualiza el tramite en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrDatoTramiteDTO> modificar(@Valid @RequestBody SnrDatoTramiteDTO dto)
			throws SiprenException {
		SnrDatoTramite obj = service.modificar(modelMapper.map(dto, SnrDatoTramite.class));
		dto = modelMapper.map(obj, SnrDatoTramiteDTO.class);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta los comentarios por tramite", notes = "La operación retorna el listado de comentarios por tramite registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/comentarios/{numTramite}")
	public ResponseEntity<List<SnrComentarioTramiteDTO>> listarComentarios(@PathVariable("numTramite") Long numTramite)
			throws SiprenException {
		List<SnrComentarioTramiteDTO> obj = serviceComentarios.listarDatosPorTramite(numTramite);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo comentario de tramite", notes = "La operación registra el comentario en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/comentarios")
	public ResponseEntity<Void> registrarComentarios(@Valid @RequestBody List<SnrComentarioTramiteDTO> dto)
			throws SiprenException {

		for (SnrComentarioTramiteDTO x : dto) {
			serviceComentarios.registrar(modelMapper.map(x, SnrComentarioTramite.class));
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza el estado de un tramite", notes = "La operación actualiza el estado de un tramite en base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/actualizarEstadoTramites")
	public ResponseEntity<Void> actualizarEstadoTramite(@Valid @RequestBody ActualizaEstadoSiniestroDTO dto)
			throws SiprenException {
		service.actualizaEstadoTramite(dto.getId(), dto.getCodEstado());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

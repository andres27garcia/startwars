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

import co.com.segurosalfa.siniestros.dto.SnrAbogadoDTO;
import co.com.segurosalfa.siniestros.dto.SnrComentarioProcesoJurDTO;
import co.com.segurosalfa.siniestros.dto.SnrInstanciaJuridicaDTO;
import co.com.segurosalfa.siniestros.dto.SnrProcesoJuridicoDTO;
import co.com.segurosalfa.siniestros.entity.SnrAbogado;
import co.com.segurosalfa.siniestros.entity.SnrComentarioProcesoJur;
import co.com.segurosalfa.siniestros.entity.SnrInstanciaJuridica;
import co.com.segurosalfa.siniestros.entity.SnrProcesoJuridico;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IAbogadoService;
import co.com.segurosalfa.siniestros.service.IComentarioProcesoJuridicoService;
import co.com.segurosalfa.siniestros.service.IInstanciaJuridicaService;
import co.com.segurosalfa.siniestros.service.IProcesoJuridicoService;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/procesosJuridicos")
public class ProcesosJuridicosController {

	@Autowired
	IComentarioProcesoJuridicoService serviceComentarios;

	@Autowired
	IAbogadoService serviceAbogados;

	@Autowired
	IProcesoJuridicoService serviceProceso;

	@Autowired
	IInstanciaJuridicaService serviceInsJur;

	@Autowired
	ModelMapper modelMapper;

	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los abogados", notes = "La operación retorna todos los abogados registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/abogados")
	public ResponseEntity<List<SnrAbogadoDTO>> listarAbogados() throws SiprenException {
		List<SnrAbogadoDTO> lista = serviceAbogados.listar().stream()
				.map(n -> this.modelMapper.map(n, SnrAbogadoDTO.class)).collect(Collectors.toList());

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un abogado por ID", notes = "La operación retorna un abogado por ID registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/abogados/{id}")
	public ResponseEntity<SnrAbogadoDTO> listarAbogadosPorId(@PathVariable("id") Long id) throws SiprenException {
		SnrAbogadoDTO obj = this.modelMapper.map(serviceAbogados.listarPorId(id), SnrAbogadoDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo abogado", notes = "La operación registra el abogado en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/abogados")
	public ResponseEntity<SnrAbogadoDTO> registrarAbogados(@Valid @RequestBody SnrAbogadoDTO dto)
			throws SiprenException {
		SnrAbogadoDTO obj = this.modelMapper.map(serviceAbogados.registrar(modelMapper.map(dto, SnrAbogado.class)),
				SnrAbogadoDTO.class);

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza un abogado", notes = "La operación actualiza el abogado en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping("/abogados")
	public ResponseEntity<SnrAbogadoDTO> modificarAbogados(@Valid @RequestBody SnrAbogadoDTO dto)
			throws SiprenException {
		SnrAbogadoDTO obj = this.modelMapper.map(serviceAbogados.modificar(modelMapper.map(dto, SnrAbogado.class)),
				SnrAbogadoDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta los comentarios por proceso juridico", notes = "La operación retorna el listado de comentarios por proceso juridico registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/comentarios/{id}")
	public ResponseEntity<List<SnrComentarioProcesoJurDTO>> listarComentariosJuridicosPorProceso(
			@PathVariable("id") Long idProcesoJur) throws SiprenException {
		List<SnrComentarioProcesoJurDTO> obj = serviceComentarios.listarPorProcesoJuridico(idProcesoJur).stream()
				.map(n -> this.modelMapper.map(n, SnrComentarioProcesoJurDTO.class)).collect(Collectors.toList());
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo comentario de proceso juridico", notes = "La operación registra el comentario en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/comentarios")
	public ResponseEntity<SnrComentarioProcesoJurDTO> registrarComentariosJuridicos(
			@Valid @RequestBody SnrComentarioProcesoJurDTO dto) throws SiprenException {
		SnrComentarioProcesoJurDTO obj = this.modelMapper.map(
				serviceComentarios.registrar(modelMapper.map(dto, SnrComentarioProcesoJur.class)),
				SnrComentarioProcesoJurDTO.class);

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los procesos juridicos", notes = "La operación retorna todos los procesos juridicos registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrProcesoJuridicoDTO>> listarProcesoJuridico() throws SiprenException {
		List<SnrProcesoJuridicoDTO> lista = serviceProceso.listar().stream()
				.map(n -> this.modelMapper.map(n, SnrProcesoJuridicoDTO.class)).collect(Collectors.toList());

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de procesos juridicos para un Afiliado", notes = "La operación retorna todos los procesos juridicos registradas en la base de datos para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/afiliado/{numPersona}")
	public ResponseEntity<List<SnrProcesoJuridicoDTO>> listarProcesoJuridicoPorPersona(
			@PathVariable("numPersona") Long persona) throws SiprenException {
		List<SnrProcesoJuridicoDTO> lista = serviceProceso.listarPorPersona(persona).stream()
				.map(n -> this.modelMapper.map(n, SnrProcesoJuridicoDTO.class)).collect(Collectors.toList());

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un proceso juridico por ID", notes = "La operación retorna un proceso juridico por ID registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrProcesoJuridicoDTO> listarProcesosJuridicosPorId(@PathVariable("id") Integer id)
			throws SiprenException {
		SnrProcesoJuridicoDTO obj = this.modelMapper.map(serviceProceso.listarPorId(id), SnrProcesoJuridicoDTO.class);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo proceos juridico", notes = "La operación registra el proceso juridico en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrProcesoJuridicoDTO> registrarAbogados(@Valid @RequestBody SnrProcesoJuridicoDTO dto)
			throws SiprenException {
		SnrProcesoJuridicoDTO obj = this.modelMapper.map(
				serviceProceso.registrar(modelMapper.map(dto, SnrProcesoJuridico.class)), SnrProcesoJuridicoDTO.class);

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza un proceso juridico", notes = "La operación actualiza el proceso juridico en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrProcesoJuridicoDTO> modificarAbogados(@Valid @RequestBody SnrProcesoJuridicoDTO dto)
			throws SiprenException {
		SnrProcesoJuridicoDTO obj = this.modelMapper.map(
				serviceProceso.modificar(modelMapper.map(dto, SnrProcesoJuridico.class)), SnrProcesoJuridicoDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de instancias juridicas por proceso juridico", notes = "La operación retorna un listado de instancias juridicas por proceso juridico registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/instanciasJuridicas/{numProcesoJur}")
	public ResponseEntity<List<SnrInstanciaJuridicaDTO>> listarInstanciaJuridicasPorProceso(
			@PathVariable("numProcesoJur") Long numProcesoJur) throws SiprenException {
		List<SnrInstanciaJuridicaDTO> obj = serviceInsJur.listarPorProcesoJuridico(numProcesoJur).stream()
				.map(n -> this.modelMapper.map(n, SnrInstanciaJuridicaDTO.class)).collect(Collectors.toList());
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de instancias juridicas por proceso juridic y tipo de instancia", notes = "La operación retorna un listado de instancias juridicas por proceso juridico y tipo de instancia registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/instanciasJuridicas/{numProcesoJur}/{idTipoInstancia}")
	public ResponseEntity<List<SnrInstanciaJuridicaDTO>> listarInstanciaJuridicasPorProceso(
			@PathVariable("numProcesoJur") Long numProcesoJur, @PathVariable("idTipoInstancia") Long idTipoInstancia)
			throws SiprenException {
		List<SnrInstanciaJuridicaDTO> obj = serviceInsJur
				.listarPorProcesoJuridicoTipoInstancia(numProcesoJur, idTipoInstancia).stream()
				.map(n -> this.modelMapper.map(n, SnrInstanciaJuridicaDTO.class)).collect(Collectors.toList());
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra una nueva instancia juridica", notes = "La operación registra la instancia juridica en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/instanciasJuridicas")
	public ResponseEntity<SnrInstanciaJuridicaDTO> registrarAbogados(@Valid @RequestBody SnrInstanciaJuridicaDTO dto)
			throws SiprenException {
		SnrInstanciaJuridicaDTO obj = this.modelMapper.map(
				serviceInsJur.registrar(modelMapper.map(dto, SnrInstanciaJuridica.class)),
				SnrInstanciaJuridicaDTO.class);

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza una instancia juridica", notes = "La operación actualiza una instancia juridica en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping("/instanciasJuridicas")
	public ResponseEntity<SnrInstanciaJuridicaDTO> modificarAbogados(@Valid @RequestBody SnrInstanciaJuridicaDTO dto)
			throws SiprenException {
		SnrInstanciaJuridicaDTO obj = this.modelMapper.map(
				serviceInsJur.modificar(modelMapper.map(dto, SnrInstanciaJuridica.class)),
				SnrInstanciaJuridicaDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}

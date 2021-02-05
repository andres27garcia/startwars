package co.com.segurosalfa.siniestros.controller;

import java.util.List;

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
@RequestMapping("/procesosJuridicos")
public class ProcesosJuridicosController {

	@Autowired
	private IComentarioProcesoJuridicoService serviceComentarios;

	@Autowired
	private IAbogadoService serviceAbogados;

	@Autowired
	private IProcesoJuridicoService serviceProceso;

	@Autowired
	private IInstanciaJuridicaService serviceInsJur;
	
	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los abogados", notes = "La operación retorna todos los abogados registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/abogados")
	public ResponseEntity<List<SnrAbogado>> listarAbogados() throws SiprenException {
		List<SnrAbogado> lista = serviceAbogados.listar();

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un abogado por ID", notes = "La operación retorna un abogado por ID registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/abogados/{id}")
	public ResponseEntity<SnrAbogado> listarAbogadosPorId(@PathVariable("id") Long id) throws SiprenException {
		SnrAbogado obj = serviceAbogados.listarPorId(id);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo abogado", notes = "La operación registra el abogado en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/abogados")
	public ResponseEntity<SnrAbogado> registrarAbogados(@Valid @RequestBody SnrAbogadoDTO dto) throws SiprenException {
		SnrAbogado obj = serviceAbogados.registrar(modelMapper.map(dto, SnrAbogado.class));

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza un abogado", notes = "La operación actualiza el abogado en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping("/abogados")
	public ResponseEntity<SnrAbogado> modificarAbogados(@Valid @RequestBody SnrAbogadoDTO dto) throws SiprenException {
		SnrAbogado obj = serviceAbogados.modificar(modelMapper.map(dto, SnrAbogado.class));
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta los comentarios por proceso juridico", notes = "La operación retorna el listado de comentarios por proceso juridico registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/comentarios/{id}")
	public ResponseEntity<List<SnrComentarioProcesoJur>> listarComentariosJuridicosPorProceso(
			@PathVariable("id") Long idProcesoJur) throws SiprenException {
		List<SnrComentarioProcesoJur> obj = serviceComentarios.listarPorProcesoJuridico(idProcesoJur);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo comentario de proceso juridico", notes = "La operación registra el comentario en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/comentarios")
	public ResponseEntity<SnrComentarioProcesoJur> registrarComentariosJuridicos(
			@Valid @RequestBody SnrComentarioProcesoJurDTO dto) throws SiprenException {
		SnrComentarioProcesoJur obj = serviceComentarios.registrar(modelMapper.map(dto, SnrComentarioProcesoJur.class));

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los procesos juridicos", notes = "La operación retorna todos los procesos juridicos registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrProcesoJuridico>> listarProcesoJuridico() throws SiprenException {
		List<SnrProcesoJuridico> lista = serviceProceso.listar();

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Operación de servicio que consulta el listado de procesos juridicos para un Afiliado", notes = "La operación retorna todos los procesos juridicos registradas en la base de datos para una persona")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/afiliado/{numPersona}")
	public ResponseEntity<List<SnrProcesoJuridico>> listarProcesoJuridicoPorPersona(@PathVariable("numPersona") Long persona) throws SiprenException {
		List<SnrProcesoJuridico> lista = serviceProceso.listarPorPersona(persona);

		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un proceso juridico por ID", notes = "La operación retorna un proceso juridico por ID registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrProcesoJuridico> listarProcesosJuridicosPorId(@PathVariable("id") Integer id)
			throws SiprenException {
		SnrProcesoJuridico obj = serviceProceso.listarPorId(id);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo proceos juridico", notes = "La operación registra el proceso juridico en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrProcesoJuridico> registrarAbogados(@Valid @RequestBody SnrProcesoJuridicoDTO dto)
			throws SiprenException {
		SnrProcesoJuridico obj = serviceProceso.registrar(modelMapper.map(dto, SnrProcesoJuridico.class));

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza un proceso juridico", notes = "La operación actualiza el proceso juridico en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrProcesoJuridico> modificarAbogados(@Valid @RequestBody SnrProcesoJuridicoDTO dto)
			throws SiprenException {
		SnrProcesoJuridico obj = serviceProceso.modificar(modelMapper.map(dto, SnrProcesoJuridico.class));
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de instancias juridicas por proceso juridico", notes = "La operación retorna un listado de instancias juridicas por proceso juridico registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/instanciasJuridicas/{numProcesoJur}")
	public ResponseEntity<List<SnrInstanciaJuridica>> listarInstanciaJuridicasPorProceso(
			@PathVariable("numProcesoJur") Long numProcesoJur) throws SiprenException {
		List<SnrInstanciaJuridica> obj = serviceInsJur.listarPorProcesoJuridico(numProcesoJur);
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
	public ResponseEntity<List<SnrInstanciaJuridica>> listarInstanciaJuridicasPorProceso(
			@PathVariable("numProcesoJur") Long numProcesoJur, @PathVariable("idTipoInstancia") Long idTipoInstancia)
			throws SiprenException {
		List<SnrInstanciaJuridica> obj = serviceInsJur.listarPorProcesoJuridicoTipoInstancia(numProcesoJur,
				idTipoInstancia);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra una nueva instancia juridica", notes = "La operación registra la instancia juridica en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/instanciasJuridicas")
	public ResponseEntity<SnrInstanciaJuridica> registrarAbogados(@Valid @RequestBody SnrInstanciaJuridicaDTO dto)
			throws SiprenException {
		SnrInstanciaJuridica obj = serviceInsJur.registrar(modelMapper.map(dto, SnrInstanciaJuridica.class));

		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza una instancia juridica", notes = "La operación actualiza una instancia juridica en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping("/instanciasJuridicas")
	public ResponseEntity<SnrInstanciaJuridica> modificarAbogados(@Valid @RequestBody SnrInstanciaJuridicaDTO dto)
			throws SiprenException {
		SnrInstanciaJuridica obj = serviceInsJur.modificar(modelMapper.map(dto, SnrInstanciaJuridica.class));
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}

package co.com.segurosalfa.siniestros.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ActualizaEstadoSiniestroDTO;
import co.com.segurosalfa.siniestros.dto.FiltroSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoPrevisionalDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasicoPrevisional;
import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.segurosalfa.siniestros.service.IResulPrcCreacionSiniestroService;
import co.com.segurosalfa.siniestros.service.ISnrDatoBasicoPrevisionalService;
import co.com.sipren.common.notifications.EmailService;
import co.com.sipren.common.notifications.EmailServiceUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ** DatoBasicoPrevisionalController clase controlador que administra las
 * peticiones para la v1 de siniestros
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 * 
 * @author Diego
 *
 */
//@RestController
//@RequestMapping("/v1/siniestros/previsional")
public class DatoBasicoPrevisionalController {

	@Autowired
	ISnrDatoBasicoPrevisionalService service;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	EmailServiceUtil emailUtil;
	@Autowired
	IParametricasService paramService;
	@Autowired
	IResulPrcCreacionSiniestroService serviceSini;

	/**
	 * Listar todos los registros asociados
	 * 
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los siniestros", notes = "La operación retorna todos los siniestros registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrDatoBasicoPrevisionalDTO>> listar() throws SiprenException {
		List<SnrDatoBasicoPrevisionalDTO> lista = service.listar().stream()
				.map(n -> this.modelMapper.map(n, SnrDatoBasicoPrevisionalDTO.class)).collect(Collectors.toList());
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
	@ApiOperation(value = "Operación de servicio que consulta un siniestros por ID", notes = "La operación retorna un siniestro por ID registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrDatoBasicoPrevisionalDTO> listarPorId(@PathVariable("id") Long id) throws SiprenException {
		SnrDatoBasicoPrevisional datoBasicoPrevisional = service.listarPorId(id);
		if (datoBasicoPrevisional == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		SnrDatoBasicoPrevisionalDTO obj = this.modelMapper.map(datoBasicoPrevisional,
				SnrDatoBasicoPrevisionalDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
	}

	/**
	 * Listar registros por idSiniestro, numPersona, numPoliza, numIdentificacion,
	 * origen, estado fecSiniestroInicial, fecSiniestroFinal
	 * 
	 * @param dto
	 * @param page
	 * @param size
	 * @return
	 * @throws SiprenException
	 * @throws JsonProcessingException
	 * @throws ServiceException
	 */
	@ApiOperation(value = "Operación de servicio que consulta datos de siniestros por filtros", notes = "La operación retorna los siniestros dependiendo de los campos seleccionados")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/paginadosPorFiltro")
	public ResponseEntity<ResponsePageableDTO> listarPorFiltro(@Valid @RequestBody FiltroSiniestrosDTO dto,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size)
			throws SiprenException, JsonProcessingException, ServiceException {

		Pageable paging = PageRequest.of(page, size);
		ResponsePageableDTO response = service.listarPorFiltro(dto, paging);

		if (Objects.isNull(response))
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * } Registrar Siniestro
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que registra un nuevo siniestro", notes = "La operación registra el siniestro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<SnrDatoBasicoPrevisionalDTO> registrar(@Valid @RequestBody SnrDatoBasicoPrevisionalDTO dto)
			throws SiprenException {
		SnrDatoBasicoPrevisional objSave = service.registrar(this.modelMapper.map(dto, SnrDatoBasicoPrevisional.class));
		SnrDatoBasicoPrevisionalDTO obj = this.modelMapper.map(objSave, SnrDatoBasicoPrevisionalDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	/**
	 * Modificar Siniestro
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que actualiza un siniestro", notes = "La operación actualiza el siniestro en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrDatoBasicoPrevisionalDTO> modificar(@Valid @RequestBody SnrDatoBasicoPrevisionalDTO dto)
			throws SiprenException {
		SnrDatoBasicoPrevisional objSave = service.modificar(this.modelMapper.map(dto, SnrDatoBasicoPrevisional.class));
		SnrDatoBasicoPrevisionalDTO obj = this.modelMapper.map(objSave, SnrDatoBasicoPrevisionalDTO.class);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	/**
	 * Reprocesa la creación de un trámite que no ha podido ser creado por
	 * diferencias en los datos del afiliado entre Cliente-Unico y AFP.
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 * @throws ParseException
	 */
	@ApiOperation(value = "Operación de servicio que simula cargue de Siniestro", notes = "La operación registra un siniestro pendiente por restricción de datos del Afiliado")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/procesarPendiente")
	public ResponseEntity<Long> crearTramitePendiente(@Valid @RequestBody ProcesarPendientesDTO dto)
			throws SiprenException, ParseException {
		service.limpiarTemporalesCargue(dto.getUsuario(), ParametroGeneralUtil.CONS_ORIGEN_REPROCESAR);
		service.crearSiniestroPendiente(dto);

		List<SnrResulPrcCreacionSiniestro> lista;

		try {

			lista = serviceSini.consultarPorProceso(ParametroGeneralUtil.CONS_ORIGEN_REPROCESAR);

			if (lista == null || lista.isEmpty())
				throw new ModeloNotFoundException("No se registran siniestros reprocesados");

			ByteArrayOutputStream outConv = new ByteArrayOutputStream();

			InputStream isConv = EnvioCorreoController.class.getResourceAsStream(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_TEMPLATE).getValor());

			Context context1 = new Context();
			context1.putVar("reporte", lista);
			JxlsHelper.getInstance().processTemplate(isConv, outConv, context1);

			MultipartFile[] multipartFiles = new MultipartFile[1];
			String fileName = paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_FILENAME)
					.getValor();
			multipartFiles[0] = new MockMultipartFile(fileName, fileName, ParametroGeneralUtil.CONS_CONTENT_EXCEL,
					outConv.toByteArray());

			EmailService email = new EmailService();
			Map<String, Object> params = new HashMap<>();

			email.setFrom(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_FROM).getValor());
			email.setSubject(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_SUBJECT).getValor());
			email.setTo(paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_TO).getValor());
			email.setTemplate(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_BODY);
			params.put("user", dto.getUsuario());
			email.setParams(params);
			emailUtil.notification(email, multipartFiles);

		} catch (Exception e) {
			throw new SiprenException(e.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Actualiza el estado de un siniestro
	 * 
	 * @param dto
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operación de servicio que actualiza el estado de un siniestro", notes = "La operación actualiza el estado de un siniestro en base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/actualizarEstadoSiniestros")
	public ResponseEntity<Void> actualizarEstadosSiniestros(@Valid @RequestBody ActualizaEstadoSiniestroDTO dto)
			throws SiprenException {
		service.actualizaEstadoSiniestro(dto.getId(), dto.getCodEstado());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

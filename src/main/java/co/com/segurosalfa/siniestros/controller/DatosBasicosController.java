
package co.com.segurosalfa.siniestros.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
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
import co.com.segurosalfa.siniestros.dto.FiltroSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoBasicoPrevisionalDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasicoPrevisional;
import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.segurosalfa.siniestros.service.IResulPrcCreacionSiniestroService;
import co.com.segurosalfa.siniestros.service.ISnrDatoBasicoPrevisionalService;
import co.com.sipren.common.bus.dto.Mail;
import co.com.sipren.common.util.EmailUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/siniestros")
public class DatosBasicosController {

	@Autowired
	private ISnrDatoBasicoPrevisionalService service;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	EmailUtil emailU;
	@Autowired
	IParametricasService paramService;
	@Autowired
	IResulPrcCreacionSiniestroService serviceSini;

	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los siniestros", notes = "La operación retorna todos los siniestros registradas en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping
	public ResponseEntity<List<SnrDatoBasicoDTO>> listar() throws SiprenException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<SnrDatoBasicoDTO> lista = service.listarSiniestros();
		if (lista != null && lista.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta un siniestros por ID", notes = "La operación retorna un siniestro por ID registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/{id}")
	public ResponseEntity<SnrDatoBasicoDTO> listarPorId(@PathVariable("id") Long id) throws SiprenException, JsonProcessingException, IllegalAccessException, InstantiationException, 
		IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ServiceException {
		SnrDatoBasicoDTO siniestro = service.listarPorSiniestro(id);
		if (siniestro == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}		
		return new ResponseEntity<>(siniestro, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Operación de servicio que consulta el listado de todos los siniestros paginados por parametros de size y page", notes = "La operación retorna todos los siniestros registradas en la base de datos que cumplan con las condiciones de paginado")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/paginados")
	public ResponseEntity<ResponsePageableDTO> listarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) throws SiprenException {

		Pageable paging = PageRequest.of(page, size);
		ResponsePageableDTO lista = service.listarPaginado(paging);

		if (Objects.isNull(lista))
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Operación de servicio que consulta datos de siniestros por filtros", notes = "La operación retorna los siniestros dependiendo de los campos seleccionados")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/paginadosPorFiltro")
	public ResponseEntity<ResponsePageableDTO> listarPorFiltro(@Valid @RequestBody FiltroSiniestrosDTO dto,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size)
			throws SiprenException {

		Pageable paging = PageRequest.of(page, size);
		ResponsePageableDTO response = service.listarPorFiltro(dto, paging);

		if (Objects.isNull(response))
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

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
					paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_TEMPLATE).getValor());

			Context context1 = new Context();
			context1.putVar("reporte", lista);
			JxlsHelper.getInstance().processTemplate(isConv, outConv, context1);

			InputStreamSource attachment = new ByteArrayResource(outConv.toByteArray());

			Mail mail = new Mail();
			mail.setFrom(paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_FROM).getValor());
			mail.setTo(paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_TO).getValor()
					.split(","));
			mail.setSubject(
					paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_SUBJECT).getValor());
			mail.setText(paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_BODY).getValor());
			mail.setFile(attachment);
			mail.setFileName(
					paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_REP_SIN_EMAIL_FILENAME).getValor());

			emailU.enviarMailAdjunto(mail);

		} catch (Exception e) {
			throw new SiprenException(e.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
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

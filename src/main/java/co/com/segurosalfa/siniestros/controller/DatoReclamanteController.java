package co.com.segurosalfa.siniestros.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ListadoReclamantesDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.dto.ReprocesoReclamantesDTO;
import co.com.segurosalfa.siniestros.dto.SnrComentarioReclamanteDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoReclamanteDTO;
import co.com.segurosalfa.siniestros.entity.SnrComentarioReclamante;
import co.com.segurosalfa.siniestros.entity.SnrDatoReclamante;
import co.com.segurosalfa.siniestros.entity.SnrDatoTramite;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IComentarioReclamanteService;
import co.com.segurosalfa.siniestros.service.IDatoReclamanteService;
import co.com.segurosalfa.siniestros.service.IDatoTramiteService;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.sipren.common.bus.dto.Mail;
import co.com.sipren.common.util.EmailUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/datosReclamantes")
public class DatoReclamanteController {

	@Autowired
	IDatoReclamanteService service;

	@Autowired
	IDatoTramiteService tramiteService;

	@Autowired
	IClienteUnicoService clienteUnicoService;

	@Autowired
	IComentarioReclamanteService comentariosService;

	@Autowired
	EmailUtil emailU;

	@Autowired
	IParametricasService paramService;

	@Autowired
	ModelMapper modelMapper;
	
	

	@ApiOperation(value = "Operación de servicio que consulta un datos de reclamantes por numero de siniestro y numero de tramite", notes = "La operación retorna un datos de reclamantes por numero de siniestro y numero de tramite registrado en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/consultaTramite/{numTramite}")
	public ResponseEntity<List<ListadoReclamantesDTO>> listarPorSiniestro(@PathVariable("numTramite") Long numTramite)
			throws SiprenException {
		List<ListadoReclamantesDTO> obj = service.listarPorTramite(numTramite);
		if (obj == null) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo reclamante", notes = "La operación registra el reclamante en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody SnrDatoReclamanteDTO dto) throws SiprenException {

		service.guardarReclamante(dto);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que asocia reclamante al trámite", notes = "La operación asocia un reclamante a un trámite, comprobando que el reclamante pase la coincidencia de datos con Cliente Unico")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/crearDatosReclamantes")
	public ResponseEntity<Void> crearDatosReclamantes(@Valid @RequestBody ProcesarPendientesDTO dto)
			throws SiprenException, JsonProcessingException, ServiceException {

		SnrDatoTramite tmpTramite = tramiteService.listarPorId(dto.getIdTramite());
		dto.setIdSolicitudAfp(tmpTramite.getIdSolicitudAfp());

		service.crearDatosReclamante(dto);

		List<ReprocesoReclamantesDTO> obj = service.consultaReprocesoReclamante(dto.getIdTramite(),
				dto.getNumPersona());

		if (obj != null && !obj.isEmpty()) {

			try {
				
				ByteArrayOutputStream outConv = new ByteArrayOutputStream();

				InputStream isConv = EnvioCorreoController.class.getResourceAsStream(paramService
						.parametroXNombre(ParametroGeneralUtil.CONS_PROC_RECL_SIN_EMAIL_TEMPLATE).getValor());

				Context context1 = new Context();

				context1.putVar("reporte", obj);
				JxlsHelper.getInstance().processTemplate(isConv, outConv, context1);

				InputStreamSource attachment = new ByteArrayResource(outConv.toByteArray());

				Mail mail = new Mail();
				mail.setFrom(
						paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_RECL_SIN_EMAIL_FROM).getValor());
				mail.setTo(paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_RECL_SIN_EMAIL_TO).getValor()
						.split(","));
				mail.setSubject(paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_RECL_SIN_EMAIL_SUBJECT)
						.getValor());
				mail.setText(
						paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_RECL_SIN_EMAIL_BODY).getValor());
				mail.setFile(attachment);
				mail.setFileName(paramService.parametroXNombre(ParametroGeneralUtil.CONS_PROC_RECL_SIN_EMAIL_FILENAME)
						.getValor());

				emailU.enviarMailAdjunto(mail);

			} catch (Exception e) {
				throw new SiprenException(e.getMessage());
			}
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Operación de servicio que actualiza un reclamante", notes = "La operación actualiza el reclamante en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PutMapping
	public ResponseEntity<SnrDatoReclamanteDTO> modificar(@Valid @RequestBody SnrDatoReclamanteDTO dto)
			throws SiprenException {
		
		SnrDatoReclamante datoReclamante = modelMapper.map(dto, SnrDatoReclamante.class);
		datoReclamante.setNumPersona(dto.getPersona().getNumPersona());
		SnrDatoReclamanteDTO datoReclamanteDTO = modelMapper.map(service.modificar(datoReclamante),
				SnrDatoReclamanteDTO.class);
	    
		return new ResponseEntity<>(datoReclamanteDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta los comentarios por reclamante", notes = "La operación retorna el listado de comentarios por reclamante registrados en la base de datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/comentarios/{idReclamante}")
	public ResponseEntity<List<SnrComentarioReclamanteDTO>> listarComentarios(
			@PathVariable("idReclamante") Long idReclamante) throws SiprenException {
		List<SnrComentarioReclamanteDTO> obj = comentariosService.listarDatosPorReclamante(idReclamante).stream()
				.map(n -> this.modelMapper.map(n, SnrComentarioReclamanteDTO.class)).collect(Collectors.toList());
		if (obj == null || obj.isEmpty()) {
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que registra un nuevo comentario de reclamante", notes = "La operación registra el comentario en base de datos y retorna el registro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 201, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping("/comentarios")
	public ResponseEntity<Void> registrarComentarios(@Valid @RequestBody List<SnrComentarioReclamanteDTO> dto)
			throws SiprenException {

		for (SnrComentarioReclamanteDTO comentarioDTO : dto) {
			comentariosService.guardarComentarios(comentarioDTO);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}

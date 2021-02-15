package co.com.segurosalfa.siniestros.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.com.segurosalfa.siniestros.dto.CargueSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.SnrResulPrcCreacionSiniestroDTO;
import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.segurosalfa.siniestros.service.IResulPrcCreacionSiniestroService;
import co.com.segurosalfa.siniestros.service.ISnrDatoBasicoPrevisionalService;
import co.com.sipren.common.bus.dto.Mail;
import co.com.sipren.common.util.ClienteRestGenerico;
import co.com.sipren.common.util.DetalleArchivoResponse;
import co.com.sipren.common.util.DetalleCargueArchivoResponse;
import co.com.sipren.common.util.DetalleRegistroResponse;
import co.com.sipren.common.util.EmailUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.RegistroResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;



/**
 * ** CargueSiniestrosController clase controlador que administra las peticiones
 * para la v1 CargueSiniestros
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 */
@Log4j2
@RestController
@RequestMapping("/v1/carguesSiniestros")
public class CargueSiniestrosController {

	@Autowired
	private IParametricasService paramService;

	@Autowired
	private ClienteRestGenerico service;

	@Autowired
	private ISnrDatoBasicoPrevisionalService serviceSiniestro;

	@Autowired
	private IResulPrcCreacionSiniestroService serviceSini;

	@Autowired
	private EmailUtil emailU;

	@Autowired
	private ModelMapper modelMapper;

//	@Autowired
//	private LogServiceUtil logServiceUtil;

	
	/**
	 * cargar archivo xls para creación de siniestros por cargue.
	 * 
	 * @param file
	 * @param usuario
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operacion para el cargue de siniestros para su creacion automatica", notes = "La operación realiza el cague en el gestor de archivos y posteriormente realiza su sincronizacion con la BD")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@PostMapping(value = "/enviarArchivo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Void> guardarArchivo(@RequestParam("adjunto") MultipartFile file,
			@RequestParam(name = "usuario") String usuario) throws SiprenException {

//		LogService logService = new LogService();
//		logService.setService(Constants.SINIESTROS_BUSINESS_UNIT, Constants.SINIESTORS_MICROSERVICES);
//		logService.setServiceName("/carguesSiniestros/enviarArchivo");
//		logService.setIp("192.168.0.1");
//		logService.setAction(LogServiceEnum.GET);
//		logService.setUser("TEST");

		try {

			Object object = service.uploadFile(multipartToFile(file),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_GESTOR_ARCHIVOS).getValor(),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_GESTOR_ARCHIVOS_CARGUE).getValor()
							.replace(":v1",
									paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PRC_CARGUE_SINIESTROS)
											.getValor())
							.replace(":v2", ParametroGeneralUtil.CONS_SINIESTROS).replace(":v3", usuario),
					5000);
			if (object == null) {
				throw new SiprenException(ParametroGeneralUtil.ERROR_CARGUE);
			}

			JsonObject jsonO = new Gson().fromJson(object.toString(), JsonObject.class);

			DetalleCargueArchivoResponse archivo = new Gson().fromJson(jsonO.get("data").toString(),
					DetalleCargueArchivoResponse.class);

			if (archivo == null || archivo.getIdArchivoCargue() == null
					|| "".equalsIgnoreCase(archivo.getIdArchivoCargue().toString())) {
				throw new SiprenException(ParametroGeneralUtil.ERROR_RESPUESTA_CARGUE);
			}

			Thread.sleep(2000);
			Object objectDetail = service.executeApi(null, HttpMethod.GET,
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_GESTOR_ARCHIVOS).getValor(),
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_URL_GESTOR_ARCHIVOS_CONSULTA).getValor()
							.replace(":v1", archivo.getIdArchivoCargue().toString()),
					5000);
			if (objectDetail == null) {
				throw new SiprenException(ParametroGeneralUtil.ERROR_CONSULTA_DETALLE);
			}

			String json = new Gson().toJson(objectDetail);

			if (json == null || json.isEmpty()) {
				throw new SiprenException(ParametroGeneralUtil.ERROR_DETALLE_VACIO);
			}

			jsonO = new Gson().fromJson(json, JsonObject.class);

			DetalleArchivoResponse detalleArchivo = new Gson().fromJson(jsonO.get("data").toString(),
					DetalleArchivoResponse.class);

			if (detalleArchivo == null || detalleArchivo.getRegistroResponse() == null
					|| detalleArchivo.getRegistroResponse().isEmpty()) {
				throw new SiprenException(ParametroGeneralUtil.ERROR_RESPUESTA_CARGUE);
			}

			List<CargueSiniestrosDTO> listado = new ArrayList<>();
			if (detalleArchivo.getRegistroResponse() != null) {
				for (RegistroResponse registroResponse : detalleArchivo.getRegistroResponse()) {
					List<DetalleRegistroResponse> detalles = registroResponse.getDetalleRegistro();
					CargueSiniestrosDTO siniestro = new CargueSiniestrosDTO(registroResponse.getEstadoRegistro(),
							new Gson().toJson(registroResponse.getDetalleError()), registroResponse.getNumeroRegistro(),
							getValor(detalles, 0), getValor(detalles, 1), getValor(detalles, 2), getValor(detalles, 3));

					listado.add(siniestro);
				}
			}

			if (listado == null || listado.isEmpty())
				throw new ModeloNotFoundException("No se registran siniestros procesados");

			List<SnrResulPrcCreacionSiniestroDTO> listResults = new ArrayList<SnrResulPrcCreacionSiniestroDTO>();

			serviceSiniestro.limpiarTemporalesCargue(usuario, ParametroGeneralUtil.CONS_ORIGEN_CARGUE);

			for (CargueSiniestrosDTO cargueSiniestrosDTO : listado) {
				if (cargueSiniestrosDTO.getEstadoRegistro().equals("PROCESADO")
						&& (null == cargueSiniestrosDTO.getDetalleError()
								|| "".equals(cargueSiniestrosDTO.getDetalleError()))) {

					serviceSiniestro.procesarCargue(cargueSiniestrosDTO);
				} else {
					// Consolida en listado los registros con errores en el cargue
					SnrResulPrcCreacionSiniestroDTO gnrResult = new SnrResulPrcCreacionSiniestroDTO();
					gnrResult.setIdRegistro(cargueSiniestrosDTO.getNumeroRegistro() != null
							? Integer.parseInt(cargueSiniestrosDTO.getNumeroRegistro())
							: 0);
					gnrResult.setTipoSolicitud(cargueSiniestrosDTO.getTipoSolicitud());
					gnrResult.setTipoIdent(cargueSiniestrosDTO.getTipoDocumento());
					gnrResult.setNroIdent(cargueSiniestrosDTO.getDocumento() != null
							? Long.parseLong(cargueSiniestrosDTO.getDocumento())
							: 0);
					gnrResult.setEstadoRegistro(cargueSiniestrosDTO.getEstadoRegistro());
					gnrResult.setDetalleError(cargueSiniestrosDTO.getDetalleError());
					listResults.add(gnrResult);
				}
			}

			serviceSiniestro.crearSiniestroCargue(usuario, ParametroGeneralUtil.CONS_ORIGEN_CARGUE);

			// Consolida en listado los que se procesaron en BD para crear siniestro
			List<SnrResulPrcCreacionSiniestro> lista = serviceSini
					.consultarPorProceso(ParametroGeneralUtil.CONS_ORIGEN_CARGUE);
			for (SnrResulPrcCreacionSiniestro gnrResulPrcCreacionSiniestro : lista) {
				listResults.add(modelMapper.map(gnrResulPrcCreacionSiniestro, SnrResulPrcCreacionSiniestroDTO.class));
			}

			ByteArrayOutputStream outConv = new ByteArrayOutputStream();

			InputStream isConv = EnvioCorreoController.class.getResourceAsStream(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_TEMPLATE).getValor());

			Context context1 = new Context();

			// Envia a la plantilla listado con consolidados
			context1.putVar("reporte", listResults);
			JxlsHelper.getInstance().processTemplate(isConv, outConv, context1);

			InputStreamSource attachment = new ByteArrayResource(outConv.toByteArray());

			Mail mail = new Mail();
			mail.setFrom(paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_FROM).getValor());
			mail.setTo(paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_TO).getValor()
					.split(","));
			mail.setSubject(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_SUBJECT).getValor());
			mail.setText(paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_BODY).getValor());
			mail.setFile(attachment);
			mail.setFileName(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_FILENAME).getValor());

			emailU.enviarMailAdjunto(mail);

		} catch (Exception e) {
			throw new SiprenException(e.getMessage());
		} finally {
			/*
			 * try { logServiceUtil.sendLog("", logService); } catch (LogServiceHandler e) {
			 * log.error("Sucedio un error al registrar el evento de log", e); }
			 */
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Dada una posición obtiene el valor de la lista de respuesta del archivo de cargue.
	 * 
	 * @param detalleRegistro
	 * @param i
	 * @return
	 */
	private String getValor(List<DetalleRegistroResponse> detalleRegistro, int i) {
		try {
			return detalleRegistro.get(i).getValor();
		} catch (Exception e) {
			log.error("Error al obtener el valor " + i);
		}
		return null;
	}

	
	/**
	 * Guarda temporalmente el archivo que se esta cargando para poder ser procesado.
	 * 
	 * @param multipart
	 * @return
	 * @throws IOException
	 */
	public File multipartToFile(MultipartFile multipart) throws IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

}
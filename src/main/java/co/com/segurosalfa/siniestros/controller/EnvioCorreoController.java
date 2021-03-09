package co.com.segurosalfa.siniestros.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.segurosalfa.siniestros.dto.SnrResulPrcCreacionSiniestroDTO;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.segurosalfa.siniestros.service.IResulPrcCreacionSiniestroService;
import co.com.sipren.common.notifications.EmailService;
import co.com.sipren.common.notifications.EmailServiceUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *** EnvioCorreoController clase controlador que administra las peticiones para la
 * v1 de EnvioCorreo
 * 
 * @author diego.marin@segurosalfa.com.co
 * @version %I%, %G%
 *
 */
@RestController
@RequestMapping("/v1/enviosCorreos")
public class EnvioCorreoController {

	@Autowired
	EmailServiceUtil emailUtil;
	@Autowired
	IParametricasService service;
	@Autowired
	IResulPrcCreacionSiniestroService serviceSini;
	@Autowired
	ModelMapper modelMapper;

	/**
	 * Envio de correo para proceso automático informando el resultado del proceso.
	 * 
	 * @return
	 * @throws SiprenException
	 */
	@ApiOperation(value = "Operacion para el envio automatico del correo con los resultados del proceso automatico de creacion de siniestros", notes = "La operación envia un correo electronico con adjunto, el servicio no retorna respuesta")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/procesosAutomaticos")
	public ResponseEntity<Void> enviarCorreo() throws SiprenException {
		List<SnrResulPrcCreacionSiniestroDTO> lista;
		try {

			lista = serviceSini.listar().stream()
					.map(n -> this.modelMapper.map(n, SnrResulPrcCreacionSiniestroDTO.class))
					.collect(Collectors.toList());

			if (lista == null || lista.isEmpty())
				throw new ModeloNotFoundException("No se registran siniestros procesados");

			ByteArrayOutputStream outConv = new ByteArrayOutputStream();

			InputStream isConv = EnvioCorreoController.class.getResourceAsStream(
					service.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_AUT_EMAIL_TEMPLATE).getValor());

			Context context1 = new Context();
			context1.putVar("reporte", lista);
			JxlsHelper.getInstance().processTemplate(isConv, outConv, context1);

			MultipartFile[] multipartFiles = new MultipartFile[1];
			String fileName = service.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_AUT_EMAIL_FILENAME).getValor();
			multipartFiles[0] = new MockMultipartFile(fileName, fileName, ParametroGeneralUtil.CONS_CONTENT_EXCEL,
					outConv.toByteArray());

			EmailService email = new EmailService();
			Map<String, Object> params = new HashMap<>();

			email.setFrom(service.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_AUT_EMAIL_FROM).getValor());
			email.setSubject(service.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_AUT_EMAIL_SUBJECT).getValor());
			email.setTo(service.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_AUT_EMAIL_TO).getValor());
			email.setTemplate(service.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_AUT_EMAIL_BODY).getValor());
			params.put("user", "Automatico");
			email.setParams(params);

			emailUtil.notification(email, multipartFiles);

		} catch (Exception e) {
			throw new SiprenException(e.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}

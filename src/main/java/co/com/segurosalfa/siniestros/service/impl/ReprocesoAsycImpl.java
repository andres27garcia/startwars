package co.com.segurosalfa.siniestros.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import co.com.segurosalfa.siniestros.controller.EnvioCorreoController;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.entity.SnrCargueArchivos;
import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.ICargueArchivosService;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import co.com.segurosalfa.siniestros.service.IResulPrcCreacionSiniestroService;
import co.com.segurosalfa.siniestros.service.ISnrDatoBasicoPrevisionalService;
import co.com.sipren.common.notifications.EmailService;
import co.com.sipren.common.notifications.EmailServiceUtil;
import co.com.sipren.common.util.ParametroGeneralUtil;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestScope
@Component
public class ReprocesoAsycImpl {

	@Autowired
	ISnrDatoBasicoPrevisionalService service;
	@Autowired
	IParametricasService paramService;
	@Autowired
	IResulPrcCreacionSiniestroService serviceSini;
	@Autowired
	EmailServiceUtil emailUtil;
	@Autowired
	ICargueArchivosService carService;

	/**
	 * Procesar afiliado de manera asincronica.
	 *
	 * @param dto the dto
	 * @throws SiprenException the sipren exception
	 * @throws ParseException  the parse exception
	 */
	@Async
	public void procesarAfiliado(ProcesarPendientesDTO dto) throws SiprenException, ParseException {

		SnrCargueArchivos entCargue = new SnrCargueArchivos();

		entCargue.setEstado(ParametroGeneralUtil.CONS_EN_PROCESO);
		entCargue.setFecEjecucion(LocalDateTime.now());
		entCargue.setIdTipoCargue(ParametroGeneralUtil.CONS_ID_REPROCESO_AFILIADO);
		entCargue.setSubTipoCargue(
				paramService.parametroPorNombre(ParametroGeneralUtil.CONS_SUB_TIPO_REPROCESO).getValor());
		entCargue.setTipoCargue(paramService.parametroPorNombre(ParametroGeneralUtil.CONS_TIPO_REPROCESO).getValor());
		entCargue.setUsuario(dto.getUsuario());

		carService.registrar(entCargue);

		entCargue = carService.getCargueActivo(ParametroGeneralUtil.CONS_ID_CARGUE_SINIESTROS);

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

			entCargue.setEstado(ParametroGeneralUtil.CONS_PROCESADO);
			carService.modificar(entCargue);

		} catch (Exception e) {
			log.error(e);
			entCargue.setEstado(ParametroGeneralUtil.CONS_ERROR);
			carService.modificar(entCargue);
			throw new SiprenException(e.getMessage());
		}
	}
}

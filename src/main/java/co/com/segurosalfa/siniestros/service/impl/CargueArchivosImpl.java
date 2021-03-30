package co.com.segurosalfa.siniestros.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.com.segurosalfa.siniestros.controller.EnvioCorreoController;
import co.com.segurosalfa.siniestros.dto.CargueSiniestrosDTO;
import co.com.segurosalfa.siniestros.dto.SnrResulPrcCreacionSiniestroDTO;
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
import co.com.sipren.common.util.ClienteRestGenerico;
import co.com.sipren.common.util.DetalleArchivoResponse;
import co.com.sipren.common.util.DetalleCargueArchivoResponse;
import co.com.sipren.common.util.DetalleRegistroResponse;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.RegistroResponse;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestScope
@Component
public class CargueArchivosImpl {
	@Autowired
	IParametricasService paramService;

	@Autowired
	ClienteRestGenerico service;

	@Autowired
	ISnrDatoBasicoPrevisionalService serviceSiniestro;

	@Autowired
	IResulPrcCreacionSiniestroService serviceSini;

	@Autowired
	EmailServiceUtil emailUtil;

	@Autowired
	ICargueArchivosService carService;

	@Autowired
	ModelMapper modelMapper;

	/**
	 * Procesar solicitud.
	 *
	 * @param file    the file
	 * @param usuario the usuario
	 * @throws SiprenException the sipren exception
	 */
	@Async
	public void procesarSolicitud(String file, String usuario) throws SiprenException {
		SnrCargueArchivos entCargue = new SnrCargueArchivos();
		try {
			entCargue.setEstado(ParametroGeneralUtil.CONS_EN_PROCESO);
			entCargue.setFecEjecucion(LocalDateTime.now());
			entCargue.setIdTipoCargue(ParametroGeneralUtil.CONS_ID_CARGUE_SINIESTROS);
			entCargue.setSubTipoCargue(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_SUB_TIPO_CARGUE).getValor());
			entCargue.setTipoCargue(paramService.parametroPorNombre(ParametroGeneralUtil.CONS_TIPO_CARGUE).getValor());
			entCargue.setUsuario(usuario);

			carService.registrar(entCargue);

			entCargue = carService.getCargueActivo(ParametroGeneralUtil.CONS_ID_CARGUE_SINIESTROS);

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

			int cont = 0;

			for (CargueSiniestrosDTO cargueSiniestrosDTO : listado) {
				if (cargueSiniestrosDTO.getEstadoRegistro().equals("PROCESADO")
						&& (null == cargueSiniestrosDTO.getDetalleError()
								|| "".equals(cargueSiniestrosDTO.getDetalleError()))) {
					serviceSiniestro.procesarCargue(cargueSiniestrosDTO);
					cont++;
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

			entCargue.setAvance(String.valueOf(cont));
			carService.modificar(entCargue);

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
			context1.putVar("reporte", listResults);
			JxlsHelper.getInstance().processTemplate(isConv, outConv, context1);

			MultipartFile[] multipartFiles = new MultipartFile[1];
			String fileName = paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_FILENAME)
					.getValor();
			multipartFiles[0] = new MockMultipartFile(fileName, fileName, ParametroGeneralUtil.CONS_CONTENT_EXCEL,
					outConv.toByteArray());

			EmailService email = new EmailService();
			Map<String, Object> params = new HashMap<>();

			email.setFrom(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_FROM).getValor());
			email.setSubject(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_SUBJECT).getValor());
			email.setTo(paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_TO).getValor());
			email.setTemplate(
					paramService.parametroPorNombre(ParametroGeneralUtil.CONS_PROC_CAR_SIN_EMAIL_BODY).getValor());
			params.put("user", usuario);
			email.setParams(params);
			emailUtil.notification(email, multipartFiles);

			entCargue = carService.getCargueActivo(ParametroGeneralUtil.CONS_ID_CARGUE_SINIESTROS);

			entCargue.setEstado(ParametroGeneralUtil.CONS_PROCESADO);
			carService.modificar(entCargue);

		} catch (Exception e) {
			entCargue.setEstado(ParametroGeneralUtil.CONS_ERROR);
			carService.modificar(entCargue);
			throw new SiprenException(e.getMessage());
		}
	}

	/**
	 * Dada una posición obtiene el valor de la lista de respuesta del archivo de
	 * cargue.
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
	 * Guarda temporalmente el archivo que se esta cargando para poder ser
	 * procesado.
	 * 
	 * @param multipart
	 * @return
	 * @throws IOException
	 */
	public File multipartToFile(String base64) throws IOException {
		String ruta = System.getProperty("java.io.tmpdir").concat(String.valueOf(System.currentTimeMillis()))
				.concat(".xlsx");

		System.out.println(ruta);

		byte[] data = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));

		Path destinationFile = Paths.get(ruta);
		Files.write(destinationFile, data);

		File convFile = new File(ruta);
		return convFile;
	}
}

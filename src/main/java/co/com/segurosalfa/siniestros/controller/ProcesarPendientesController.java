package co.com.segurosalfa.siniestros.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.ComparacionPersonaDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.exception.ModeloNotFoundException;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IProcesarPendientesService;
import co.com.sipren.common.bus.dto.ErrorResponse;
import co.com.sipren.common.util.ParametroGeneralUtil;
import co.com.sipren.common.util.ParametrosMensajes;
import co.com.sipren.common.util.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/procesarPendientes")
public class ProcesarPendientesController {

	@Autowired
	private IProcesarPendientesService pendientesService;

	@Autowired
	private IClienteUnicoService clienteUnicoService;

	@Autowired
	private ObjectMapper mapper;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ParametroGeneralUtil.GRAL_FORMAT_YYYYMMDD);

	// servicios que retornan las listas de datos pendientes para afiliados y
	// reclamantes
	@ApiOperation(value = "Operación de servicio que consulta el listado de afiliados pendientes por procesar siniestro", notes = "La operación retorna todos los afiliados con diferencias en datos principales y que impiden la creación de siniestro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/afiliados")
	public ResponseEntity<List<ProcesarPendientesDTO>> listarAfiliadosPendientes() throws SiprenException {
		List<ProcesarPendientesDTO> listaAfiliadosPendientes = pendientesService.listarPendientesAfiliados();

		if (listaAfiliadosPendientes != null && listaAfiliadosPendientes.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(listaAfiliadosPendientes, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de reclamantes pendientes por procesar siniestro", notes = "La operación retorna todos los reclamantes con diferencias en datos principales y que impiden la creación de siniestro")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/reclamantes")
	public ResponseEntity<List<ProcesarPendientesDTO>> listarReclamantesPendientes() throws JsonProcessingException, SiprenException, ServiceException {
		List<ProcesarPendientesDTO> listaReclamantespendientes = pendientesService.listarPendientesreclamante();

		if (listaReclamantespendientes != null && listaReclamantespendientes.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(listaReclamantespendientes, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que consulta el listado de reclamantes y afiliados pendientes por procesar", notes = "La operación retorna los reclamantes y afiliados pendientes de actualizar datos")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/infoAdicional")
	public ResponseEntity<List<ProcesarPendientesDTO>> listarInfoAdicional() throws JsonProcessingException, ServiceException, SiprenException {
		List<ProcesarPendientesDTO> listaInfoAdicional = pendientesService.listarPendientesInfoAdicional();

		if (listaInfoAdicional != null && listaInfoAdicional.isEmpty())
			throw new ModeloNotFoundException(ParametrosMensajes.ERROR_NO_DATA);

		return new ResponseEntity<>(listaInfoAdicional, HttpStatus.OK);
	}

	// Servicios que exponen las operaciones de comparación

	@ApiOperation(value = "Operación de servicio que compara el afiliado", notes = "La operación retorna la comparación del Afiliado entre Cliente Unico y AFP")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/compararAfiliado/{tipoDoc}/{documento}")
	public ResponseEntity<ProcesarPendientesDTO> compararAfiliadoPendiente(@PathVariable("tipoDoc") Integer tipoDoc,
			@PathVariable("documento") Long documento) throws SiprenException, JsonProcessingException, ServiceException {
		List<ProcesarPendientesDTO> afiliados = pendientesService.consultarPendientePorCedula(tipoDoc, documento);
		ProcesarPendientesDTO afiliado = new ProcesarPendientesDTO();
		if (Objects.nonNull(afiliados) && !afiliados.isEmpty()) {
			afiliado = afiliados.get(0);
		}

		List<ComparacionPersonaDTO> comparacionPersona = null;

		StringBuilder msj = new StringBuilder("El documento ");
		if (Objects.isNull(afiliado))
			throw new ModeloNotFoundException(
					msj.append(documento).append(" no registra informacion en Afiliados Pendientes").toString());

		try {

			ClienteUnicoDTO afiliadoAfp = new ClienteUnicoDTO();
			afiliadoAfp.setTipoDocumento(
					String.valueOf(afiliado.getTipoDocumento() == null ? "" : afiliado.getTipoDocumento()));
			afiliadoAfp.setCedula(afiliado.getIdentificacionAfiliado() == null ? ""
					: String.valueOf(afiliado.getIdentificacionAfiliado()));
			afiliadoAfp.setPrimerApell(afiliado.getPrimerApellido() == null ? "" : afiliado.getPrimerApellido());
			afiliadoAfp.setSegundoApell(afiliado.getSegundoApellido() == null ? "" : afiliado.getSegundoApellido());
			afiliadoAfp.setPrimerNombre(afiliado.getPrimerNombre() == null ? "" : afiliado.getPrimerNombre());
			afiliadoAfp.setSegundoNombre(afiliado.getSegundoNombre() == null ? "" : afiliado.getSegundoNombre());

			comparacionPersona = pendientesService.procesarPendientes(
					clienteUnicoService.consumirRestClienteUnico(String.valueOf(tipoDoc), String.valueOf(documento)),
					afiliadoAfp, Boolean.TRUE);

			if (Objects.isNull(comparacionPersona))
				throw new ModeloNotFoundException("Error comparando afiliado con Cliente Unico");

			afiliado.setListComparaciones(comparacionPersona);

		} catch (Exception e) {
			ErrorResponse error = mapper.readValue(e.getMessage(), ErrorResponse.class);
			throw new ModeloNotFoundException(error.getMensaje());
		}

		return new ResponseEntity<>(afiliado, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que compara el reclamante", notes = "La operación retorna la comparación del Reclamante entre Cliente Unico y AFP")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/compararReclamante/{tipoDoc}/{documento}")
	public ResponseEntity<ProcesarPendientesDTO> compararReclamantePendiente(@PathVariable("tipoDoc") Integer tipoDoc,
			@PathVariable("documento") Long documento) throws SiprenException, JsonProcessingException, ServiceException {
		List<ProcesarPendientesDTO> afiliados = pendientesService.consultarReclamantePorCedula(tipoDoc, documento);
		ProcesarPendientesDTO afiliado = new ProcesarPendientesDTO();
		if (Objects.nonNull(afiliados) && !afiliados.isEmpty()) {
			afiliado = afiliados.get(0);
		}
		List<ComparacionPersonaDTO> comparacionPersona = null;

		StringBuilder msj = new StringBuilder("El documento ");
		if (Objects.isNull(afiliado))
			throw new ModeloNotFoundException(
					msj.append(documento).append(" no registra informacion en Reclamantes Pendientes").toString());

		try {

			ClienteUnicoDTO afiliadoAfp = new ClienteUnicoDTO();
			afiliadoAfp.setTipoDocumento(
					String.valueOf(afiliado.getTipoDocumento() == null ? "" : afiliado.getTipoDocumento()));
			afiliadoAfp.setCedula(afiliado.getIdentificacionReclamante() == null ? ""
					: String.valueOf(afiliado.getIdentificacionReclamante()));
			afiliadoAfp.setPrimerApell(afiliado.getPrimerApellido() == null ? "" : afiliado.getPrimerApellido());
			afiliadoAfp.setSegundoApell(afiliado.getSegundoApellido() == null ? "" : afiliado.getSegundoApellido());
			afiliadoAfp.setPrimerNombre(afiliado.getPrimerNombre() == null ? "" : afiliado.getPrimerNombre());
			afiliadoAfp.setSegundoNombre(afiliado.getSegundoNombre() == null ? "" : afiliado.getSegundoNombre());

			comparacionPersona = pendientesService.procesarPendientes(
					clienteUnicoService.consumirRestClienteUnico(String.valueOf(tipoDoc), String.valueOf(documento)),
					afiliadoAfp, Boolean.TRUE);

			if (Objects.isNull(comparacionPersona))
				throw new ModeloNotFoundException("Error comparando afiliado con Cliente Unico");

			afiliado.setListComparaciones(comparacionPersona);

		} catch (Exception e) {
			ErrorResponse error = mapper.readValue(e.getMessage(), ErrorResponse.class);
			throw new ModeloNotFoundException(error.getMensaje());
		}

		return new ResponseEntity<>(afiliado, HttpStatus.OK);
	}

	@ApiOperation(value = "Operación de servicio que compara Datos Adicionales", notes = "La operación retorna la comparación de Información Adicional del Afiliado o el reclamante entre Cliente Unico y AFP")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParametrosMensajes.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParametrosMensajes.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParametrosMensajes.RESPUESTA_CORRECTA) })
	@GetMapping("/compararInfoAdicional/{tipoDoc}/{documento}")
	public ResponseEntity<ProcesarPendientesDTO> compararInfoAdicionalPendiente(
			@PathVariable("tipoDoc") Integer tipoDoc, @PathVariable("documento") Long documento)
			throws SiprenException, JsonProcessingException, ServiceException {
		List<ProcesarPendientesDTO> afiliados = pendientesService.consultarPendientesInfoAdicionalPorCedula(tipoDoc,
				documento);
		ProcesarPendientesDTO afiliado = new ProcesarPendientesDTO();
		if (Objects.nonNull(afiliados) && !afiliados.isEmpty()) {
			afiliado = afiliados.get(0);
		}
		List<ComparacionPersonaDTO> comparacionPersona = null;

		StringBuilder msj = new StringBuilder("El documento ");
		if (Objects.isNull(afiliado))
			throw new ModeloNotFoundException(
					msj.append(documento).append(" no registra informacion en Afiliados Pendientes").toString());

		try {

			ClienteUnicoDTO afiliadoAfp = new ClienteUnicoDTO();

			afiliadoAfp.setGenero(afiliado.getGenero() == null ? "" : afiliado.getGenero());
			afiliadoAfp.setEstadoCivil(afiliado.getEstadoCivilDesc() == null ? "" : afiliado.getEstadoCivilDesc());
			afiliadoAfp.setFecNacimiento(
					afiliado.getFecNacimiento() == null ? "" : afiliado.getFecNacimiento().format(formatter));
			afiliadoAfp.setEps(afiliado.getEpsDesc() == null ? "" : afiliado.getEpsDesc().trim());
			afiliadoAfp.setFecMuerte(afiliado.getFecMuerte() == null ? "" : afiliado.getFecMuerte());

			comparacionPersona = pendientesService.procesarPendientes(
					clienteUnicoService.consumirRestClienteUnico(String.valueOf(tipoDoc), String.valueOf(documento)),
					afiliadoAfp, Boolean.FALSE);

			if (Objects.isNull(comparacionPersona))
				throw new ModeloNotFoundException("Error comparando afiliado con Cliente Unico");

			afiliado.setListComparaciones(comparacionPersona);

		} catch (Exception e) {
			ErrorResponse error = mapper.readValue(e.getMessage(), ErrorResponse.class);
			throw new ModeloNotFoundException(error.getMensaje());
		}

		return new ResponseEntity<>(afiliado, HttpStatus.OK);
	}

}

package co.com.segurosalfa.siniestros.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.ComparacionPersonaDTO;
import co.com.segurosalfa.siniestros.dto.CuEpsDTO;
import co.com.segurosalfa.siniestros.dto.CuEstadoCivilDTO;
import co.com.segurosalfa.siniestros.dto.ProcesarPendientesDTO;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.ISnrTmpPendienteAfiliadoRepo;
import co.com.segurosalfa.siniestros.repo.ISnrTmpPendienteInfoAdicionalRepo;
import co.com.segurosalfa.siniestros.repo.ISnrTmpPendienteReclamanteRepo;
import co.com.segurosalfa.siniestros.service.IClienteUnicoService;
import co.com.segurosalfa.siniestros.service.IProcesarPendientesService;
import co.com.sipren.common.util.ComparacionObject;
import co.com.sipren.common.util.ServiceException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
//@XRayEnabled
public class ProcesarPendientesServiceImpl implements IProcesarPendientesService {

	@Autowired
	private ISnrTmpPendienteAfiliadoRepo repoAfiliado;

	@Autowired
	private ISnrTmpPendienteReclamanteRepo repoReclamante;

	@Autowired
	private ISnrTmpPendienteInfoAdicionalRepo repoInfoAdicional;

	@Autowired
	private IClienteUnicoService clienteUnicoService;

	@Override
	public List<ProcesarPendientesDTO> listarPendientesAfiliados() throws SiprenException {
		return repoAfiliado.listarPendientesAfiliados();
	}

	@Override
	public List<ProcesarPendientesDTO> consultarPendientePorCedula(Integer tipoDoc, Long documento)
			throws SiprenException, JsonProcessingException, ServiceException {
		return repoAfiliado.consultarPendientePorCedula(tipoDoc, documento);
	}

	@Override
	public List<ComparacionPersonaDTO> procesarPendientes(ClienteUnicoDTO clienteUnico, ClienteUnicoDTO afiliadoAfp,
			Boolean comparaAfp) {
		List<ComparacionPersonaDTO> listaComparaciones = new ArrayList<ComparacionPersonaDTO>();
		Map<String, Object> campos = new HashMap<String, Object>();
		ComparacionObject compara = new ComparacionObject();

		try {
			for (Field f : afiliadoAfp.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (Objects.nonNull(f.get(afiliadoAfp))) {
					campos.put(f.getName(), f.get(afiliadoAfp));
				}
			}

			for (Field f : clienteUnico.getClass().getDeclaredFields()) {
				f.setAccessible(true);

				ComparacionPersonaDTO comparacion = new ComparacionPersonaDTO();
				if (campos.containsKey(f.getName())) {
					String valueAfp = campos.get(f.getName()).toString();

					comparacion.setDescripcion(f.getName());
					comparacion.setAfp(valueAfp);
					comparacion.setClienteUnico(f.get(clienteUnico) == null ? "" : f.get(clienteUnico).toString());

					if (comparaAfp) {
						comparacion.setEsDiferente(compara.validation(campos.get(f.getName()), f.get(clienteUnico)));
					} else if (!comparaAfp && !"".equals(valueAfp)) {
						comparacion.setEsDiferente(compara.validation(campos.get(f.getName()), f.get(clienteUnico)));
					} else {
						comparacion.setEsDiferente(false);
					}

					listaComparaciones.add(comparacion);
				}

			}
		} catch (Exception e) {
			log.error("Error comparando campos entre cliente Unico y AFP: {}", e);
		}

		return listaComparaciones;
	}

	@Override
	public List<ProcesarPendientesDTO> listarPendientesInfoAdicional()
			throws JsonProcessingException, ServiceException, SiprenException {
		List<ProcesarPendientesDTO> listPendientes = repoInfoAdicional.listarPendientesInfoAdicional();
		listPendientes.parallelStream().peek(ia -> {
			try {
				ClienteUnicoDTO afiliado = clienteUnicoService
						.consumirRestClienteUnico(String.valueOf(ia.getNumPersona()));
				ia.setIdentificacionAfiliado(Long.parseLong(afiliado.getCedula()));
				ia.setTipoDocumento(afiliado.getTipoDocumento());
			} catch (Exception e) {
				log.error("error consultando afiliado en cliente unico en listar pendientes info adicional");
			}
		});
		return listPendientes;
	}

	@Override
	public List<ProcesarPendientesDTO> listarPendientesreclamante()
			throws JsonProcessingException, ServiceException, SiprenException {
		List<ProcesarPendientesDTO> listpendientes = repoReclamante.listarPendientesreclamante();
		listpendientes.parallelStream().peek(r -> {
			try {
				ClienteUnicoDTO afiliado = clienteUnicoService.consumirRestClienteUnico(r.getNumPersona().toString());
				if (Objects.nonNull(afiliado)) {
					r.setIdentificacionAfiliado(Long.parseLong(afiliado.getCedula()));
				}
			} catch (JsonProcessingException | ServiceException | SiprenException e) {
				log.error("Error consumiendo cliente unico para pendientes reclamante", e);
			}
		});
		return listpendientes;
	}

	@Override
	public List<ProcesarPendientesDTO> consultarReclamantePorCedula(Integer tipoDoc, Long documento)
			throws JsonProcessingException, ServiceException, SiprenException {
		List<ProcesarPendientesDTO> listpendientes = repoReclamante.consultarReclamantePorCedula(tipoDoc, documento);
		for (ProcesarPendientesDTO procesarPendientesDTO : listpendientes) {
			if (Objects.nonNull(procesarPendientesDTO.getNumPersona())) {
				ClienteUnicoDTO afiliado = clienteUnicoService
						.consumirRestClienteUnico(procesarPendientesDTO.getNumPersona().toString());
				if (Objects.nonNull(afiliado)) {
					procesarPendientesDTO.setIdentificacionAfiliado(Long.parseLong(afiliado.getCedula()));
				}
			}
			ClienteUnicoDTO reclamante = clienteUnicoService.consumirRestClienteUnico(
					procesarPendientesDTO.getIdTipoDocumento().toString(),
					procesarPendientesDTO.getIdentificacionReclamante().toString());
			procesarPendientesDTO.setNumPersona(reclamante.getNumPersona());

		}
		return listpendientes;
	}

	@Override
	public void eliminarPendientePorCedula(Integer tipoDoc, Long documento) {
		repoAfiliado.eliminarPendientePorCedula(tipoDoc, documento);
		repoReclamante.eliminarPendientePorCedula(tipoDoc, documento);

	}

	@Override
	public List<ProcesarPendientesDTO> consultarPendientesInfoAdicionalPorCedula(Integer tipoDoc, Long documento)
			throws JsonProcessingException, ServiceException, SiprenException {
		List<ProcesarPendientesDTO> listaPendientes = repoInfoAdicional
				.consultarPendientesInfoAdicionalPorCedula(tipoDoc, documento);
		return listaPendientes.stream().map(n -> asignarValoresClienteUnico(n)).collect(Collectors.toList());
	}
	
	private ProcesarPendientesDTO asignarValoresClienteUnico(ProcesarPendientesDTO obj) {
		try {
			CuEpsDTO epsDTO = clienteUnicoService.consultarEps(String.valueOf(obj.getEps()));
			CuEstadoCivilDTO estadoCivilDTO = clienteUnicoService.consultarEstadoCivil(obj.getCodEstadoCivil());
			if (Objects.nonNull(epsDTO))
				obj.setEpsDesc(epsDTO.getRazonSocial());
			if (Objects.nonNull(estadoCivilDTO))
				obj.setEstadoCivilDesc(estadoCivilDTO.getNombre());
		} catch (Exception e) {
			log.error("Error consultando información adicional : ", e);
		}
		return obj;
	}

	@Override
	public void eliminarPendientePorCedulaAdicional(Integer tipoDoc, Long documento) {
		repoInfoAdicional.eliminarPendientePorCedula(tipoDoc, documento);
	}

}

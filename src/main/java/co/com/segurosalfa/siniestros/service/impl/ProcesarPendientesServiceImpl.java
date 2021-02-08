package co.com.segurosalfa.siniestros.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.ClienteUnicoDTO;
import co.com.segurosalfa.siniestros.dto.ComparacionPersonaDTO;
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
		List<ProcesarPendientesDTO> listaPendientes = repoAfiliado.consultarPendientePorCedula(tipoDoc, documento);
		for (ProcesarPendientesDTO procesarPendientesDTO : listaPendientes) {
			ClienteUnicoDTO afiliado = clienteUnicoService.consumirRestClienteUnico(String.valueOf(tipoDoc),
					String.valueOf(documento));
			procesarPendientesDTO.setPrimerNombre(afiliado.getPrimerNombre());
			procesarPendientesDTO.setSegundoNombre(afiliado.getSegundoNombre());
			procesarPendientesDTO.setPrimerApellido(afiliado.getPrimerApell());
			procesarPendientesDTO.setSegundoApellido(afiliado.getSegundoApell());
		}
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
	public List<ProcesarPendientesDTO> listarPendientesInfoAdicional() {
		return repoInfoAdicional.listarPendientesInfoAdicional();
	}

	@Override
	public List<ProcesarPendientesDTO> listarPendientesreclamante() {
		return repoReclamante.listarPendientesreclamante();
	}

	@Override
	public List<ProcesarPendientesDTO> consultarReclamantePorCedula(Integer tipoDoc, Long documento) {
		return repoReclamante.consultarReclamantePorCedula(tipoDoc, documento);
	}

	@Override
	public void eliminarPendientePorCedula(Integer tipoDoc, Long documento) {
		repoAfiliado.eliminarPendientePorCedula(tipoDoc, documento);

	}

	@Override
	public List<ProcesarPendientesDTO> consultarPendientesInfoAdicionalPorCedula(Integer tipoDoc, Long documento) {
		return repoInfoAdicional.consultarPendientesInfoAdicionalPorCedula(tipoDoc, documento);
	}

}

package co.com.segurosalfa.siniestros.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.dto.ProcesosDTO;
import co.com.segurosalfa.siniestros.dto.ProgramacionJobDTO;
import co.com.segurosalfa.siniestros.entity.SnrParametrica;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrParametricasRepo;
import co.com.segurosalfa.siniestros.repo.ISnrResulPrcCreacionSiniestroRepo;
import co.com.segurosalfa.siniestros.service.IParametricasService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@XRayEnabled
public class ParametricasImpl extends CRUDImpl<SnrParametrica, Integer> implements IParametricasService {

	@Autowired
	ISnrParametricasRepo repo;

	@Autowired
	ISnrResulPrcCreacionSiniestroRepo avance;

	@Override
	protected IGenericRepo<SnrParametrica, Integer> getRepo() {
		return repo;
	}

	@Override
	public SnrParametrica parametroPorNombre(String nombre) throws SiprenException {
		return repo.parametroPorNombre(nombre);
	}

	@Transactional
	@Override
	public Void programarJob(ProgramacionJobDTO dto) {

		repo.actualizarParamFecCorte(dto.getCodFecCorte());

		return repo.programarJob(dto.getCodJob(), dto.getFechaEjecucion(), dto.getUsuario(), dto.getEmail(),
				dto.getCodPeriodicidad(), dto.getDetalleFrecuencia(), dto.getAcccionJob());

	}

	/*
	 * 1. Consulta del listado de Procesos 2. Consulta del detalle del proceso 3.
	 * Consulta del historico de modificaciones del proceso
	 */
	@Override
	public List<ProcesosDTO> listarProcesos() throws SiprenException {

		List<Object[]> tempProcesos = new ArrayList<>();
		List<ProcesosDTO> resultProcesos = new ArrayList<>();

		try {
			tempProcesos = repo.listarProcesos();
			resultProcesos = convertToDTO(tempProcesos, 1);
		} catch (Exception e) {
			throw new SiprenException("Error al obtener el listado de procesos: " + e.toString());
		}

		return resultProcesos;
	}

	@Override
	public List<ProcesosDTO> detalleProceso(String idProceso) throws SiprenException {

		List<Object[]> detProceso = new ArrayList<>();
		List<ProcesosDTO> resultDetProceso = new ArrayList<>();

		try {
			detProceso = repo.detalleProceso(idProceso);
			resultDetProceso = convertToDTO(detProceso, 2);
		} catch (Exception e) {
			throw new SiprenException("Error al obtener el detalle del proceso: " + e.toString());
		}

		return resultDetProceso;
	}

	@Override
	public List<ProcesosDTO> historicoProceso(String idProceso) throws SiprenException {

		List<Object[]> histProceso = new ArrayList<>();
		List<ProcesosDTO> resultHistProceso = new ArrayList<>();

		try {
			histProceso = repo.historicoProceso(idProceso);
			resultHistProceso = convertToDTO(histProceso, 3);
		} catch (Exception e) {
			throw new SiprenException("Error al obtener el detalle del proceso: " + e.toString());
		}

		return resultHistProceso;
	}

	public String consultarAvanceProc(String idProceso) {
		StringBuilder result = new StringBuilder();

		try {

			result.append(avance.count()).append("/").append(avance.registProcesar()).append(" Registros a procesar.");

		} catch (Exception e) {
			log.error("Error al consultar el avance del proceso: ", e);
		}

		return result.toString();
	}

	private List<ProcesosDTO> convertToDTO(List<Object[]> dto, Integer consulta) {
		List<ProcesosDTO> result = new ArrayList<>();

		try {
			if ((dto != null && !dto.isEmpty()) && consulta == 1) {
				dto.stream().forEach(i -> {
					ProcesosDTO tempDTO = new ProcesosDTO();

					tempDTO.setIdProceso(i[0].toString());
					tempDTO.setNombreProceso(i[1].toString());
					tempDTO.setPeriodicidad(i[2].toString());
					tempDTO.setFecFinEjecucion(i[3].toString());
					tempDTO.setEstadoProceso(i[4].toString());

					if (tempDTO.getEstadoProceso().equalsIgnoreCase("En Ejecución")) {
						tempDTO.setFecFinEjecucion("");
						tempDTO.setAvance(consultarAvanceProc(tempDTO.getIdProceso()));
					} else {
						tempDTO.setAvance("");
					}

					result.add(tempDTO);
				});
			} else if ((dto != null && !dto.isEmpty()) && (consulta == 3 || consulta == 2)) {
				dto.stream().forEach(i -> {
					ProcesosDTO tempDTO = new ProcesosDTO();

					tempDTO.setIdProceso(i[0].toString());
					tempDTO.setNombreProceso(i[1].toString());
					tempDTO.setPeriodicidad(i[2].toString());
					tempDTO.setFecInicioProceso(i[3].toString());
					tempDTO.setPeriodoCorte(i[4].toString());
					tempDTO.setFecCancelacion(i[5].toString());
					tempDTO.setUsuarioInicio(i[6].toString());
					tempDTO.setUsuarioCancelacion(i[7].toString());

					result.add(tempDTO);
				});
			}
		} catch (Exception e) {
			log.error("Error al convertir la lista al objeto DTO", e);
		}

		return result;
	}

	@Override
	public Integer cancelarJob(ProgramacionJobDTO dto) {
		return repo.cancelarJob(dto.getCodJob(), dto.getUsuario());
	}

}

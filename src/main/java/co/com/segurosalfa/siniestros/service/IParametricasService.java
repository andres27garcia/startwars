package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.dto.ProcesosDTO;
import co.com.segurosalfa.siniestros.dto.ProgramacionJobDTO;
import co.com.segurosalfa.siniestros.entity.SnrParametrica;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IParametricasService extends ICRUD<SnrParametrica, Integer> {

	SnrParametrica parametroPorNombre(String nombre) throws SiprenException;

	public Void programarJob(ProgramacionJobDTO dto);

	public Integer cancelarJob(ProgramacionJobDTO dto);

	public List<ProcesosDTO> listarProcesos() throws SiprenException;

	public List<ProcesosDTO> detalleProceso(Integer idProceso) throws SiprenException;

	public List<ProcesosDTO> historicoProceso(Integer idProceso) throws SiprenException;

}

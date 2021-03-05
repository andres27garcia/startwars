package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.entity.SnrEstado;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrEstadoService extends ICRUD<SnrEstado, Integer> {

	List<SnrEstado> listarPorTipo(String tipo) throws SiprenException;
}
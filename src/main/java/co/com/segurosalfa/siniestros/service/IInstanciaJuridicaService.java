package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.entity.SnrInstanciaJuridica;
import co.com.segurosalfa.siniestros.entity.SnrInstanciaJuridicaId;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IInstanciaJuridicaService extends ICRUD<SnrInstanciaJuridica, SnrInstanciaJuridicaId> {

	List<SnrInstanciaJuridica> listarPorProcesoJuridico(Long idProcesoJuridico) throws SiprenException;

	List<SnrInstanciaJuridica> listarPorProcesoJuridicoTipoInstancia(Long idProcesoJuridico, Long idTipoInstancia)
			throws SiprenException;

}

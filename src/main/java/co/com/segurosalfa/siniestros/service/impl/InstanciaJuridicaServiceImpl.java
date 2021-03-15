package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrInstanciaJuridica;
import co.com.segurosalfa.siniestros.entity.SnrInstanciaJuridicaId;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrInstanciaJuridicaRepo;
import co.com.segurosalfa.siniestros.service.IInstanciaJuridicaService;

@Service
//@XRayEnabled
public class InstanciaJuridicaServiceImpl extends CRUDImpl<SnrInstanciaJuridica, SnrInstanciaJuridicaId>
		implements IInstanciaJuridicaService {

	@Autowired
	private ISnrInstanciaJuridicaRepo repo;

	@Override
	protected IGenericRepo<SnrInstanciaJuridica, SnrInstanciaJuridicaId> getRepo() {
		return repo;
	}

	@Override
	public List<SnrInstanciaJuridica> listarPorProcesoJuridico(Long idProcesoJuridico) throws SiprenException {
		return repo.listarPorProcesoJuridico(idProcesoJuridico);
	}

	@Override
	public List<SnrInstanciaJuridica> listarPorProcesoJuridicoTipoInstancia(Long idProcesoJuridico,
			Long idTipoInstancia) throws SiprenException {
		return repo.listarPorProcesoJuridicoTipoInstancia(idProcesoJuridico, idTipoInstancia);
	}

	
}

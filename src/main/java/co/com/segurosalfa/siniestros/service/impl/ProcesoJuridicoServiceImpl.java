package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrProcesoJuridico;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrProcesoJuridicoRepo;
import co.com.segurosalfa.siniestros.service.IProcesoJuridicoService;

@Service
public class ProcesoJuridicoServiceImpl extends CRUDImpl<SnrProcesoJuridico, Integer> implements IProcesoJuridicoService {

	@Autowired
	private ISnrProcesoJuridicoRepo repo;

	@Override
	protected IGenericRepo<SnrProcesoJuridico, Integer> getRepo() {
		return repo;
	}

	@Override
	public List<SnrProcesoJuridico> listarPorPersona(Long numPersona) {
		return repo.listarPorPersona(numPersona);
	}

}

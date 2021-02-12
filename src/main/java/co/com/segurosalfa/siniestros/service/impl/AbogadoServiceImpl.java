package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrAbogado;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrAbogadoRepo;
import co.com.segurosalfa.siniestros.service.IAbogadoService;

@Service
public class AbogadoServiceImpl extends CRUDImpl<SnrAbogado, Integer> implements IAbogadoService {

	@Autowired
	private ISnrAbogadoRepo repo;

	@Override
	protected IGenericRepo<SnrAbogado, Integer> getRepo() {
		return repo;
	}

}

package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrTipo;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrTipoRepo;
import co.com.segurosalfa.siniestros.service.ISnrTipoService;

@Service
public class SnrTiposImpl extends CRUDImpl<SnrTipo, Integer> implements ISnrTipoService {

	@Autowired
	private ISnrTipoRepo repo;

	@Override
	protected IGenericRepo<SnrTipo, Integer> getRepo() {
		return repo;
	}

}
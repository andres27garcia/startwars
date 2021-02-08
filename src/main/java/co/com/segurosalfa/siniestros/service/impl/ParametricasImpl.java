package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrParametrica;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrParametricasRepo;
import co.com.segurosalfa.siniestros.service.IParametricasService;

@Service
public class ParametricasImpl extends CRUDImpl<SnrParametrica, Integer> implements IParametricasService {

	@Autowired
	private ISnrParametricasRepo repo;

	@Override
	protected IGenericRepo<SnrParametrica, Integer> getRepo() {
		return repo;
	}

	@Override
	public SnrParametrica parametroXNombre(String nombre) throws SiprenException {
		return repo.parametroXNombre(nombre);
	}

}

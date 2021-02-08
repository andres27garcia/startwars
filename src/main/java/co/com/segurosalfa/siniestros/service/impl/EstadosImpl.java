package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrEstado;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrEstadoRepo;
import co.com.segurosalfa.siniestros.service.ISnrEstadoService;

@Service
public class EstadosImpl extends CRUDImpl<SnrEstado, Integer> implements ISnrEstadoService {

	@Autowired
	private ISnrEstadoRepo repo;

	@Override
	protected IGenericRepo<SnrEstado, Integer> getRepo() {
		return repo;

	}

}
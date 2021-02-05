package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.IGnrResulPrcCreacionSiniestroRepo;
import co.com.segurosalfa.siniestros.service.IResulPrcCreacionSiniestroService;

@Service
public class GnrResulPrcCreacionSiniestrosImpl extends CRUDImpl<SnrResulPrcCreacionSiniestro, Integer>
		implements IResulPrcCreacionSiniestroService {

	@Autowired
	private IGnrResulPrcCreacionSiniestroRepo repo;

	@Override
	protected IGenericRepo<SnrResulPrcCreacionSiniestro, Integer> getRepo() {
		return repo;
	}

	@Override
	public List<SnrResulPrcCreacionSiniestro> consultarPorProceso(String proceso) {
		return repo.consultarPorProceso(proceso);
	}

}
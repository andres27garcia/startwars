package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrTipoEquivalencia;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrTipoEquivalenciaRepo;
import co.com.segurosalfa.siniestros.service.ITipoEquivalenciaService;

@Service
//@XRayEnabled
public class TipoEquivalenciaServiceImpl extends CRUDImpl<SnrTipoEquivalencia, Long> implements ITipoEquivalenciaService{

	@Autowired
	ISnrTipoEquivalenciaRepo repo;
	
	@Override
	protected IGenericRepo<SnrTipoEquivalencia, Long> getRepo() {
		return repo;
	}

}

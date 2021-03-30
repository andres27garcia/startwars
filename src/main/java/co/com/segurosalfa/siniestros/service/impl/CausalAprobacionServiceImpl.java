package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrCausalAprobacion;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrCausalAprobacionRepo;
import co.com.segurosalfa.siniestros.service.ICausalAprobacionService;

@Service
public class CausalAprobacionServiceImpl extends CRUDImpl<SnrCausalAprobacion, Long> implements ICausalAprobacionService{

	@Autowired
	ISnrCausalAprobacionRepo repo;
	
	@Override
	protected IGenericRepo<SnrCausalAprobacion, Long> getRepo() {		
		return repo;
	}

}

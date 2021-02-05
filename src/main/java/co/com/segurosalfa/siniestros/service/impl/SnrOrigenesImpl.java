package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrOrigen;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrOrigenesRepo;
import co.com.segurosalfa.siniestros.service.IOrigenesService;

@Service
public class SnrOrigenesImpl extends CRUDImpl<SnrOrigen, Integer> implements IOrigenesService{

	@Autowired
	private ISnrOrigenesRepo repo;
	
	@Override
	protected IGenericRepo<SnrOrigen, Integer> getRepo(){
		return repo;
	}

}

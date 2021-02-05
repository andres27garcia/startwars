package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrHilAportante;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.IHilAportanteRepo;
import co.com.segurosalfa.siniestros.service.IHilAportanteService;

@Service
public class HilAportantesServiceImpl extends CRUDImpl<SnrHilAportante, Integer> implements IHilAportanteService{

	@Autowired
	private IHilAportanteRepo repo;
	
	@Override
	protected IGenericRepo<SnrHilAportante, Integer> getRepo(){
		return repo;
	}

}

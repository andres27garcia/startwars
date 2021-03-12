package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.entity.SnrHilAportante;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrHilAportanteRepo;
import co.com.segurosalfa.siniestros.service.IHilAportanteService;

@Service
@XRayEnabled
public class HilAportantesServiceImpl extends CRUDImpl<SnrHilAportante, Long> implements IHilAportanteService{

	@Autowired
	private ISnrHilAportanteRepo repo;
	
	@Override
	protected IGenericRepo<SnrHilAportante, Long> getRepo(){
		return repo;
	}

}

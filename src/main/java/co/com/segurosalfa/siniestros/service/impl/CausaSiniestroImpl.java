package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.entity.SnrCausaSiniestro;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrCausaSiniestroRepo;
import co.com.segurosalfa.siniestros.service.ICausaSiniestroService;

@Service
@XRayEnabled
public class CausaSiniestroImpl extends CRUDImpl<SnrCausaSiniestro, Integer> implements ICausaSiniestroService{

	@Autowired
	private ISnrCausaSiniestroRepo repo;
	
	@Override
	protected IGenericRepo<SnrCausaSiniestro, Integer> getRepo(){
		return repo;
	}

}

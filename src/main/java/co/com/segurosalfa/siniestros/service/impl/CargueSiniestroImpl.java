package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrCargueArchivos;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrCargueArchivosRepo;
import co.com.segurosalfa.siniestros.service.ICargueArchivosService;

@Service
//@XRayEnabled
public class CargueSiniestroImpl extends CRUDImpl<SnrCargueArchivos, Integer> implements ICargueArchivosService{

	@Autowired
	private ISnrCargueArchivosRepo repo;
	
	@Override
	protected IGenericRepo<SnrCargueArchivos, Integer> getRepo(){
		return repo;
	}

	@Override
	public SnrCargueArchivos getCargueActivo(String tipo) throws SiprenException {
		return repo.getCargueActivo(tipo);
	}

}

package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.entity.ClfDatosDictamenes;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IClfDatosDictamenesRepo;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.service.IDatosDictamenesService;

@Service
@XRayEnabled
public class ClfDatosDictamenesImpl extends CRUDImpl<ClfDatosDictamenes, Integer> implements IDatosDictamenesService {

	@Autowired
	private IClfDatosDictamenesRepo repo;

	@Override
	protected IGenericRepo<ClfDatosDictamenes, Integer> getRepo() {
		return repo;
	}

	@Override
	public ClfDatosDictamenes consultaPorPersona(Integer numPersona) throws SiprenException {
		return repo.consultaPorPersona(numPersona);
	}

}

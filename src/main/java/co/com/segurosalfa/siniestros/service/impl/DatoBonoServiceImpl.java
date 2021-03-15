package co.com.segurosalfa.siniestros.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrDatoBono;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrDatoBonoRepo;
import co.com.segurosalfa.siniestros.service.IDatoBonoService;

@Service
//@XRayEnabled
public class DatoBonoServiceImpl extends CRUDImpl<SnrDatoBono, Long> implements IDatoBonoService{

	@Autowired
	private ISnrDatoBonoRepo repo;
	
	@Override
	protected IGenericRepo<SnrDatoBono, Long> getRepo() {
		return repo;
	}

	@Override
	public SnrDatoBono obtenerBonosPersona(Long numPersona) {
		return repo.obtenerBonosPersona(numPersona);
	}

}

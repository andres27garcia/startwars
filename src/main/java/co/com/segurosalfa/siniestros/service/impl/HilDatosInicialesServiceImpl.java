package co.com.segurosalfa.siniestros.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrHilDatoInicial;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrHilDatoInicialRepo;
import co.com.segurosalfa.siniestros.service.IHilDatoInicialService;

@Service
//@XRayEnabled
public class HilDatosInicialesServiceImpl extends CRUDImpl<SnrHilDatoInicial, Long> implements IHilDatoInicialService{

	@Autowired
	private ISnrHilDatoInicialRepo repo;
	
	@Override
	protected IGenericRepo<SnrHilDatoInicial, Long> getRepo(){
		return repo;
	}

	@Override
	public void actualizarRegistroInvalido(String blnRegInv, Long numPersona, LocalDate fecha) {
		repo.actualizarRegistroInvalido(blnRegInv, numPersona, fecha);
	}

}

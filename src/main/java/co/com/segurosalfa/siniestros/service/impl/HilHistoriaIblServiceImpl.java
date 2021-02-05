package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrHilHistoriaIbl;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrHilHistoriaIblRepo;
import co.com.segurosalfa.siniestros.service.IHilHistoriaIblService;

@Service
public class HilHistoriaIblServiceImpl extends CRUDImpl<SnrHilHistoriaIbl, Long> implements IHilHistoriaIblService{

	@Autowired
	private ISnrHilHistoriaIblRepo repo;

	@Override
	protected IGenericRepo<SnrHilHistoriaIbl, Long> getRepo(){
		return repo;
	}

	@Override
	public List<SnrHilHistoriaIbl> listarPorPersona(Long numPersona) throws SiprenException {
		return repo.listarPorPersona(numPersona);
	}

}

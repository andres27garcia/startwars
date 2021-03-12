package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.entity.SnrComentarioProcesoJur;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrComentarioProcesoJuridicoRepo;
import co.com.segurosalfa.siniestros.service.IComentarioProcesoJuridicoService;

@Service
@XRayEnabled
public class ComentarioProcesoJuridicoServiceImpl extends CRUDImpl<SnrComentarioProcesoJur, Long> implements IComentarioProcesoJuridicoService{

	@Autowired
	private ISnrComentarioProcesoJuridicoRepo repo;

	@Override
	protected IGenericRepo<SnrComentarioProcesoJur, Long> getRepo() {
		return repo;
	}

	@Override
	public List<SnrComentarioProcesoJur> listarPorProcesoJuridico(Long idProcesoJur) throws SiprenException {
		return repo.listarPorProcesoJuridico(idProcesoJur);
	}

}

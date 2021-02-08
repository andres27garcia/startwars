package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.entity.SnrComentarioReclamante;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrComentarioReclamanteRepo;
import co.com.segurosalfa.siniestros.service.IComentarioReclamanteService;

@Service
public class ComentarioReclamanteServiceImpl extends CRUDImpl<SnrComentarioReclamante, Long>
		implements IComentarioReclamanteService {

	@Autowired
	private ISnrComentarioReclamanteRepo repo;

	@Override
	protected IGenericRepo<SnrComentarioReclamante, Long> getRepo() {
		return repo;
	}

	@Override
	public List<SnrComentarioReclamante> listarDatosPorReclamante(Long idReclamante) throws SiprenException {
		return repo.listarDatosPorReclamante(idReclamante);
	}

}

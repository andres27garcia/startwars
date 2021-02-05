package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.dto.SnrComentarioTramiteDTO;
import co.com.segurosalfa.siniestros.entity.SnrComentarioTramite;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrComentarioTramiteRepo;
import co.com.segurosalfa.siniestros.service.IComentarioTramiteService;

@Service
public class SnrComentarioTramiteServiceImpl extends CRUDImpl<SnrComentarioTramite, Long>
		implements IComentarioTramiteService {

	@Autowired
	private ISnrComentarioTramiteRepo repo;
	@Autowired
	private ModelMapper mapper;

	@Override
	protected IGenericRepo<SnrComentarioTramite, Long> getRepo() {
		return repo;
	}

	@Override
	public List<SnrComentarioTramiteDTO> listarDatosPorTramite(Long numTramite) throws SiprenException {
		List<SnrComentarioTramiteDTO> listComentarios = repo.listarDatosPorTramite(numTramite).stream()
				.map(c -> mapper.map(c, SnrComentarioTramiteDTO.class)).collect(Collectors.toList());
		return listComentarios;
	}

}

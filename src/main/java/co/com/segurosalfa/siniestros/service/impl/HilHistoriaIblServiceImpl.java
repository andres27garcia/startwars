package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.dto.SnrHilHistoriaIblDTO;
import co.com.segurosalfa.siniestros.entity.SnrHilHistoriaIbl;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrHilHistoriaIblRepo;
import co.com.segurosalfa.siniestros.service.IHilDatoInicialService;
import co.com.segurosalfa.siniestros.service.IHilHistoriaIblService;
import co.com.sipren.common.util.ParametroGeneralUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//@XRayEnabled
public class HilHistoriaIblServiceImpl extends CRUDImpl<SnrHilHistoriaIbl, Long> implements IHilHistoriaIblService {

	@Autowired
	private ISnrHilHistoriaIblRepo repo;
	@Autowired
	private ModelMapper mapper;
	@Autowired 
	private IHilDatoInicialService serviceDatosIniciales;

	@Override
	protected IGenericRepo<SnrHilHistoriaIbl, Long> getRepo() {
		return repo;
	}

	@Override
	public List<SnrHilHistoriaIblDTO> listarPorPersona(Long numPersona) throws SiprenException {
		return repo.listarPorPersona(numPersona).stream().map(h -> mapper.map(h, SnrHilHistoriaIblDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public Boolean depurarHistoriaLaboral(SnrHilHistoriaIblDTO hilHistoriaIblDTO) throws SiprenException {
		try {
			repo.depurarHistoriaLaboral(hilHistoriaIblDTO.getPersona(), 
					hilHistoriaIblDTO.getFecInicialCot());
			serviceDatosIniciales.actualizarRegistroInvalido(ParametroGeneralUtil.GRAL_BLN_INVALIDO,
					hilHistoriaIblDTO.getPersona(),
					hilHistoriaIblDTO.getFecInicialCot());
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error("Error al depurar los periodos de la historia laboral", e);
		}
		return Boolean.FALSE;
	}

}

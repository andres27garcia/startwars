package co.com.segurosalfa.siniestros.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.segurosalfa.siniestros.dto.CargueSiniestrosDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoBasico;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.repo.ISnrDatosBasicosRepo;
import co.com.segurosalfa.siniestros.service.ISnrDatosBasicosService;
import co.com.sipren.common.util.ParametroGeneralUtil;

@Service
public class DatoBasicoServiceImpl extends CRUDImpl<SnrDatoBasico, Long> implements ISnrDatosBasicosService {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private ISnrDatosBasicosRepo repo;
	

	@Override
	protected IGenericRepo<SnrDatoBasico, Long> getRepo() {
		return repo;
	}

	@Override
	public void crearSiniestroCargue(String usuario, String proceso) throws SiprenException {
		repo.crearSiniestroCargue(usuario, proceso);

	}

	@Override
	public void limpiarTemporalesCargue(String usuario, String proceso) throws SiprenException {
		repo.limpiarTemporalesCargue(usuario, proceso);

	}

	@Override
	public void procesarCargue(CargueSiniestrosDTO siniestro)
			throws SiprenException, NumberFormatException, ParseException {

		this.consultaPorvenirPorAfiliado(ParametroGeneralUtil.USER_TMP, ParametroGeneralUtil.CONS_ORIGEN_CARGUE,
				siniestro.getDocumento() != null ? Long.parseLong(siniestro.getDocumento()) : 0,
				siniestro.getTipoDocumento(),
				siniestro.getTipoSolicitud() != null ? Integer.parseInt(siniestro.getTipoSolicitud()) : 0,
				siniestro.getFechaInicioConsulta() != null ? sdf.parse(siniestro.getFechaInicioConsulta()) : null);

	}

	@Override
	public void consultaPorvenirPorAfiliado(String usuario, String proceso, Long documento, String tipoDoc,
			Integer tipoSolicitud, Date fechaProceso) throws SiprenException, ParseException {
//		GnrEquivalenciaTipos gnrEquivalencias = repoEquivalencias.obtenerEquivalenciaAfp(tipoDoc);
//		tipoDoc = gnrEquivalencias != null ? gnrEquivalencias.getAbreviaturaTipoIdAfp() : "";
		repo.consultaPorvenirPorAfiliado(usuario, proceso, documento, tipoDoc, tipoSolicitud, fechaProceso);

	}

	@Override
	public Long consultaUltimoSiniestro(Long numPersona) throws SiprenException {
		return repo.consultaUltimoSiniestro(numPersona);
	}
}
package co.com.segurosalfa.siniestros.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.segurosalfa.siniestros.dto.FiltroTramitesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoTramiteDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoTramite;
import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.sipren.common.util.ServiceException;

public interface IDatoTramiteService extends ICRUD<SnrDatoTramite, Long> {


	List<SnrDatoTramiteDTO> listarDatosXSiniestro(Long numSiniestro) throws SiprenException;

	List<SnrDatoTramiteDTO> listarDatosPorPersona(Long numPersona) throws SiprenException;

	SnrDatoTramiteDTO consultarPorId(Long id) throws SiprenException;

	Long ultimoTramiteXSiniestro(Long numSiniestro) throws SiprenException;

	ResponsePageableDTO listarPorFiltro(FiltroTramitesDTO dto, Pageable page) throws JsonProcessingException, ServiceException, SiprenException;

	void actualizaEstadoTramite(Long numTramite, Integer codEstado) throws SiprenException;

}

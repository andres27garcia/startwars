package co.com.segurosalfa.siniestros.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import co.com.segurosalfa.siniestros.dto.FiltroTramitesDTO;
import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;
import co.com.segurosalfa.siniestros.dto.SnrDatoTramiteDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoTramite;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IDatoTramiteService extends ICRUD<SnrDatoTramite, Long> {

	ResponsePageableDTO listarPaginado(Pageable page) throws SiprenException;

	List<SnrDatoTramiteDTO> listarDatosXSiniestro(Long numSiniestro) throws SiprenException;

	List<SnrDatoTramiteDTO> listarDatosPorPersona(Long numPersona) throws SiprenException;

	SnrDatoTramiteDTO consultarPorId(Long id) throws SiprenException;

	Long ultimoTramiteXSiniestro(Long numSiniestro) throws SiprenException;

	ResponsePageableDTO listarPorFiltro(FiltroTramitesDTO dto, Pageable page);

	void actualizaEstadoTramite(Long numTramite, Integer codEstado) throws SiprenException;

}

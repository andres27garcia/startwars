package co.com.segurosalfa.siniestros.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.SnrInstanciaJuridica;
import co.com.segurosalfa.siniestros.entity.SnrInstanciaJuridicaId;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ISnrInstanciaJuridicaRepo extends IGenericRepo<SnrInstanciaJuridica, SnrInstanciaJuridicaId> {

	@Query("FROM SnrInstanciaJuridica o where o.id.procesoJuridico.idProcesoJuridico = :idProcesoJuridico")
	List<SnrInstanciaJuridica> listarPorProcesoJuridico(@Param("idProcesoJuridico") Long idProcesoJuridico)
			throws SiprenException;

	@Query("FROM SnrInstanciaJuridica o where o.id.procesoJuridico.idProcesoJuridico = :idProcesoJuridico and o.tipoInstancia.id = :idTipoInstancia")
	List<SnrInstanciaJuridica> listarPorProcesoJuridicoTipoInstancia(
			@Param("idProcesoJuridico") Long idProcesoJuridico, @Param("idTipoInstancia") Long idTipoInstancia)
			throws SiprenException;

}

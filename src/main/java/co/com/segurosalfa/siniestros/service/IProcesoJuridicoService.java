package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.entity.SnrProcesoJuridico;

public interface IProcesoJuridicoService extends ICRUD<SnrProcesoJuridico, Integer>{
	public List<SnrProcesoJuridico> listarPorPersona(Long numPersona);
}

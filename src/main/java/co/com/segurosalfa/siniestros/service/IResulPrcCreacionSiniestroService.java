package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.entity.SnrResulPrcCreacionSiniestro;

public interface IResulPrcCreacionSiniestroService extends ICRUD<SnrResulPrcCreacionSiniestro, Integer>{

	public List<SnrResulPrcCreacionSiniestro> consultarPorProceso(String proceso);

}
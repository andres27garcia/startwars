package co.com.segurosalfa.siniestros.service;

import co.com.segurosalfa.siniestros.entity.SnrDatoBono;

public interface IDatoBonoService extends ICRUD<SnrDatoBono, Long>{

	public SnrDatoBono obtenerBonosPersona(Long numPersona);
}

package co.com.segurosalfa.siniestros.repo;

import org.springframework.data.jpa.repository.Query;

import co.com.segurosalfa.siniestros.entity.SnrDatoBono;

public interface ISnrDatoBonoRepo extends IGenericRepo<SnrDatoBono, Long>{

	@Query("FROM SnrDatoBono s where s.persona = :numPersona")
	public SnrDatoBono obtenerBonosPersona(Long numPersona);
}

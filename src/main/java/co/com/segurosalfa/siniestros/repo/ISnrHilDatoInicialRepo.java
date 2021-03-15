package co.com.segurosalfa.siniestros.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.segurosalfa.siniestros.entity.SnrHilDatoInicial;
import co.com.segurosalfa.siniestros.exception.SiprenException;

@Repository
public interface ISnrHilDatoInicialRepo extends IGenericRepo<SnrHilDatoInicial, Long>{
	
	@Query("FROM SnrHilDatoInicial hi where hi.persona = :numPersona")
	List<SnrHilDatoInicial> listarPorPersona(@Param("numPersona") Long numPersona) throws SiprenException;
	

	@Transactional
	@Modifying
	@Query("UPDATE SnrHilDatoInicial hi SET hi.blnRegistroInvalido = :blnRegInv where hi.persona = :numPersona and hi.fecInicialCot < :fecha")
	void actualizarRegistroInvalido(@Param("blnRegInv") String blnRegInv, @Param("numPersona") Long numPersona, @Param("fecha") LocalDate fecha);
}
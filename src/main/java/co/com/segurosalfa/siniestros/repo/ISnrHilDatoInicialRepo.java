package co.com.segurosalfa.siniestros.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.segurosalfa.siniestros.entity.SnrHilDatoInicial;

@Repository
public interface ISnrHilDatoInicialRepo extends IGenericRepo<SnrHilDatoInicial, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE SnrHilDatoInicial hi SET hi.blnRegistroInvalido = :blnRegInv where hi.persona = :numPersona and hi.fecInicialCot < :fecha")
	void actualizarRegistroInvalido(@Param("blnRegInv") String blnRegInv, @Param("numPersona") Long numPersona, @Param("fecha") LocalDate fecha);
}
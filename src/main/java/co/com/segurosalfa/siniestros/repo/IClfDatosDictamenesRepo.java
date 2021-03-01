package co.com.segurosalfa.siniestros.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.segurosalfa.siniestros.entity.ClfDatosDictamenes;
import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface IClfDatosDictamenesRepo extends IGenericRepo<ClfDatosDictamenes, Integer> {

	@Query("FROM ClfDatosDictamenes e WHERE e.idDictamen = (SELECT MAX(d.idDictamen) "
			+ "												FROM ClfDatosDictamenes d"
			+ "												WHERE d.idTramite =(SELECT e.idTramite FROM ClfDatosTramites e"
			+ "																	WHERE e.numPersona = :numPersona ))")
	ClfDatosDictamenes consultaPorPersona(@Param("numPersona") Integer numPersona) throws SiprenException;

}

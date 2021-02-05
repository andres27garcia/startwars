package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class SnrInstanciaJuridicaId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private SnrProcesoJuridico procesoJuridico;
	private SnrTipo instanciaJuridica;
}

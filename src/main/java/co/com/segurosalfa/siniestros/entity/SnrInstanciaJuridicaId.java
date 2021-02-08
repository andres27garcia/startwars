package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class SnrInstanciaJuridicaId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "ID_PROCESO_JURIDICO")
	private SnrProcesoJuridico procesoJuridico;
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_INSTANCIA_JUR")
	private SnrTipo instanciaJuridica;
}

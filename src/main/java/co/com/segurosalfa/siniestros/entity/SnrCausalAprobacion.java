package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "SNR_CAUSAL_APROBACION")
public class SnrCausalAprobacion implements Serializable{
	
	private static final long serialVersionUID = 8168959864039870361L;
	
	@Id
	@Column(name = "COD_CAUSAL_APROBACION_SNR")
	private Long codigo;
	@Column(name = "NOMBRE") 
	private String nombre;
}

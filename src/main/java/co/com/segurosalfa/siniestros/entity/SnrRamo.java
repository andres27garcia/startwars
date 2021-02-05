package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_RAMO")
public class SnrRamo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "COD_RAMO")
	private Integer id;
	@Column(name = "NOMBRE")
	private String nombre;
	

}

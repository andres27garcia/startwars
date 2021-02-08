package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrHilOrigenHistoriaDTO implements Serializable {

	private static final long serialVersionUID = 3819422111789832446L;
	private Integer id;
	private String nombre;

}
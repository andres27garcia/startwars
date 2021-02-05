package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ActualizaEstadoSiniestroDTO implements Serializable {

	private static final long serialVersionUID = -3732062851808497299L;
	@NotNull
	private Long id;
	@NotNull
	private Integer codEstado;
}

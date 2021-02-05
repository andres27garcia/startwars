package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;

public class ListadoDTO implements Serializable {

	private static final long serialVersionUID = 5872676178687052098L;

	private Integer id;
	private String nombre;

	public ListadoDTO() {
		super();
	}

	public ListadoDTO(Integer id) {
		super();
		this.id = id;
	}

	public ListadoDTO(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
package com.devsuperior.bds04.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.services.validations.CityInsertValid;

@CityInsertValid
public class CityInsertDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo requerido")
	private String name;
	
	public CityInsertDTO() {
	}

	public CityInsertDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CityInsertDTO(City entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

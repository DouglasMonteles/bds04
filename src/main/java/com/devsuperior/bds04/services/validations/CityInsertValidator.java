package com.devsuperior.bds04.services.validations;

import java.util.ArrayList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.bds04.dto.CityInsertDTO;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.resources.exceptions.FieldMessage;

public class CityInsertValidator implements ConstraintValidator<CityInsertValid, CityInsertDTO> {

	@Autowired
	private CityRepository cityRepository;
	
	@Override
	public boolean isValid(CityInsertDTO dto, 
			ConstraintValidatorContext context) {
		var list = new ArrayList<FieldMessage>();
		var city = this.cityRepository.findByName(dto.getName());
		
		if (city != null) {
			list.add(new FieldMessage("name", "Cidade já cadastrada!"));
		}
		
		list.forEach(item -> {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(item.getMessage())
				.addPropertyNode(item.getFieldName())
				.addConstraintViolation();
		});
		
		return list.isEmpty();
	}
	
}

package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.CityInsertDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		return this.cityRepository.findAll(Sort.by("name")).stream()
				.map(city -> new CityDTO(city))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public CityInsertDTO insert(CityInsertDTO cityDTO) {
		var city = new City();
		
		city.setName(cityDTO.getName());
		city = this.cityRepository.save(city);
		
		return new CityInsertDTO(city);
	}
	
}

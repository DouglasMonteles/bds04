package com.devsuperior.bds04.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.CityInsertDTO;
import com.devsuperior.bds04.services.CityService;

@RestController
@RequestMapping(value = "/cities")
public class CityResource {

	@Autowired
	private CityService cityService;
	
	@GetMapping
	public ResponseEntity<List<CityDTO>> findAll() {
		var citiesDTO = this.cityService.findAll();
		return ResponseEntity.ok().body(citiesDTO);
	}
	
	@PostMapping
	public ResponseEntity<CityInsertDTO> insert(@Valid @RequestBody CityInsertDTO cityDTO) {
		var newCityDTO = this.cityService.insert(cityDTO);
		
		var uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newCityDTO.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(newCityDTO);
	}
	
}

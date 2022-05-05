package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAll(Pageable pageable) {
		return this.eventRepository
				.findAll(pageable)
				.map(event -> new EventDTO(event));
	}
	
	@Transactional
	public EventDTO insert(EventDTO eventDTO) {
		var event = new Event();
		
		event.setName(eventDTO.getName());
		event.setDate(eventDTO.getDate());
		event.setUrl(eventDTO.getUrl());
		
		var city = this.cityRepository.getOne(eventDTO.getCityId());
		event.setCity(city);
		
		event = this.eventRepository.save(event);
		
		return new EventDTO(event);
	}
	
}

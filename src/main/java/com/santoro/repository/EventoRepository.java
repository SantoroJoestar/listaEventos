package com.santoro.repository;

import org.springframework.data.repository.CrudRepository;

import com.santoro.models.Eventos;

public interface EventoRepository extends CrudRepository<Eventos, Long>{

	public Eventos findById(long id); 
	 
}

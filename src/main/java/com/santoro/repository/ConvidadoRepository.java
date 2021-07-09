package com.santoro.repository;

import org.springframework.data.repository.CrudRepository;

import com.santoro.models.Convidado;
import com.santoro.models.Eventos;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{

	Iterable<Convidado> findByEvento(Eventos eventos);
	Convidado findByRg(String rg);
}

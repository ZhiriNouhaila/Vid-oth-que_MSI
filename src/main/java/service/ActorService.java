package service;

import java.util.List;
import java.util.Optional;
import  model.Actor;

public interface ActorService {

	Optional<Actor> findById(Long id);
	List<Actor> findAll(String search);
	Actor insert(Actor artist);
	Actor update(Long id, Actor artist);
	void delete(Long id);
}



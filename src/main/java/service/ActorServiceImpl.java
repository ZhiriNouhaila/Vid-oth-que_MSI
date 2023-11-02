package service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Actor;
import repository.ActorRepository;

@Service

public class ActorServiceImpl implements ActorService {
	@Autowired
	private ActorRepository repository;
	
	@Override
	public List<Actor> findAll(String search) {
		if (! "".equals(search))
			return repository.findByNameContaining(search);
		else
			return repository.findAll();
	}
	
	@Override
	public Optional<Actor> findById (Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Actor insert(Actor Actor) {
		return repository.save(Actor);
	}
	
	@Override
	public Actor update(Long id, Actor Actor) {
		Optional<Actor> optionalActor = this.findById(id);
		if(optionalActor.isPresent()) {
			return repository.save(Actor);
		}
		return null;
	}
	
	@Override
	public void delete(Long id) {
		Optional<Actor> Actor = this.findById(id);
		if (Actor.isPresent()) {
			repository.delete(Actor.get());
		}
	}

}

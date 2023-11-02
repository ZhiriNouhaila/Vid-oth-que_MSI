package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long>{
	
	List<Actor> findByNameContaining(String name);
}

package controller;


import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import model.Actor;
import service.ActorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/actor")
public class ActorController {

	@Autowired
	ActorService service;
	
	@CrossOrigin
	@GetMapping("/actors")
	public ResponseEntity<List<Actor>> getAllActor(@RequestParam(value="search", defaultValue="") String search) {
		List<Actor> listActor;
		try {
			listActor = service.findAll(search);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(listActor);
	}

	@CrossOrigin
	@GetMapping("/actors/{id}")
	ResponseEntity<Actor> getActorById(@PathVariable(value="id") long id) {
		Optional<Actor> Actor = service.findById(id);
		if (Actor.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(Actor.get());
	}

	@CrossOrigin
	@PostMapping("/actors")
	ResponseEntity<Actor> addActor(@Valid @RequestBody Actor Actor){
		return ResponseEntity.ok().body(service.insert(Actor));
	}
	
	@CrossOrigin
	@PutMapping("/actors/{id}")
	ResponseEntity<Actor> updateActor(@PathVariable(value="id") long id, @Valid @RequestBody Actor Actor){
		Actor updatedActore = service.update(id, Actor);
		if(updatedActore == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok().body(updatedActore);
	}

	@CrossOrigin
	@DeleteMapping("/actors/{id}")
	ResponseEntity<Actor> deleteActor(@PathVariable(value="id") long id){
		Optional<Actor> Actor = service.findById(id);
		if(Actor.isEmpty())
			return ResponseEntity.notFound().build();
		
		service.delete(Actor.get().getId());
		return ResponseEntity.accepted().build();
	}

}
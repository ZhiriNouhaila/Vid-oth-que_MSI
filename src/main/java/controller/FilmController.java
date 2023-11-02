package controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import model.Film;
import service.FilmService;

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

@RestController
public class FilmController {
	@Autowired
	FilmService service;

	@CrossOrigin
	@GetMapping("/films")
	public ResponseEntity<List<Film>> getAllFilm(@RequestParam(value="search", defaultValue="") String search) {
		List<Film> listFilm;
		try {
			listFilm = service.findAll(search);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(listFilm);
	}

	@CrossOrigin
	@GetMapping("/films/{id}")
	ResponseEntity<Film> getFilmById(@PathVariable(value="id") long id) {
		Optional<Film> Film = service.findById(id);
		if (Film.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(Film.get());
	}
	
	@CrossOrigin
	@PostMapping("/films")
	ResponseEntity<Film> addFilm(@Valid @RequestBody Film Film){
		return ResponseEntity.ok().body(service.insert(Film));
	}
	
	@CrossOrigin
	@PutMapping("/films/{id}")
	ResponseEntity<Film> updateFilm(@PathVariable(value="id") long id, @Valid @RequestBody Film Film){
		Film updatedFilm = service.update(id, Film);
		if(updatedFilm == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok().body(updatedFilm);
	}

	@CrossOrigin
	@DeleteMapping("/films/{id}")
	ResponseEntity<Film> deleteFilm(@PathVariable(value="id") long id){
		Optional<Film> Film = service.findById(id);
		if(Film.isEmpty())
			return ResponseEntity.notFound().build();
		
		service.delete(Film.get().getId());
		return ResponseEntity.accepted().build();
	}
}

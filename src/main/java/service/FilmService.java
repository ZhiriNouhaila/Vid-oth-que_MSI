package service;
import java.util.List;
import java.util.Optional;

import model.Film;

public interface FilmService {
	Optional<Film> findById(Long id);
	List<Film> findAll(String search);
	Film insert(Film artist);
	Film update(Long id, Film artist);
	void delete(Long id);
}

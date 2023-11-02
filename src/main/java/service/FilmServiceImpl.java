package service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.AppException;
import model.Film;
import repository.FilmRepository;

@Service
public class FilmServiceImpl implements FilmService {
	@Autowired
	private FilmRepository repository;
	
	@Override
	public List<Film> findAll(String search) {
		if (! "".equals(search))
			return repository.findByTitleIgnoreCaseContaining(search);
		else
			return repository.findAll();
	}
	
	@Override
	public Optional<Film> findById (Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Film insert(Film Film) {
		return repository.save(Film);
	}
	
	@Override
	public Film update(Long id, Film Film) {
		Optional<Film> optionalFilm = this.findById(id);
		if(optionalFilm.isPresent()) {
			return repository.save(Film);
		}
		return null;
	}
	
	@Override
	public void delete(Long id) {
		Optional<Film> Film = this.findById(id);
		if (Film.isPresent()) {

			if (!Film.get().getActors().isEmpty())
				throw new AppException("Invalid Delete, Film in use", "Film in playlist");
			repository.delete(Film.get());
		}
	}
}

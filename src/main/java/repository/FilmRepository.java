package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Film;


public interface FilmRepository extends JpaRepository<Film, Long>  {

	List<Film> findByTitleIgnoreCaseContaining(String title) ;
}


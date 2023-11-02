package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import model.Category;
import model.Film;

@DataJpaTest
public class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void testFindByTitleIgnoreCaseContaining() {
        // Given
        Category category = new Category(1, "Action", null);
        Film film1 = new Film(1, "FilmTitle1", "FilmDescription1", null, 120, 2023, category, Collections.emptyList());
        Film film2 = new Film(2, "FilmTitle2", "FilmDescription2", null, 130, 2022, category, Collections.emptyList());
        Film film3 = new Film(3, "FilmTitle3", "FilmDescription3", null, 110, 2021, category, Collections.emptyList());

        filmRepository.save(film1);
        filmRepository.save(film2);
        filmRepository.save(film3);

        // When
        List<Film> foundFilms = filmRepository.findByTitleIgnoreCaseContaining("FilmTitle");

        // Then
        assertThat(foundFilms).isNotNull();
        assertThat(foundFilms).hasSize(3);
        assertThat(foundFilms).contains(film1, film2, film3);
    }

    @Test
    public void testFindByTitleIgnoreCaseContaining_NoMatch() {
        // Given
        Category category = new Category(1, "Action", null);
        Film film1 = new Film(1, "FilmTitle1", "FilmDescription1", null, 120, 2023, category, Collections.emptyList());
        Film film2 = new Film(2, "FilmTitle2", "FilmDescription2", null, 130, 2022, category, Collections.emptyList());
        Film film3 = new Film(3, "FilmTitle3", "FilmDescription3", null, 110, 2021, category, Collections.emptyList());

        filmRepository.save(film1);
        filmRepository.save(film2);
        filmRepository.save(film3);

        // When
        List<Film> foundFilms = filmRepository.findByTitleIgnoreCaseContaining("Nonexistent");

        // Then
        assertThat(foundFilms).isNotNull();
        assertThat(foundFilms).isEmpty();
    }


}

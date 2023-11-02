package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.Category;
import model.Film;
import repository.FilmRepository;

public class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmServiceImpl filmService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        // Given
        long filmId = 1;
        Film film = new Film(filmId, "FilmTitle", "FilmDescription", null, 120, 2023, null, null);

        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        // When
        Optional<Film> foundFilm = filmService.findById(filmId);

        // Then
        assertThat(foundFilm).isPresent();
        assertThat(foundFilm.get()).isEqualTo(film);
    }

    @Test
    public void testFindAll() {
        // Given
        String search = "FilmTitle";
        Category category = new Category(1L, "Action", null);
        Film film1 = new Film(1L, "FilmTitle1", "FilmDescription1", null, 120, 2023, category, null);
        Film film2 = new Film(2L, "FilmTitle2", "FilmDescription2", null, 130, 2022, category, null);
        Film film3 = new Film(3L, "FilmTitle3", "FilmDescription3", null, 110, 2021, category, null);

        List<Film> allFilms = Arrays.asList(film1, film2, film3);

        when(filmRepository.findByTitleIgnoreCaseContaining(search)).thenReturn(allFilms);

        // When
        List<Film> foundFilms = filmService.findAll(search);

        // Then
        assertThat(foundFilms).isNotNull();
        assertThat(foundFilms).hasSize(3);
        assertThat(foundFilms).contains(film1, film2, film3);
    }

    @Test
    public void testInsert() {
        // Given
        Film filmToInsert = new Film(1, "FilmTitle", "FilmDescription", null, 120, 2023, null, null);

        when(filmRepository.save(isA(Film.class))).thenReturn(filmToInsert);

        // When
        Film insertedFilm = filmService.insert(filmToInsert);

        // Then
        assertThat(insertedFilm).isNotNull();
        assertThat(insertedFilm.getTitle()).isEqualTo(filmToInsert.getTitle());
        assertThat(insertedFilm.getDescription()).isEqualTo(filmToInsert.getDescription());
    }

    @Test
    public void testUpdate() {
        // Given
        long filmId = 1;
        Film existingFilm = new Film(filmId, "FilmTitle", "FilmDescription", null, 120, 2023, null, null);
        Film updatedFilm = new Film(filmId, "Updated Title", "Updated Description", null, 130, 2022, null, null);

        when(filmRepository.findById(filmId)).thenReturn(Optional.of(existingFilm));
        when(filmRepository.save(isA(Film.class))).thenReturn(updatedFilm);

        // When
        Film result = filmService.update(filmId, updatedFilm);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(updatedFilm.getTitle());
        assertThat(result.getDescription()).isEqualTo(updatedFilm.getDescription());
    }

    @Test
    public void testDelete() {
        // Given
        long filmId = 1;

        // When
        filmService.delete(filmId);

        // Then
        verify(filmRepository, times(1)).deleteById(filmId);
    }
}

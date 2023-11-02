package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Actor;
import model.Category;
import model.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service.FilmService;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FilmController.class)
@Tag("controller_film")
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService service;

    @DisplayName("Get all films")
    @Test
    public void getAllFilmsTest() throws Exception {
        mockMvc.perform(get("/films"))
                .andExpect(status().isOk());
    }

    @DisplayName("Get one film by ID")
    @Test
    public void getFilmByIdTest() throws Exception {
        // GIVEN
        long filmId = 1;
        Optional<Film> film = Optional.of(new Film(
                1,
                "The Notebook",
                "Dès le début du film, le ton est donné. Elle débute avec un vieil homme (Noah) qui lit quelques notes d’un livre dédié à Allie, son amour éternel. ",
                new Date(),
                2,
                2023,
                new Category(1, "romantique", null),
                Collections.singletonList(new Actor(1, "Robert De Niro", null))
        ));
        film.get().setId(filmId);
        when(service.findById(filmId)).thenReturn(film);

        // WHEN
        mockMvc.perform(get("/films/{id}", filmId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("FilmTitle"))
                .andExpect(jsonPath("$.description").value("FilmDescription"))
                .andExpect(jsonPath("$.duration").value("2"))
                .andExpect(jsonPath("$.year").value("2023"))
                .andExpect(jsonPath("$.category.id").value("1"))
                .andExpect(jsonPath("$.category.name").value("CategoryName"))
                .andExpect(jsonPath("$.actors[0].id").value("1"))
                .andExpect(jsonPath("$.actors[0].name").value("ActorName"));
    }

    @DisplayName("Add film")
    @Test
    public void addFilmTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new Film(
                1,
                "purple heart",
                "film de deux amooureux vivant une relation à distance",
                new Date(),
                2,
                2023,
                new Category(1, "Drame", null),
                Collections.singletonList(new Actor(1, "lAURA", null))
        ));

        Film filmOut = new Film(
        		1,
                "purple heart",
                "film de deux amooureux vivant une relation à distance",
                new Date(),
                2,
                2023,
                new Category(1, "Drame", null),
                Collections.singletonList(new Actor(1, "lAURA", null))
        );
        when(service.insert(isA(Film.class))).thenReturn(filmOut);

        // WHEN
        mockMvc.perform(post("/films")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("FilmTitle"))
                .andExpect(jsonPath("$.description").value("FilmDescription"))
                .andExpect(jsonPath("$.duration").value("2"))
                .andExpect(jsonPath("$.year").value("2023"))
                .andExpect(jsonPath("$.category.id").value("1"))
                .andExpect(jsonPath("$.category.name").value("CategoryName"))
                .andExpect(jsonPath("$.actors[0].id").value("1"))
                .andExpect(jsonPath("$.actors[0].name").value("ActorName"));
    }

    @DisplayName("Update film")
    @Test
    public void updateFilmTest() throws Exception {
        // GIVEN
        long filmId = 1;
        Film film = new Film(
                1,
                "The Fault in Our Stars",
                "Ce film a été l’un des plus plébiscités par le public car il est empreint d’une rare émotion.",
                new Date(),
                2,
                2023,
                new Category(1, "Romantique", null),
                Collections.singletonList(new Actor(1, "ActorName", null))
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(film);

        when(service.update(isA(Long.class), isA(Film.class))).thenReturn(film);

        // WHEN
        mockMvc.perform(put("/films/{id}", filmId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("FilmTitle"))
                .andExpect(jsonPath("$.description").value("FilmDescription"))
                .andExpect(jsonPath("$.duration").value("2"))
                .andExpect(jsonPath("$.year").value("2023"))
                .andExpect(jsonPath("$.category.id").value("1"))
                .andExpect(jsonPath("$.category.name").value("CategoryName"))
                .andExpect(jsonPath("$.actors[0].id").value("1"))
                .andExpect(jsonPath("$.actors[0].name").value("ActorName"));
    }

    @DisplayName("Delete film")
    @Test
    public void deleteFilmTest() throws Exception {
        // GIVEN
        long filmId = 1;
        Optional<Film> film = Optional.of(new Film(
        		1,
                "purple heart",
                "film de deux amooureux vivant une relation à distance",
                new Date(),
                2,
                2023,
                new Category(1, "Drame", null),
                Collections.singletonList(new Actor(1, "lAURA", null))
        ));
        film.get().setId(filmId);
        when(service.findById(filmId)).thenReturn(film);
        doNothing().when(service).delete(filmId);

        // WHEN
        mockMvc.perform(delete("/films/{id}", filmId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}

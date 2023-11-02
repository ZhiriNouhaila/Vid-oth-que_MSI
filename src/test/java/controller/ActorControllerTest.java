package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Actor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service.ActorService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ActorController.class)
@Tag("controller_actor")
public class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService service;

    @DisplayName("Get all actors")
    @Test
    public void getAllActorsTest() throws Exception {
        mockMvc.perform(get("/actor/actors"))
                .andExpect(status().isOk());
    }

    @DisplayName("Get one actor by ID")
    @Test
    public void getActorByIdTest() throws Exception {
        // GIVEN
        long actorId = 1;
        Optional<Actor> actor = Optional.of(new Actor(1, "Angelina", "Beautiful actor"));
        actor.get().setId(actorId);
        when(service.findById(actorId)).thenReturn(actor);

        // WHEN
        mockMvc.perform(get("/actor/actors/{id}", actorId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("ActorName"))
                .andExpect(jsonPath("$.bio").value("ActorBio"));
    }

    @DisplayName("Add actor")
    @Test
    public void addActorTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new Actor(1, "Damon salvator", "the big bad vampire"));

        Actor actorOut = new Actor(1, "Damon salvator", "the big bad vampire");
        when(service.insert(isA(Actor.class))).thenReturn(actorOut);

        // WHEN
        mockMvc.perform(post("/actor/actors")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("ActorName"))
                .andExpect(jsonPath("$.bio").value("ActorBio"));
    }

    @DisplayName("Update actor")
    @Test
    public void updateActorTest() throws Exception {
        // GIVEN
        long actorId = 1;
        Actor actor = new Actor(1, "chandler bing", "the funniest actor");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(actor);

        when(service.update(isA(Long.class), isA(Actor.class))).thenReturn(actor);

        // WHEN
        mockMvc.perform(put("/actor/actors/{id}", actorId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("ActorName"))
                .andExpect(jsonPath("$.bio").value("ActorBio"));
    }

    @DisplayName("Delete actor")
    @Test
    public void deleteActorTest() throws Exception {
        // GIVEN
        long actorId = 1;
        Optional<Actor> actor = Optional.of(new Actor(1, "chandler bing", "the funniest actor"));
        actor.get().setId(actorId);
        when(service.findById(actorId)).thenReturn(actor);
        doNothing().when(service).delete(actorId);

        // WHEN
        mockMvc.perform(delete("/actor/actors/{id}", actorId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}

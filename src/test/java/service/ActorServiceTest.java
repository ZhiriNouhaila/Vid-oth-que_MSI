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

import model.Actor;
import repository.ActorRepository;

public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        // Given
        long actorId = 1;
        Actor actor = new Actor(actorId, "John Doe", "Bio");

        when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));

        // When
        Optional<Actor> foundActor = actorService.findById(actorId);

        // Then
        assertThat(foundActor).isPresent();
        assertThat(foundActor.get()).isEqualTo(actor);
    }

    @Test
    public void testFindAll() {
        // Given
        String search = "Doe";
        Actor actor1 = new Actor(1L, "John Doe", "Bio1");
        Actor actor2 = new Actor(2L, "Jane Doe", "Bio2");
        Actor actor3 = new Actor(3L, "Bob Smith", "Bio3");

        List<Actor> allActors = Arrays.asList(actor1, actor2, actor3);

        when(actorRepository.findByNameContaining(search)).thenReturn(allActors);

        // When
        List<Actor> foundActors = actorService.findAll(search);

        // Then
        assertThat(foundActors).isNotNull();
        assertThat(foundActors).hasSize(2); // Assuming "Doe" matches two actors
        assertThat(foundActors).contains(actor1, actor2);
    }

    @Test
    public void testInsert() {
        // Given
        Actor actorToInsert = new Actor(1, "John Doe", "Bio");

        when(actorRepository.save(isA(Actor.class))).thenReturn(actorToInsert);

        // When
        Actor insertedActor = actorService.insert(actorToInsert);

        // Then
        assertThat(insertedActor).isNotNull();
        assertThat(insertedActor.getName()).isEqualTo(actorToInsert.getName());
        assertThat(insertedActor.getBio()).isEqualTo(actorToInsert.getBio());
    }

    @Test
    public void testUpdate() {
        // Given
        long actorId = 1;
        Actor existingActor = new Actor(actorId, "John Doe", "Bio");
        Actor updatedActor = new Actor(actorId, "Updated Name", "Updated Bio");

        when(actorRepository.findById(actorId)).thenReturn(Optional.of(existingActor));
        when(actorRepository.save(isA(Actor.class))).thenReturn(updatedActor);

        // When
        Actor result = actorService.update(actorId, updatedActor);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updatedActor.getName());
        assertThat(result.getBio()).isEqualTo(updatedActor.getBio());
    }

    @Test
    public void testDelete() {
        // Given
        long actorId = 1;

        // When
        actorService.delete(actorId);

        // Then
        verify(actorRepository, times(1)).deleteById(actorId);
    }
}

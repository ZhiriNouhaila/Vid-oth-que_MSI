package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import model.Actor;

@DataJpaTest
public class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    @Test
    public void testFindByNameContaining() {
        // Given
    	Actor actor1 = new Actor(1, "John Doe", "Bio1");
        Actor actor2 = new Actor(2, "Jane Doe", "Bio2");
        Actor actor3 = new Actor(3, "Bob Smith", "Bio3");

        actorRepository.save(actor1);
        actorRepository.save(actor2);
        actorRepository.save(actor3);

        // When
        List<Actor> foundActors = actorRepository.findByNameContaining("Doe");

        // Then
        assertThat(foundActors).isNotNull();
        assertThat(foundActors).hasSize(2);
        assertThat(foundActors).contains(actor1, actor2);
    }

    @Test
    public void testFindByNameContaining_NoMatch() {
        // Given
    	Actor actor1 = new Actor(1, "John Doe", "Bio1");
        Actor actor2 = new Actor(2, "Jane Doe", "Bio2");
        Actor actor3 = new Actor(3, "Bob Smith", "Bio3");

        actorRepository.save(actor1);
        actorRepository.save(actor2);
        actorRepository.save(actor3);

        // When
        List<Actor> foundActors = actorRepository.findByNameContaining("Nonexistent");

        // Then
        assertThat(foundActors).isNotNull();
        assertThat(foundActors).isEmpty();
    }

    @Test
    public void testFindById() {
        // Given
    	Actor actor = new Actor(1, "John Doe", "Bio1");
        actor = actorRepository.save(actor);

        // When
        Optional<Actor> foundActor = actorRepository.findById(actor.getId());

        // Then
        assertThat(foundActor).isPresent();
        assertThat(foundActor.get()).isEqualTo(actor);
    }

    @Test
    public void testFindById_NotFound() {
        // Given an actor with a known ID
        long nonExistentId = 999L;

        // When
        Optional<Actor> foundActor = actorRepository.findById(nonExistentId);

        // Then
        assertThat(foundActor).isNotPresent();
    }
}

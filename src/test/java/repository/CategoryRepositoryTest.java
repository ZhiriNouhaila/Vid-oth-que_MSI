package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import model.Category;
import model.Film;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindByTitleIgnoreCaseContaining() {
        // Given
    	Film film = new Film(1, "FilmTitle", "FilmDescription", null, 120, 2023, null, Collections.emptyList());
        Category category1 = new Category(1, "Action", Collections.singletonList(film));
        Category category2 = new Category(2, "Drama", null);
        Category category3 = new Category(3, "Comedy", null);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // When
        List<Category> foundCategories = categoryRepository.findByTitleIgnoreCaseContaining("dr");

        // Then
        assertThat(foundCategories).isNotNull();
        assertThat(foundCategories).hasSize(1);
        assertThat(foundCategories.get(0)).isEqualTo(category2);
    }

    @Test
    public void testFindByArtistId() {
        // Given
    	Film film = new Film(1, "FilmTitle", "FilmDescription", null, 120, 2023, null, Collections.emptyList());
        Category category1 = new Category(1, "Action", Collections.singletonList(film));
        Category category2 = new Category(2, "Drama", null);
        Category category3 = new Category(3, "Comedy", null);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // When
        List<Category> foundCategories = categoryRepository.findByArtistId(category2.getId());

        // Then
        assertThat(foundCategories).isNotNull();
        assertThat(foundCategories).hasSize(1);
        assertThat(foundCategories.get(0)).isEqualTo(category2);
    }

    @Test
    public void testFindByArtistId_NoMatch() {
        // Given
    	Film film = new Film(1, "FilmTitle", "FilmDescription", null, 120, 2023, null, Collections.emptyList());
        Category category1 = new Category(1, "Action", Collections.singletonList(film));
        Category category2 = new Category(2, "Drama", null);        
        Category category3 = new Category(3, "Comedy", null);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // When
        List<Category> foundCategories = categoryRepository.findByArtistId(999L); // Nonexistent ID

        // Then
        assertThat(foundCategories).isNotNull();
        assertThat(foundCategories).isEmpty();
    }

    @Test
    public void testFindById() {
        // Given
    	Film film = new Film(1, "FilmTitle", "FilmDescription", null, 120, 2023, null, Collections.emptyList());
        Category category = new Category(1, "Action", Collections.singletonList(film));
        category = categoryRepository.save(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());

        // Then
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get()).isEqualTo(category);
    }

    @Test
    public void testFindById_NotFound() {
        // Given a category with a known ID
        long nonExistentId = 999L;

        // When
        Optional<Category> foundCategory = categoryRepository.findById(nonExistentId);

        // Then
        assertThat(foundCategory).isNotPresent();
    }
}

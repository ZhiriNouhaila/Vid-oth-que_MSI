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
import repository.CategoryRepository;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        // Given
        long categoryId = 1;
        Category category = new Category(categoryId, "Action", null);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        Optional<Category> foundCategory = categoryService.findById(categoryId);

        // Then
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get()).isEqualTo(category);
    }

    @Test
    public void testFindAll() {
        // Given
        String search = "Action";
        Category category1 = new Category(1L, "Action", null);
        Category category2 = new Category(2L, "Drama", null);
        Category category3 = new Category(3L, "Comedy", null);

        List<Category> allCategories = Arrays.asList(category1, category2, category3);

        when(categoryRepository.findByTitleIgnoreCaseContaining(search)).thenReturn(allCategories);

        // When
        List<Category> foundCategories = categoryService.findAll(search);

        // Then
        assertThat(foundCategories).isNotNull();
        assertThat(foundCategories).hasSize(1); // Assuming "Action" matches one category
        assertThat(foundCategories).contains(category1);
    }

    @Test
    public void testFindByFilm() {
        // Given
        long filmId = 1;
        Category category1 = new Category(1L, "Action", null);
        Category category2 = new Category(2L, "Drama", null);
        Category category3 = new Category(3L, "Comedy", null);

        List<Category> allCategories = Arrays.asList(category1, category2, category3);

        when(categoryRepository.findByArtistId(filmId)).thenReturn(allCategories);

        // When
        List<Category> foundCategories = categoryService.findByFilm(filmId);

        // Then
        assertThat(foundCategories).isNotNull();
        assertThat(foundCategories).hasSize(3);
        assertThat(foundCategories).contains(category1, category2, category3);
    }

    @Test
    public void testInsert() {
        // Given
        Category categoryToInsert = new Category(1, "Action", null);

        when(categoryRepository.save(isA(Category.class))).thenReturn(categoryToInsert);

        // When
        Category insertedCategory = categoryService.insert(categoryToInsert);

        // Then
        assertThat(insertedCategory).isNotNull();
        assertThat(insertedCategory.getName()).isEqualTo(categoryToInsert.getName());
    }

    @Test
    public void testUpdate() {
        // Given
        long categoryId = 1;
        Category existingCategory = new Category(categoryId, "Action", null);
        Category updatedCategory = new Category(categoryId, "Updated Category", null);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(isA(Category.class))).thenReturn(updatedCategory);

        // When
        Category result = categoryService.update(categoryId, updatedCategory);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updatedCategory.getName());
    }

    @Test
    public void testDelete() {
        // Given
        long categoryId = 1;

        // When
        categoryService.delete(categoryId);

        // Then
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}

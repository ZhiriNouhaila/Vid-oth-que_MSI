package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import service.CategoryService;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
@Tag("controller_category")
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @DisplayName("Get all categories")
    @Test
    public void getAllCategoriesTest() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk());
    }

    @DisplayName("Get one category by ID")
    @Test
    public void getCategoryByIdTest() throws Exception {
        // GIVEN
        long categoryId = 1;
        Optional<Category> category = Optional.of(new Category(1, "CategoryName", null));
        category.get().setId(categoryId);
        when(service.findById(categoryId)).thenReturn(category);

        // WHEN
        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("CategoryName"));
    }

    @DisplayName("Add category")
    @Test
    public void addCategoryTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();

        // Create a Film object to include in the Category
        Film film = new Film(1,"purple heart","film de deux amooureux vivant une relation à distance",new Date(),
                2,
                2023, null, null);

        String categoryName = "CategoryName";
        Category categoryWithFilm = new Category(1, categoryName, Collections.singletonList(film));
        String json = mapper.writeValueAsString(categoryWithFilm);

        Category categoryOut = new Category(1, categoryName, Collections.singletonList(film));
        when(service.insert(isA(Category.class))).thenReturn(categoryOut);

        // WHEN
        mockMvc.perform(post("/categories")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(categoryName));
    }



    @DisplayName("Update category")
    @Test
    public void updateCategoryTest() throws Exception {
        // GIVEN
        long categoryId = 1;
        Category category = new Category(1, "CategoryName", null);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(category);

        when(service.update(isA(Long.class), isA(Category.class))).thenReturn(category);

        // WHEN
        mockMvc.perform(put("/categories/{id}", categoryId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("CategoryName"));
    }

    @DisplayName("Delete category")
    @Test
    public void deleteCategory() throws Exception {
        // GIVEN
        long categoryId = 1;
        Optional<Category> category = Optional.of(new Category(1, "CategoryName", null));
        category.get().setId(categoryId);
        when(service.findById(categoryId)).thenReturn(category);
        doNothing().when(service).delete(categoryId);

        // WHEN
        mockMvc.perform(delete("/categories/{id}", categoryId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}

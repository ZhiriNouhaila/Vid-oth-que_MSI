package service;
import java.util.List;
import java.util.Optional;

import model.Category;
public interface CategoryService {
	Optional<Category> findById(Long id);
	List<Category> findAll(String search);
	List<Category> findByFilm(Long id);
	Category insert(Category category);
	Category update(Long id, Category category);
	void delete(Long id);
}

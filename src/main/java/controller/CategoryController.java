package controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import model.Category;
import service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CategoryController {

	@Autowired
	CategoryService service;
	
	@CrossOrigin
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategory(@RequestParam(value="search", defaultValue="") String search) {
		List<Category> listCategory;
		try {
			listCategory = service.findAll(search);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(listCategory);
	}

	@CrossOrigin
	@GetMapping("/categories/{id}")
	ResponseEntity<Category> getCategoryById(@PathVariable(value="id") long id) {
		Optional<Category> Category = service.findById(id);
		if (Category.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(Category.get());
	}
	
	@CrossOrigin
	@GetMapping("/films/{id}/categories")
	public ResponseEntity<List<Category>> getAllCategory(@PathVariable(value="id") long id) {
		List<Category> listCategory;
		try {
			listCategory = service.findByFilm(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(listCategory);
	}

	@CrossOrigin
	@PostMapping("/categories")
	ResponseEntity<Category> addCategory(@Valid @RequestBody Category Category){
		return ResponseEntity.ok().body(service.insert(Category));
	}
	
	@CrossOrigin
	@PutMapping("/categories/{id}")
	ResponseEntity<Category> updateCategory(@PathVariable(value="id") long id, @Valid @RequestBody Category Category){
		Category updatedCategory = service.update(id, Category);
		if(updatedCategory == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok().body(updatedCategory);
	}

	@CrossOrigin
	@DeleteMapping("/categories/{id}")
	ResponseEntity<Category> deleteCategory(@PathVariable(value="id") long id){
		Optional<Category> Category = service.findById(id);
		if(Category.isEmpty())
			return ResponseEntity.notFound().build();
		
		service.delete(Category.get().getId());
		return ResponseEntity.accepted().build();
	}
}

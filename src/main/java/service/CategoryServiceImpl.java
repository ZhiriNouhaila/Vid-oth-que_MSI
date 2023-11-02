package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Category;
import repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repository;
	
	@Override
	public List<Category> findAll(String search) {
		if (! "".equals(search))
			return repository.findByTitleIgnoreCaseContaining(search);
		else
			return repository.findAll();
	}
	
	@Override
	public List<Category> findByFilm(Long id) {
		return repository.findByArtistId(id);
	}
	
	@Override
	public Optional<Category> findById (Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Category insert(Category Category) {
		return repository.save(Category);
	}
	
	@Override
	public Category update(Long id, Category Category) {
		Optional<Category> optionalCategory = this.findById(id);
		if(optionalCategory.isPresent()) {
			return repository.save(Category);
		}
		return null;
	}
	
	@Override
	public void delete(Long id) {
		Optional<Category> Category = this.findById(id);
		if (Category.isPresent()) {
			repository.delete(Category.get());
		}
	}
}

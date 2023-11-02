package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
@Entity

public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull(message = "Name cannot be null")
	private String name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<Film> films;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Category(long id, @NotNull(message = "Name cannot be null") String name, List<Film> films) {
		super();
		this.id = id;
		this.name = name;
		this.films = films;
	}
	public List<Film> getFilms() {
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}
}

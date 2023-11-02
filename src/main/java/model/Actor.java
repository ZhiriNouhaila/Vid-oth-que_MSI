package model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;
@Entity

public class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull(message = "Name cannot be null")
	private String name;
    @Size(min = 10, max = 200, message = "Bio must be between 10 and 200 characters")
	private String bio;
	
	
	public Actor(long id, String name, String bio) {
		super();
		this.id = id;
		this.name = name;
		this.bio = bio;
	}
	
	@ManyToMany
	@JoinTable(
			name = "actor_film",
			joinColumns = @JoinColumn(name = "actor_id"),
			inverseJoinColumns = @JoinColumn(name = "film_id")
			  )
	List<Film> films;
	
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
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public List<Film> getFilms() {
		return films;
	}

	public void setTracks(List<Film> films) {
		this.films = films;
	}
}

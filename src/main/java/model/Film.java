package model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Film {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Title can't be empty")
	private String title;
	@Size(min = 10, max = 200, message = "description must be between 10 and 200 characters")
	private String description;
	
	@PastOrPresent(message = "release date must be past")
	private Date releaseDate;
	
	@Min(value = 1, message = "duration should not be less than 1 hour")
    @Max(value = 3, message = "duration should not be greater than 3 hours")
	private int duration;
	
	private int year;
	
	
	
	@ManyToOne @JoinColumn(name="category_id")
	@JsonBackReference
	private Category category;
	
	@ManyToMany(mappedBy = "films")
	@JsonIdentityReference(alwaysAsId = true)
	private List<Actor> actors;	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getYear() {
		return year;
	}
	public Film(long id, @NotBlank(message = "Title can't be empty") String title,
			@Size(min = 10, max = 200, message = "description must be between 10 and 200 characters") String description,
			@PastOrPresent(message = "release date must be past") Date releaseDate,
			@Min(value = 1, message = "duration should not be less than 1 hour") @Max(value = 3, message = "duration should not be greater than 3 hours") int duration,
			int year, Category category, List<Actor> actors) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.duration = duration;
		this.year = year;
		this.category = category;
		this.actors = actors;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

}

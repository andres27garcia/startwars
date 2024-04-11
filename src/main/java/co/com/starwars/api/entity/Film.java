package co.com.starwars.api.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "films")
@Entity
@NoArgsConstructor
public class Film implements Serializable {

	private static final long serialVersionUID = -2716161871893969556L;

	@Id
	@Column(name = "num_film")
	private int num_film;
	@Column(name = "episode_id")
	private int episode_id;
	@Column(name = "title", length = 200, nullable = false)
	private String title;
	@Column(name = "release_date")
	private String release_date;

}
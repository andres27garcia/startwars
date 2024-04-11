package co.com.starwars.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseApi {

	private int num_film;
	private int episode_id;
	private String title;
	private String release_date;

}
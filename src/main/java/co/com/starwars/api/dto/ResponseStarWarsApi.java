package co.com.starwars.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseStarWarsApi {

	private int episode_id;
	private String title;
	private String release_date;
	
}
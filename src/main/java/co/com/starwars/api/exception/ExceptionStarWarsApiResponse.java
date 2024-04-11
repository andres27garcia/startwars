package co.com.starwars.api.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionStarWarsApiResponse {

	private String detail;

	public ExceptionStarWarsApiResponse(String detail) {
		this.detail = detail;
	}

}

package co.com.segurosalfa.siniestros.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiltroGenericoDTO {

	private List<FiltroRangoDTO> rango;
	private List<String> individual;

}

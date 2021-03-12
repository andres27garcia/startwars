package co.com.segurosalfa.siniestros.util;

import org.springframework.data.domain.Page;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.dto.ResponsePageableDTO;

@XRayEnabled
public class PageableUtil {

	public static ResponsePageableDTO responsePageable(Object object, Page<?> page) {
		ResponsePageableDTO response = new ResponsePageableDTO(object, 
				page.getTotalPages(), 
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.isFirst(),
				page.isLast(),
				page.isEmpty());
		return response;
	}
}

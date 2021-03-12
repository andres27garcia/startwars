package co.com.segurosalfa.siniestros.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.xray.spring.aop.XRayEnabled;

import co.com.segurosalfa.siniestros.dto.SnrDatoReclamanteDTO;
import co.com.segurosalfa.siniestros.entity.SnrDatoReclamante;
import co.com.sipren.common.util.ClienteRestGenerico;
import co.com.sipren.common.util.StringUtil;

@Configuration
@XRayEnabled
public class Beans {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(skipFieldsReclamanteDTO);
		return modelMapper;
	}

	PropertyMap<SnrDatoReclamanteDTO, SnrDatoReclamante> skipFieldsReclamanteDTO = new PropertyMap<SnrDatoReclamanteDTO, SnrDatoReclamante>() {
		@Override
		protected void configure() {
			skip().setNumPersona(null);
		}
	};

	@Bean
	public StringUtil stringUtil() {
		StringUtil stringUtil = new StringUtil();
		return stringUtil;
	}

	@Bean
	public ClienteRestGenerico clienteRestGenerico() {
		return new ClienteRestGenerico();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
package co.com.segurosalfa.siniestros.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.sipren.common.util.StringUtil;

@Configuration
public class Beans {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Bean
	public StringUtil stringUtil() {
		StringUtil stringUtil = new StringUtil();
		return stringUtil;
	}

}
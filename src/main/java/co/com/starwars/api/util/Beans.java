package co.com.starwars.api.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {

    @Bean
    RestTemplate restTemplate() {
		return new RestTemplate();
	}
    
    @Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
package co.com.segurosalfa.siniestros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import co.com.sipren.common.util.ClienteRestGenerico;
import co.com.sipren.common.util.EmailUtil;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAsync
@EntityScan(basePackages = { "co.com.segurosalfa.siniestros.entity" })
@ComponentScan({ "co.com.sipren.common.log", "co.com.sipren.common.events", "co.com.segurosalfa.siniestros" })
public class SiniestrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiniestrosApplication.class, args);
	}

	@Bean
	public EmailUtil emailUtil() {
		return new EmailUtil();
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
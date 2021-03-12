package co.com.segurosalfa.siniestros.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;

@Configuration
public class AwsXrayConfig {
	
  @Value("${spring.application.name}")
  private String appName;

  @Bean
  public Filter tracingFilter() {
    return new AWSXRayServletFilter(appName);
  }
}
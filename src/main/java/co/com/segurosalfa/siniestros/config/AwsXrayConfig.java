package co.com.segurosalfa.siniestros.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsXrayConfig {
	
  @Value("${spring.application.name}")
  private String appName;

//  @Bean
//  public Filter tracingFilter() {
//    return new AWSXRayServletFilter(appName);
//  }
}
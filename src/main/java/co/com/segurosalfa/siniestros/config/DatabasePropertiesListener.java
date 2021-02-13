package co.com.segurosalfa.siniestros.config;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Base64;
import java.util.Properties;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class DatabasePropertiesListener implements ApplicationListener<ApplicationPreparedEvent> {

	private final static String DATASOURCE_USERNAME = "spring.datasource.username";
	private final static String DATASOURCE_PASSWORD = "spring.datasource.password";
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		ConfigurableEnvironment env = event.getApplicationContext().getEnvironment();
		String secretName = env.getProperty("spring.aws.secretsmanager.secretName");
		String region = env.getProperty("spring.aws.secretsmanager.region");
		String secretJson = getSecret(secretName, region);
		String dbUser = getString(secretJson, "username");
		String dbPassword = getString(secretJson, "password");
		Properties dbProperties = new Properties();
		dbProperties.put(DATASOURCE_USERNAME, dbUser);
		dbProperties.put(DATASOURCE_PASSWORD, dbPassword);
		env.getPropertySources().addFirst(new PropertiesPropertySource("aws.secret.manager", dbProperties));
	}

	public static String getSecret(String secretName, String region) {
		// Create a Secrets Manager client
		AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(region).build();
		String secret, decodedBinarySecret;
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
		GetSecretValueResult getSecretValueResult = null;

		try {
			getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		} catch (DecryptionFailureException e) {
			throw e;
		} catch (InternalServiceErrorException e) {
			throw e;
		} catch (InvalidParameterException e) {
			throw e;
		} catch (InvalidRequestException e) {
			throw e;
		} catch (ResourceNotFoundException e) {
			throw e;
		}
		if (getSecretValueResult.getSecretString() != null) {
			secret = getSecretValueResult.getSecretString();
			return secret;
		} else {
			decodedBinarySecret = new String(
					Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
			return decodedBinarySecret;
		}
	}

	private String getString(String json, String path) {
		try {
			JsonNode root = mapper.readTree(json);
			return root.path(path).asText();
		} catch (IOException e) {
			log.error("Can't get {} from json {}", path, json, e);
			return null;
		}
	}
}
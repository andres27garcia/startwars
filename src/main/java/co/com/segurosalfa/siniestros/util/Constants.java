package co.com.segurosalfa.siniestros.util;

import com.amazonaws.xray.spring.aop.XRayEnabled;

@XRayEnabled
public class Constants {
	private Constants() {
		
	}
	
	public static final String SINIESTROS_BUSINESS_UNIT = "PREVISIONAL";
	public static final String SINIESTORS_MICROSERVICES = "SINIESTROS";
	
}
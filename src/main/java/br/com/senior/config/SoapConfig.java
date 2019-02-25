package br.com.senior.config;



import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@ConfigurationProperties("senior.webservices.soap")
public class SoapConfig {

	private String host; 
	private String masterUsername;
	private String masterPassword;
	
	

	public SoapConfig (){
		this.host = host;
		this.masterUsername = masterUsername;
		this.masterPassword = masterPassword;

		log.info(this.toString());
	}
	
}

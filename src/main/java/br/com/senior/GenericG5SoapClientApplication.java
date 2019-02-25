package br.com.senior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


import br.com.senior.config.SoapConfig;


@SpringBootApplication
@EnableConfigurationProperties(SoapConfig.class)
public class GenericG5SoapClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenericG5SoapClientApplication.class, args);
	}

}

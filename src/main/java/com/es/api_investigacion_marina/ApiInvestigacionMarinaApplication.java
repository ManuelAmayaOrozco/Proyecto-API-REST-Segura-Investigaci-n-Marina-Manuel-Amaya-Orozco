package com.es.api_investigacion_marina;

import com.es.api_investigacion_marina.Security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ApiInvestigacionMarinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiInvestigacionMarinaApplication.class, args);
	}

}

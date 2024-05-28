package main.java.com.proyectofinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


public class AppConfig {
    @Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}    
}

package com.proyectofinal.polucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;


@SpringBootApplication
@OpenAPIDefinition(
  info =@Info(
    title = "API Polucion",
    version = "v1",
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    description = "API de Polucion"
  ),
  servers = @Server(
    url = "/",
    description = "Production"
  )
)
public class PolucionApplication {

	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	

	public static void main(String[] args) {
		SpringApplication.run(PolucionApplication.class, args);
	}

}

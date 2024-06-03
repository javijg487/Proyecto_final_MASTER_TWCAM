package com.proyectofinal.ayuntamientomongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
  info =@Info(
    title = "Repo mongo Ayuntamiento",
    version = "v1",
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    description = "API de Bicicletas"
  ),
  servers = @Server(
    url = "/",
    description = "Production"
  )
)
public class AyuntamientomongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AyuntamientomongoApplication.class, args);
	}

}

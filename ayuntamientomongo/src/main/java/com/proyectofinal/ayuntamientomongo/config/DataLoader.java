package com.proyectofinal.ayuntamientomongo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.proyectofinal.ayuntamientomongo.repositories.AgregadoMongoRepository;
import com.proyectofinal.ayuntamientomongo.models.AgregadoMongo;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(AgregadoMongoRepository repository) {
        return args -> {
            // Leer el archivo JSON
            if (repository.count() == 0) {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<AgregadoMongo>> typeReference = new TypeReference<List<AgregadoMongo>>() {
                };
                InputStream inputStream = getClass().getResourceAsStream("/data.json");
                try {
                    List<AgregadoMongo> aparcamientos = mapper.readValue(inputStream, typeReference);
                    repository.saveAll(aparcamientos);
                    System.out.println("Datos iniciales cargados.");
                } catch (IOException e) {
                    System.out.println("No se pudo cargar el archivo JSON: " + e.getMessage());
                }
            } else {
                System.out.println("La base de datos ya contiene datos.");
            }
        };
    }
}

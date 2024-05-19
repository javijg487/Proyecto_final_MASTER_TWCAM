package com.proyectofinal.polucionmongo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.proyectofinal.polucionmongo.repositories.EstacionMongoRepository;
import com.proyectofinal.polucionmongo.models.EstacionMongo;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(EstacionMongoRepository repository) {
        return args -> {
            // Leer el archivo JSON
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<EstacionMongo>> typeReference = new TypeReference<List<EstacionMongo>>() {};
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            try {
                List<EstacionMongo> aparcamientos = mapper.readValue(inputStream, typeReference);
                repository.saveAll(aparcamientos);
                System.out.println("Datos iniciales cargados.");
            } catch (IOException e) {
                System.out.println("No se pudo cargar el archivo JSON: " + e.getMessage());
            }
        };
    }
}

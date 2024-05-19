package com.proyectofinal.bicicletasmongo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.proyectofinal.bicicletasmongo.models.AparcamientoMongo;
import com.proyectofinal.bicicletasmongo.repositories.AparcamientoMongoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(AparcamientoMongoRepository repository) {
        return args -> {
            // Leer el archivo JSON
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<AparcamientoMongo>> typeReference = new TypeReference<List<AparcamientoMongo>>() {};
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            try {
                List<AparcamientoMongo> aparcamientos = mapper.readValue(inputStream, typeReference);
                repository.saveAll(aparcamientos);
                System.out.println("Datos iniciales cargados.");
            } catch (IOException e) {
                System.out.println("No se pudo cargar el archivo JSON: " + e.getMessage());
            }
        };
    }
}


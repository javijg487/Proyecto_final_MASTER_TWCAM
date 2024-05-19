package com.proyectofinal.bicicletasmongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.proyectofinal.bicicletasmongo.models.AparcamientoMongo;
import java.util.List;

public interface AparcamientoMongoRepository extends MongoRepository<AparcamientoMongo, Integer>{
    
    List<AparcamientoMongo> findAll();
}



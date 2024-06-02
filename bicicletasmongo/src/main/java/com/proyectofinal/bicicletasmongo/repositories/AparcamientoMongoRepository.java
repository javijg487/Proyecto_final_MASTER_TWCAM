package com.proyectofinal.bicicletasmongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.proyectofinal.bicicletasmongo.models.AparcamientoMongo;

import java.util.List;

public interface AparcamientoMongoRepository extends MongoRepository<AparcamientoMongo, String>{

    List<AparcamientoMongo> findByIdentificadorAndTimestampBetween(Integer id, String from, String to);

    AparcamientoMongo findFirstByIdentificadorOrderByTimestampDesc(Integer id);
    
}



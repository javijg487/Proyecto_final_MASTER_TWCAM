package com.proyectofinal.ayuntamientomongo.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyectofinal.ayuntamientomongo.models.AgregadoMongo;

public interface AgregadoMongoRepository extends MongoRepository<AgregadoMongo, String> {
	
	AgregadoMongo findFirstByOrderByTimeStampDesc();
}

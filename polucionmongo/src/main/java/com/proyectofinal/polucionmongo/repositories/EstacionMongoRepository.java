package com.proyectofinal.polucionmongo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyectofinal.polucionmongo.models.EstacionMongo;

public interface EstacionMongoRepository extends MongoRepository<EstacionMongo, String> {
	
	List<EstacionMongo> findByIdentificador(Integer identificador);
	
	List<EstacionMongo> findByIdentificadorAndTimeStampBetween(Integer id, String from, String to);
}

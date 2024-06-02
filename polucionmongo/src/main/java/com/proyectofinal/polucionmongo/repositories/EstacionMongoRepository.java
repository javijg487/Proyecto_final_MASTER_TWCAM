package com.proyectofinal.polucionmongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyectofinal.polucionmongo.models.EstacionMongo;

public interface EstacionMongoRepository extends MongoRepository<EstacionMongo, String> {
	
	List<EstacionMongo> findByIdentificadorAndTimeStampBetween(Integer id, String from, String to);

	EstacionMongo findFirstByIdentificadorOrderByTimeStampDesc(Integer identificador);
}

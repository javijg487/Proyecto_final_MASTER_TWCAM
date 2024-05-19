package com.proyectofinal.polucionmongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyectofinal.polucionmongo.models.EstacionMongo;

public interface EstacionMongoRepository extends MongoRepository<EstacionMongo, Integer> {
	
	// List<EstacionMongo> findByFirstname(String firstname);
	
	// List<EstacionMongo> findByFirstnameStartingWith(String regexp);
	
	// List<EstacionMongo> findByLastname(String lastname);
	
	// @Query("{ 'lastname' : { $regex: ?0 } }")
	// List<EstacionMongo> findByLastnameStartingWith(String regexp);
	
	// List<EstacionMongo> findByEmail(String email);
	
}

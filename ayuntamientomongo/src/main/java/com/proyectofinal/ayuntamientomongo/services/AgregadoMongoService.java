package com.proyectofinal.ayuntamientomongo.services;

import com.proyectofinal.ayuntamientomongo.models.AgregadoMongo;
import com.proyectofinal.ayuntamientomongo.repositories.AgregadoMongoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgregadoMongoService {

	@Autowired
	AgregadoMongoRepository amr;

	public AgregadoMongo findLastAgregadoMongo() {
		return this.amr.findFirstByOrderByTimeStampDesc();
	}

	public AgregadoMongo create(AgregadoMongo agregadoMongo) {
		try {
			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			String formattedString = date.format(formatter);
			agregadoMongo.setTimeStamp(formattedString);
			AgregadoMongo u = this.amr.save(agregadoMongo);
			return u;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}

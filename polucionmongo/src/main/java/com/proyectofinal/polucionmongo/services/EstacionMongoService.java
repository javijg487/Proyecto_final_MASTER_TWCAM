package com.proyectofinal.polucionmongo.services;

import com.proyectofinal.polucionmongo.models.EstacionMongo;
import com.proyectofinal.polucionmongo.repositories.EstacionMongoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstacionMongoService {

	@Autowired
	EstacionMongoRepository emr;

	public List<EstacionMongo> findAll() {
		return this.emr.findAll();
	}

	public EstacionMongo create(EstacionMongo estacionMongo, Integer id) {
		try {
			estacionMongo.setIdentificador(id);
			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			String formattedString = date.format(formatter);
			estacionMongo.setTimeStamp(formattedString);
			EstacionMongo u = this.emr.save(estacionMongo);
			return u;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public void delete(EstacionMongo EstacionMongo) {
		this.emr.delete(EstacionMongo);
	}

	public List<EstacionMongo> findByIdentificadorAndTimestampBetween(Integer id, String from, String to){
        return this.emr.findByIdentificadorAndTimeStampBetween(id, from, to);
    }

	public EstacionMongo findFirstByIdentificadorOrderByTimestampDesc(Integer id){
		return this.emr.findFirstByIdentificadorOrderByTimeStampDesc(id);
	}
}

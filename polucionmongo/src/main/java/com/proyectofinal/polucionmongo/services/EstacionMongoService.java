package com.proyectofinal.polucionmongo.services;

import com.proyectofinal.polucionmongo.models.EstacionMongo;
import com.proyectofinal.polucionmongo.repositories.EstacionMongoRepository;

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

	// public List<EstacionMongo> findByFirstname(String firstname) {
	// return this.emr.findByFirstname(firstname);
	// }

	// public List<EstacionMongo> findByFirstnameStartingWith(String firstname) {
	// return this.emr.findByFirstnameStartingWith(firstname);
	// }

	// public List<EstacionMongo> findByLastname(String lastname) {
	// return this.emr.findByLastname(lastname);
	// }

	// public List<EstacionMongo> findByLastnameStartingWith(String lastname) {
	// return this.emr.findByLastnameStartingWith(lastname);
	// }

	// public List<EstacionMongo> findByEmail(String email) {
	// return this.emr.findByEmail(email);
	// }

	public EstacionMongo create(EstacionMongo EstacionMongo) {
		try {
			EstacionMongo u = this.emr.save(EstacionMongo);
			return u;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public List<EstacionMongo> findAllById(Integer id) {
		return this.emr.findByIdentificador(id);
	}

	public void delete(EstacionMongo EstacionMongo) {
		this.emr.delete(EstacionMongo);
	}

	public List<EstacionMongo> findByIdentificadorAndTimestampBetween(Integer id, String from, String to){
        return this.emr.findByIdentificadorAndTimeStampBetween(id, from, to);
    }
}

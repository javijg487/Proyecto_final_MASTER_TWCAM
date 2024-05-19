package com.proyectofinal.polucionmongo.controllers;

import com.proyectofinal.polucionmongo.models.EstacionMongo;
import com.proyectofinal.polucionmongo.repositories.EstacionMongoRepository;
import com.proyectofinal.polucionmongo.services.EstacionMongoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estacion")
public class EstacionMongoController {

	@Autowired
	EstacionMongoService ems;

	@GetMapping("/status")
	public ResponseEntity<List<EstacionMongo>> getAll() {
		List<EstacionMongo> users = new ArrayList<EstacionMongo>();
			users = ems.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// @GetMapping("/status")
	// public ResponseEntity<List<EstacionMongo>> getAll(@RequestParam(value="firstname", required = false) Optional<String> first, 
	// 		@RequestParam(value="lastname", required = false) Optional<String> last, 
	// 		@RequestParam(value="start", required = false) Optional<Boolean> start) {
	// 	List<EstacionMongo> users = new ArrayList<EstacionMongo>();
	// 	if(first.isPresent()) {
	// 		if(start.isPresent()) {
	// 			users = this.ems.findByFirstnameStartingWith(first.get());
	// 		}
	// 		else {
	// 			users = this.ems.findByFirstname(first.get());
	// 		}
	// 	}
	// 	else if(last.isPresent()) {
	// 		if(start.isPresent()) {
	// 			users = this.ems.findByLastnameStartingWith(last.get());
	// 		}
	// 		else {
	// 			users = this.ems.findByLastname(last.get());
	// 		}
	// 	}
	// 	else {
	// 		users = ems.findAll();
	// 	}
		
	// 	return new ResponseEntity<>(users, HttpStatus.OK);
	// }

	// @GetMapping("/{id}/status")
	// public ResponseEntity<?> getById(@PathVariable Integer id) {
	// 	EstacionMongo em = ems.findById(id);
	// 	if (em == null) {
	// 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 	}

	// 	return new ResponseEntity<>(em, HttpStatus.OK);
	// }

	// @PostMapping("/{id}")
	// public ResponseEntity<?> create(@RequestBody EstacionMongo EstacionMongo) throws IOException {
	// 	EstacionMongo em = ems.create(EstacionMongo);
	// 	if (em == null) {
	// 		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	// 	}

	// 	return new ResponseEntity<>(em, HttpStatus.CREATED);
	// }

	// @DeleteMapping("/{id}")
	// public ResponseEntity<?> delete(@PathVariable Integer id) {
	// 	EstacionMongo em = ems.findById(id);
	// 	if (em == null) {
	// 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 	}
	// 	ems.delete(em);
	// 	return new ResponseEntity<>(em, HttpStatus.OK);
	// }
}

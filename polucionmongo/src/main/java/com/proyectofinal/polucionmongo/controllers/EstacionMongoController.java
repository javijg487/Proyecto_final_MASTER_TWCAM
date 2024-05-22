package com.proyectofinal.polucionmongo.controllers;

import com.proyectofinal.polucionmongo.models.EstacionMongo;
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
		List<EstacionMongo> estaciones = new ArrayList<EstacionMongo>();
		estaciones = ems.findAll();
		return new ResponseEntity<>(estaciones, HttpStatus.OK);
	}

	@GetMapping("/{id}/status")
	public ResponseEntity<?> getById(@PathVariable Integer id,
			@RequestParam(value = "from", required = false) Optional<String> from,
			@RequestParam(value = "to", required = false) Optional<String> to) {

		if (from.isPresent() && to.isPresent()) {
			List<EstacionMongo> em = ems.findByIdentificadorAndTimestampBetween(id, from.get(), to.get());
			if (em.isEmpty()) {
				System.out.println("No se encontraron registros");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(em, HttpStatus.OK);
		} else {
			EstacionMongo em = ems.findFirstByIdentificadorOrderByTimestampDesc(id);

			if (em == null) {
				System.out.println("No se encontraron registros");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(em, HttpStatus.OK);
		}
	}

	@PostMapping("/{id}")
	public ResponseEntity<?> create(@RequestBody EstacionMongo EstacionMongo) throws IOException {
		EstacionMongo em = ems.create(EstacionMongo);
		if (em == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(em, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		List<EstacionMongo> em = ems.findAllById(id);
		if (em.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		for (EstacionMongo estacionMongo : em) {
			ems.delete(estacionMongo);
		}
		return new ResponseEntity<>(em, HttpStatus.OK);
	}
}

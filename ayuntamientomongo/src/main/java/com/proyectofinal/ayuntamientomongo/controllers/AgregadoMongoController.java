package com.proyectofinal.ayuntamientomongo.controllers;

import com.proyectofinal.ayuntamientomongo.models.AgregadoMongo;
import com.proyectofinal.ayuntamientomongo.services.AgregadoMongoService;

import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aggregatedData")
public class AgregadoMongoController {

	@Autowired
	AgregadoMongoService ams;

	@GetMapping("")
	@Operation(summary = "Obtener datos agregados", description = "Obtiene los datos agregados más recientes de la base de datos MongoDB")
	public ResponseEntity<AgregadoMongo> getLastAggregatedData() {
		AgregadoMongo agregado = new AgregadoMongo();
		agregado = ams.findLastAgregadoMongo();
		if (agregado == null) {
			System.out.println("No se encontraron registros");
			return new ResponseEntity<>(new AgregadoMongo(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agregado, HttpStatus.OK);
	}

	@PostMapping("")
	@Operation(summary = "Agregar datos agregados", description = "Agregar datos agregados en la base de datos MongoDB")
	public ResponseEntity<?> create(@RequestBody AgregadoMongo agregadoMongo) throws IOException {
		AgregadoMongo am = ams.create(agregadoMongo);
		if (am == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(am, HttpStatus.CREATED);
	}

}

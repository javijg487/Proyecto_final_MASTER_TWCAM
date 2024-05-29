package com.proyectofinal.autenticacion.endpoints;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.proyectofinal.autenticacion.services.JwtService;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

	@Autowired
    private JwtService jwtService;

	@GetMapping("/createAparcamiento")
	public ResponseEntity<String> createAparcamiento() {
		String token = jwtService.generateAccessToken("aparcamiento", List.of("APARCAMIENTO"));
		if (token != null) {
			return new ResponseEntity<>(token, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/createEstacion")
	public ResponseEntity<String> createEstacion() {
		String token = jwtService.generateAccessToken("estacion", List.of("ESTACION"));
		if (token != null) {
			return new ResponseEntity<>(token, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}


}

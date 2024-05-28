package com.proyectofinal.autenticacion.endpoints;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.autenticacion.config.WebSecurityConfig;
import com.proyectofinal.autenticacion.models.AuthenticatedUser;
import com.proyectofinal.autenticacion.services.JwtService;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
	private final static Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
	@Autowired
    private JwtService jwtService;

	@GetMapping("/myroles")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> getMyRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.error("ha entrado en getMyRoles");
		if (authentication != null) {
			LOGGER.error("ha entrado en getMyRoles y authentication no es null");
			Object userDetails = authentication.getPrincipal();
			LOGGER.error("UserDETAIL " + userDetails.toString());
			return new ResponseEntity<>(userDetails.toString(), HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

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

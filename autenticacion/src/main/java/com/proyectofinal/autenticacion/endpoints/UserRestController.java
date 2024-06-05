package com.proyectofinal.autenticacion.endpoints;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.autenticacion.models.AuthenticatedUser;
import com.proyectofinal.autenticacion.services.JwtService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

	@Autowired
	private JwtService jwtService;

	
	@GetMapping("/createAparcamiento")
	@Operation(summary="Crear Aparcamiento", description="Genera el token de acceso para el rol de APARCAMIENTO")
	public ResponseEntity<String> createAparcamiento() {
		String token = jwtService.generateAccessToken("aparcamiento", List.of("ROLE_APARCAMIENTO"));
		if (token != null) {
			return new ResponseEntity<>(token, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/createEstacion")
	@Operation(summary="Crear Estación", description="Genera el token de acceso para el rol de ESTACION")
	public ResponseEntity<String> createEstacion() {
		String token = jwtService.generateAccessToken("estacion", List.of("ROLE_ESTACION"));
		if (token != null) {
			return new ResponseEntity<>(token, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/authenticate")
	@Operation(summary="Autenticar", description="Autentica al usuario y devuelve el token de acceso")
	public ResponseEntity<AuthenticatedUser> authenticate(@RequestHeader("Authorization") String authHeader) {

		AuthenticatedUser authenticatedUser = new AuthenticatedUser();

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = jwtService.getTokenFromHeader(authHeader);
			String username = jwtService.getUsernameFromToken(token);

			String[] roles = jwtService.getRolesFromToken(token);
			System.out.println("Roles: " + Arrays.toString(roles));
			System.out.println("Username: " + username);

			authenticatedUser.setUsername(username);
			authenticatedUser.setRoles(Arrays.asList(roles));

			System.out.println("authenticatedUser: " + authenticatedUser);
			System.out.println("authenticatedUser.getUsername(): " + authenticatedUser.getUsername() + " authenticatedUser.getRoles(): " + authenticatedUser.getRoles());
			// UsernamePasswordAuthenticationToken authenticationToken = new
			// UsernamePasswordAuthenticationToken(
			// username, null, authorities);
			// SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
	}

}

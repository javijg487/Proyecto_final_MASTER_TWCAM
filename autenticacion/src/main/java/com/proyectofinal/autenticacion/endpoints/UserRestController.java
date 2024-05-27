package com.proyectofinal.autenticacion.endpoints;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
	private final static Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

	@GetMapping("authenticated")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AuthenticatedUser> getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object userDetails = authentication.getPrincipal();
			if (userDetails != null && userDetails instanceof UserDetails) {
				UserDetails secUser = (UserDetails) userDetails;
				String username = secUser.getUsername();

				List<String> roles = secUser.getAuthorities()
						.stream()
						.map(authority -> authority.getAuthority())
						.collect(Collectors.toList());
				AuthenticatedUser authenticatedUser = new AuthenticatedUser(username, roles);
				return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
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
}

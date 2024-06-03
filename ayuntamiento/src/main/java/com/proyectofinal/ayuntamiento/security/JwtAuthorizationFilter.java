package com.proyectofinal.ayuntamiento.security;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectofinal.ayuntamiento.models.AuthenticatedUser;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private String autenticacionUrl;

	private RestTemplate restTemplate;

	public JwtAuthorizationFilter(RestTemplate restTemplate, String autenticacionUrl) {
		this.restTemplate = restTemplate;
		this.autenticacionUrl = autenticacionUrl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		System.out.println("authHeader: " + authHeader);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			ResponseEntity<AuthenticatedUser> response_rest;
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization", authHeader);

				HttpEntity<AuthenticatedUser> entity = new HttpEntity<>(headers);
				System.out.println("autenticacionUrl: " + autenticacionUrl );
				response_rest = restTemplate.exchange(autenticacionUrl+ "/api/v1/authenticate", HttpMethod.GET,
						entity,
						AuthenticatedUser.class);
				System.out.println("response_rest: " + response_rest.getStatusCode());
				AuthenticatedUser authenticatedUser = response_rest.getBody();
				String username = authenticatedUser.getUsername();
				Collection<SimpleGrantedAuthority> authorities = authenticatedUser.getRoles().stream()
						.map(SimpleGrantedAuthority::new).toList();
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(403);
				System.out.println(403);
				Map<String, String> error = new HashMap<>();
				error.put("error_msg", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

}

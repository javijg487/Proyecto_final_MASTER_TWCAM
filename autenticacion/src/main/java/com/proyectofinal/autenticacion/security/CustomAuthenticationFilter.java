package com.proyectofinal.autenticacion.security;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proyectofinal.autenticacion.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	private JwtService jwtService;
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) { 	
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(request.getParameter("username"), 
																								request.getParameter("password"));
		return this.authenticationManager.authenticate(authtoken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		User user = (User)auth.getPrincipal();
		
		String access_token = jwtService.generateAccessToken(user.getUsername(), 
															 user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

		// response.setHeader("access_token", access_token);
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"access_token\": \"" + access_token + "\" }");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
}

package com.proyectofinal.autenticacion.models;

import java.util.ArrayList;
import java.util.List;

public class AuthenticatedUser {

	private String username;
	private List<String> roles = new ArrayList<>();
	
	public AuthenticatedUser(String username, List<String> roles) {
		this.username = username;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public List<String> getRoles() {
		return roles;
	}

}

package com.proyectofinal.polucion.models;

import java.util.ArrayList;
import java.util.List;

public class AuthenticatedUser {

	private String username;
	private List<String> roles = new ArrayList<>();

	public AuthenticatedUser() {
	}

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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}

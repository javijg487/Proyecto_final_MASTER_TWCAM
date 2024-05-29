package com.proyectofinal.autenticacion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proyectofinal.autenticacion.security.CustomAuthenticationFilter;
import com.proyectofinal.autenticacion.security.CustomAuthorizationFilter;
import com.proyectofinal.autenticacion.security.InMemoryUserDetailsService;
import com.proyectofinal.autenticacion.services.JwtService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private  InMemoryUserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager(),
				jwtService);
		authenticationFilter.setFilterProcessesUrl("/api/v1/login");

		CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter(jwtService);

        http.csrf(csrf -> csrf.disable())
                .formLogin(login -> login.disable())
                .logout(logout -> logout.disable())
				
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/login").permitAll()
						.requestMatchers("/api/v1/createAparcamiento").hasRole("ADMIN")
						.requestMatchers("/api/v1/createEstacion").hasRole("ADMIN"))
                .addFilter(authenticationFilter)
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}

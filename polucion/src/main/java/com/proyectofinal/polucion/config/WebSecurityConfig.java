package com.proyectofinal.polucion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proyectofinal.polucion.security.JwtAuthorizationFilter;
import com.proyectofinal.polucion.services.JwtService;

@Configuration
public class WebSecurityConfig {
    @Autowired
    private JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(jwtService);

       http.csrf(csrf -> csrf.disable())
                .formLogin(login -> login.disable())
                .logout(logout -> logout.disable())
                .authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.POST, "/api/v1/estacion").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/estacion/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/estacion/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "api/v1/estacion/{id}").hasRole("ESTACION")
                .requestMatchers(HttpMethod.GET, "api/v1/estaciones", "api/v1/estacion/{id}/status").permitAll())
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

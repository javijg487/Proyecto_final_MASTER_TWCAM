package com.proyectofinal.ayuntamiento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proyectofinal.ayuntamiento.security.JwtAuthorizationFilter;
import com.proyectofinal.ayuntamiento.services.JwtService;

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
                        .requestMatchers(
                                "api/v1/aparcamientoCercano",
                                "/api/v1/aggregatedData")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/aggregateData").hasAnyRole( "SERVICIO", "ADMIN")
                        .requestMatchers(
                                "/api/v1/aparcamiento",
                                "/api/v1/aparcamiento/*",
                                "/api/v1/estacion",
                                "/api/v1/estacion/*")
                        .hasRole("ADMIN"))

                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

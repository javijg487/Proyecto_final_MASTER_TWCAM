package com.proyectofinal.ayuntamiento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.proyectofinal.ayuntamiento.security.JwtAuthorizationFilter;

@Configuration
public class WebSecurityConfig {

        @Autowired
        private RestTemplate restTemplate;

        @Value("${autenticacion.url}")
        private String autenticacionUrl;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(restTemplate, autenticacionUrl);

                http.csrf(csrf -> csrf.disable())
                                .formLogin(login -> login.disable())
                                .logout(logout -> logout.disable())
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers(
                                                                "api/v1/aparcamientoCercano",
                                                                "/api/v1/aggregatedData")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/v1/aggregateData")
                                                .hasAnyRole("SERVICIO", "ADMIN")
                                                .requestMatchers(
                                                                "/api/v1/aparcamiento",
                                                                "/api/v1/aparcamiento/*",
                                                                "/api/v1/estacion",
                                                                "/api/v1/estacion/*")
                                                .hasRole("ADMIN")
                                                .requestMatchers("api/v1/api-spec","api/v1/api-gui.html").permitAll())

                                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}

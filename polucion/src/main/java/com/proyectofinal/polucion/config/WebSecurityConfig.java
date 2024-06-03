package com.proyectofinal.polucion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.proyectofinal.polucion.security.JwtAuthorizationFilter;

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
                        .requestMatchers(HttpMethod.POST, "/api/v1/estacion").hasRole("ADMIN")
                        .requestMatchers("api/v1/api-spec","api/v1/api-gui.html").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/estacion/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/estacion/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "api/v1/estacion/{id}").hasRole("ESTACION")
                        .requestMatchers(HttpMethod.GET, "api/v1/estaciones", "api/v1/estacion/{id}/status")
                        .permitAll())
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

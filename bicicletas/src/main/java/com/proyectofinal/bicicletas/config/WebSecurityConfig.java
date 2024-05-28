package com.proyectofinal.bicicletas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proyectofinal.bicicletas.security.JwtAuthorizationFilter;
import com.proyectofinal.bicicletas.services.JwtService;

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
                .requestMatchers(HttpMethod.POST, "/api/v1/aparcamiento").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/aparcamiento/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/aparcamiento/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "api/v1/aparcamiento/{id}").hasRole("APARCAMIENTO")
                .requestMatchers(HttpMethod.GET, "api/v1/aparcamiento/{id}/status").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/aparcamientos").permitAll()
                .requestMatchers(HttpMethod.GET, "api/v1/aparcamiento/top10").permitAll()) 
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

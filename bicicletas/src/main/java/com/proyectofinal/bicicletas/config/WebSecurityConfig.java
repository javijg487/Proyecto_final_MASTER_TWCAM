package com.proyectofinal.bicicletas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.proyectofinal.bicicletas.security.JwtAuthorizationFilter;

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
                .requestMatchers("api/v1/api-spec").permitAll()
						.requestMatchers("/api/v1/api-gui.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/aparcamiento").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/aparcamiento/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/aparcamiento/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/aparcamiento/{id}").hasRole("APARCAMIENTO")
                .requestMatchers(HttpMethod.GET, "/api/v1/aparcamiento/{id}/status", "/api/v1/aparcamiento/top10", "/api/v1/aparcamientos").permitAll())
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

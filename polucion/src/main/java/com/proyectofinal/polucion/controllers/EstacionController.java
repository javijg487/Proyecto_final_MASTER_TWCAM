package com.proyectofinal.polucion.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.proyectofinal.polucion.models.EstacionDTO;
import com.proyectofinal.polucion.models.EstacionMongoDTO;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("api/v1")
public class EstacionController {
   
    @Autowired
    private RestTemplate restTemplate;

    @Value("${estacion.SQL.url}")
    private String estacionSQLUrl;

    @Value("${estacion.Mongo.url}")
    private String estacionMongoUrl;

    @Value("${autenticacion.url}")
    private String autenticacionUrl;

    // Parte de SQL
    @GetMapping("/estaciones")
    @Operation(summary = "Obtener estaciones", description = "Obtiene todas las estaciones de la base de datos MySQL")
    public ResponseEntity<List<EstacionDTO>> findEstaciones() {
        ResponseEntity<EstacionDTO[]> response;
        List<EstacionDTO> estaciones = new ArrayList<EstacionDTO>();
        try {
            response = restTemplate.getForEntity(estacionSQLUrl + "/estaciones", EstacionDTO[].class);
        } catch (ResourceAccessException e) {

            return new ResponseEntity<>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            estaciones = Arrays.asList(response.getBody());
            return new ResponseEntity<List<EstacionDTO>>(estaciones, HttpStatus.OK);
        }
        return new ResponseEntity<List<EstacionDTO>>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping("/estacion")
    @Operation(summary = "Crear estación", description = "Crea una estación en la base de datos MySQL")
    public ResponseEntity<?> create(@RequestBody EstacionDTO Estacion,@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        ResponseEntity<EstacionDTO> response;
        ResponseEntity<String> responseToken;

        try {
            response = restTemplate.postForEntity(estacionSQLUrl + "/estacion", Estacion, EstacionDTO.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<EstacionDTO>(new EstacionDTO(), HttpStatus.SERVICE_UNAVAILABLE);
        }

       if (response.getStatusCode() == HttpStatus.CREATED) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization",  authorizationHeader);
                HttpEntity<String> request = new HttpEntity<>(headers);
                responseToken = restTemplate.exchange(
                        autenticacionUrl + "/api/v1/createEstacion", HttpMethod.GET, request,  String.class );

                if (responseToken.getStatusCode() == HttpStatus.OK) {
                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.set("access_token_Estacion", responseToken.getBody());

                    return new ResponseEntity<>(response.getBody(), responseHeaders, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(new EstacionDTO(), HttpStatus.UNAUTHORIZED);
                }
            } catch (ResourceAccessException e) {
                return new ResponseEntity<>(new EstacionDTO(), HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
        return new ResponseEntity<EstacionDTO>(new EstacionDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping("/estacion/{id}")
    @Operation(summary = "Borrar estación", description = "Borra una estación de la base de datos MySQL")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            restTemplate.delete(estacionSQLUrl + "/estacion/" + id);
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<String>("Not available", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/estacion/{id}")
    @Operation(summary = "Editar estación", description = "Edita una estación de la base de datos MySQL")
    public ResponseEntity<?> editEstacion(@PathVariable Integer id,
            @RequestBody EstacionDTO estacion) {
        try {
            restTemplate.put(estacionSQLUrl + "/estacion/" + id, estacion);
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    // Parte de Mongo
    @GetMapping("/estacion/{id}/status")
    @Operation(summary = "Obtener datos de una estacion por id y periodo de tiempo", description = "Obtiene datos de una estacion por su id y periodo de tiempo. Si no se pasa un periodo de tiempo obtiene los datos más recientes")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id,
            @RequestParam(value = "from", required = false) Optional<String> from,
            @RequestParam(value = "to", required = false) Optional<String> to) {

        ResponseEntity<EstacionMongoDTO[]> response;
        List<EstacionMongoDTO> estaciones = new ArrayList<EstacionMongoDTO>();
        // Para facilitar la construccion de la URL
        String URL = UriComponentsBuilder.fromHttpUrl(estacionMongoUrl + "/estacion/" + id + "/status")
                .queryParamIfPresent("from", from)
                .queryParamIfPresent("to", to).toUriString();
        try {
            response = restTemplate.getForEntity(URL, EstacionMongoDTO[].class);
            estaciones = Arrays.asList(response.getBody());
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(estaciones, HttpStatus.OK);
    }

    @PostMapping("/estacion/{id}")
    @Operation(summary = "Agregar datos de estacion", description = "Agregar datos de una estacion en la base de datos MongoDB")
    public ResponseEntity<?> createmongo(@PathVariable Integer id, @RequestBody EstacionMongoDTO EstacionMongo)
            throws IOException {
        ResponseEntity<EstacionMongoDTO> response;
        boolean idEncontrado = false;
        List<EstacionDTO> estaciones = findEstaciones().getBody();

        if (!estaciones.isEmpty()) {
            for (EstacionDTO estacion : estaciones) {
                if (estacion.getId().equals(id)) {
                    idEncontrado = true;
                    break;
                }
            }
        }

        if (idEncontrado) {
            try {
                response = restTemplate.postForEntity(estacionMongoUrl + "/estacion/" + id, EstacionMongo,
                        EstacionMongoDTO.class);
            } catch (ResourceAccessException e) {
                return new ResponseEntity<EstacionMongoDTO>(new EstacionMongoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
            }
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return new ResponseEntity<EstacionMongoDTO>(response.getBody(), HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<EstacionMongoDTO>(new EstacionMongoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}

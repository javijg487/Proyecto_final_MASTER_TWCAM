package com.proyectofinal.polucion.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

import com.proyectofinal.polucion.models.EstacionDTO;
import com.proyectofinal.polucion.models.EstacionMongoDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("")
public class EstacionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(EstacionController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${estacion.SQL.url}")
    private String estacionSQLUrl;

    @Value("${estacion.Mongo.url}")
    private String estacionMongoUrl;

    // Parte de SQL
    @GetMapping("/estaciones")
    public ResponseEntity<List<EstacionDTO>> findEstaciones() {
        ResponseEntity<EstacionDTO[]> response;
        LOGGER.debug("View all estaciones");
        List<EstacionDTO> estaciones = new ArrayList<EstacionDTO>();
        try {
            response = restTemplate.getForEntity(estacionSQLUrl + "/estaciones", EstacionDTO[].class);
        } catch (ResourceAccessException e) {
            LOGGER.error("Error al obtener las estaciones de la base de datos SQL");
            return new ResponseEntity<>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            estaciones = Arrays.asList(response.getBody());
            return new ResponseEntity<List<EstacionDTO>>(estaciones, HttpStatus.OK);
        }
        return new ResponseEntity<List<EstacionDTO>>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping("/estacion")
    public ResponseEntity<?> create(@RequestBody EstacionDTO Estacion) throws IOException {
        ResponseEntity<EstacionDTO> response;

        try {
            response = restTemplate.postForEntity(estacionSQLUrl + "/estacion", Estacion, EstacionDTO.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<EstacionDTO>(new EstacionDTO(), HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.CREATED) {
            LOGGER.error("Body: " + response.getBody() + "status" + response.getStatusCode().toString());
            return new ResponseEntity<EstacionDTO>(response.getBody(), HttpStatus.CREATED);
        }
        return new ResponseEntity<EstacionDTO>(new EstacionDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping("/estacion/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            restTemplate.delete(estacionSQLUrl + "/estacion/" + id);
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<String>("Not available", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("estacion/{id}")
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

    @GetMapping("estacion/status")
    public ResponseEntity<List<EstacionMongoDTO>> getAll() {
        ResponseEntity<EstacionMongoDTO[]> response;
        List<EstacionMongoDTO> estaciones = new ArrayList<EstacionMongoDTO>();
        try {
            response = restTemplate.getForEntity(estacionMongoUrl + "/status", EstacionMongoDTO[].class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            estaciones = Arrays.asList(response.getBody());
            return new ResponseEntity<List<EstacionMongoDTO>>(estaciones, HttpStatus.OK);
        }
        return new ResponseEntity<List<EstacionMongoDTO>>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("estacion/{id}/status")
    public ResponseEntity<?> getById(@PathVariable Integer id,
            @RequestParam(value = "from", required = false) Optional<String> from,
            @RequestParam(value = "to", required = false) Optional<String> to) {

        ResponseEntity<EstacionMongoDTO[]> response;
        List<EstacionMongoDTO> estaciones = new ArrayList<EstacionMongoDTO>();
        // Para facilitar la construccion de la URL
        String URL = UriComponentsBuilder.fromHttpUrl(estacionMongoUrl + "/" + id + "/status")
                .queryParam("from", from.orElse(null))
                .queryParam("to", to.orElse(null)).toUriString();
        try {
            response = restTemplate.getForEntity(URL, EstacionMongoDTO[].class);
            estaciones = Arrays.asList(response.getBody());
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(estaciones, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(estaciones, HttpStatus.OK);
    }

    @PostMapping("estacion/{id}")
    public ResponseEntity<?> createmongo(@PathVariable Integer id, @RequestBody EstacionMongoDTO EstacionMongo)
            throws IOException {
        ResponseEntity<EstacionMongoDTO> response;
        boolean idEncontrado = false;
        List<EstacionDTO> estaciones = findEstaciones().getBody();

        if (estaciones != null) {
            for (EstacionDTO estacion : estaciones) {
                if (estacion.getId().equals(id)) {
                    idEncontrado = true;
                    break;
                }
            }
        }

        if (idEncontrado) {
            try {
                response = restTemplate.postForEntity(estacionMongoUrl + "/" + id, EstacionMongo,
                        EstacionMongoDTO.class);
            } catch (ResourceAccessException e) {
                return new ResponseEntity<EstacionMongoDTO>(new EstacionMongoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
            }
            if (response.getStatusCode() == HttpStatus.CREATED) {
                LOGGER.error("Body: " + response.getBody() + "status" + response.getStatusCode().toString());
                return new ResponseEntity<EstacionMongoDTO>(response.getBody(), HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<EstacionMongoDTO>(new EstacionMongoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

     @DeleteMapping("estacion/{id}/status")
     public ResponseEntity<?> deletemongo(@PathVariable Integer id) {
         try {
             restTemplate.delete(estacionMongoUrl + "/" + id);
             return new ResponseEntity<Integer>(id, HttpStatus.OK);
         } catch (ResourceAccessException e) {
             return new ResponseEntity<String>("Not available", HttpStatus.SERVICE_UNAVAILABLE);
         }
     }
}

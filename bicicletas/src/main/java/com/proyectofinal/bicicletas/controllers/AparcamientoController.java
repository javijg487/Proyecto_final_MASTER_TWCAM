package com.proyectofinal.bicicletas.controllers;

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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;


import java.util.HashSet;

import com.proyectofinal.bicicletas.models.AparcamientoDTO;
import com.proyectofinal.bicicletas.models.AparcamientoMongoDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("api/v1")
public class AparcamientoController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bicicletas.SQL.url}")
    private String aparcamientoSQLUrl;

    @Value("${bicicletas.Mongo.url}")
    private String aparcamientoMongoUrl;

    @Value("${autenticacion.url}")
    private String autenticacionUrl;

    // Parte de SQL
    @GetMapping("/aparcamientos")
    public ResponseEntity<List<AparcamientoDTO>> findAparcamientos() {
        ResponseEntity<AparcamientoDTO[]> response;
        List<AparcamientoDTO> aparcamientos = new ArrayList<AparcamientoDTO>();
        try {
            response = restTemplate.getForEntity(aparcamientoSQLUrl + "/aparcamientos", AparcamientoDTO[].class);
        } catch (ResourceAccessException e) {

            return new ResponseEntity<>(aparcamientos, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            aparcamientos = Arrays.asList(response.getBody());
            return new ResponseEntity<List<AparcamientoDTO>>(aparcamientos, HttpStatus.OK);
        }
        return new ResponseEntity<List<AparcamientoDTO>>(aparcamientos, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping("/aparcamiento")
    public ResponseEntity<?> create(@RequestBody AparcamientoDTO Aparcamiento,
            @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        ResponseEntity<AparcamientoDTO> response;
        ResponseEntity<String> responseToken;

        try {
            response = restTemplate.postForEntity(aparcamientoSQLUrl + "/aparcamiento", Aparcamiento,
                    AparcamientoDTO.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new AparcamientoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
        }

        if (response.getStatusCode() == HttpStatus.CREATED) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization",  authorizationHeader);
                HttpEntity<String> request = new HttpEntity<>(headers);
                responseToken = restTemplate.exchange(
                        autenticacionUrl + "/api/v1/createAparcamiento", HttpMethod.GET, request,  String.class );

                if (responseToken.getStatusCode() == HttpStatus.OK) {
                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.set("access_token_Aparcamiento", responseToken.getBody());

                    return new ResponseEntity<>(response.getBody(), responseHeaders, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(new AparcamientoDTO(), HttpStatus.UNAUTHORIZED);
                }
            } catch (ResourceAccessException e) {
                return new ResponseEntity<>(new AparcamientoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return new ResponseEntity<>(new AparcamientoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping("/aparcamiento/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            restTemplate.delete(aparcamientoSQLUrl + "/aparcamiento/" + id);
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<String>("Not available", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/aparcamiento/{id}")
    public ResponseEntity<?> editAparcamiento(@PathVariable("id") Integer id,
            @RequestBody AparcamientoDTO aparcamiento) {
        try {
            restTemplate.put(aparcamientoSQLUrl + "/aparcamiento/" + id, aparcamiento);
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    // Parte de Mongo
    @GetMapping("/aparcamiento/{id}/status")
    public ResponseEntity<?> getAparcamientoById(@PathVariable Integer id,
            @RequestParam("from") Optional<String> from, @RequestParam("to") Optional<String> to) {

        ResponseEntity<AparcamientoMongoDTO[]> response;
        List<AparcamientoMongoDTO> aparcamientos = new ArrayList<AparcamientoMongoDTO>();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(aparcamientoMongoUrl + "/aparcamiento/" + id + "/status");
            if (from.isPresent() && to.isPresent()) {
                builder.queryParam("from", from.get());
                builder.queryParam("to", to.get());
            }
            response = restTemplate.getForEntity(builder.toUriString(), AparcamientoMongoDTO[].class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(aparcamientos, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            aparcamientos = Arrays.asList(response.getBody());
            return new ResponseEntity<List<AparcamientoMongoDTO>>(aparcamientos, HttpStatus.OK);
        }
        return new ResponseEntity<List<AparcamientoMongoDTO>>(aparcamientos, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping("/aparcamiento/{id}")
    public ResponseEntity<?> createmongo(@PathVariable Integer id, @RequestBody AparcamientoMongoDTO aparcamientoMongo)
            throws IOException {
        ResponseEntity<AparcamientoMongoDTO> response;
        boolean idEncontrado = false;
        List<AparcamientoDTO> aparcamientos = findAparcamientos().getBody();

        if (!aparcamientos.isEmpty()) {
            for (AparcamientoDTO aparcamiento : aparcamientos) {
                if (aparcamiento.getId().equals(id)) {
                    idEncontrado = true;
                    break;
                }
            }
        }

        if (idEncontrado) {
            try {
                response = restTemplate.postForEntity(aparcamientoMongoUrl + "/aparcamiento/" + id, aparcamientoMongo,
                        AparcamientoMongoDTO.class);
            } catch (ResourceAccessException e) {
                return new ResponseEntity<AparcamientoMongoDTO>(new AparcamientoMongoDTO(),
                        HttpStatus.SERVICE_UNAVAILABLE);
            }
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return new ResponseEntity<AparcamientoMongoDTO>(response.getBody(), HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<AparcamientoMongoDTO>(new AparcamientoMongoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    // CAMBIAR USANDO APARCAMIENTO/{id}/STATUS
    @GetMapping("/aparcamiento/top10")
    public ResponseEntity<List<AparcamientoMongoDTO>> getTop10() {
        OffsetDateTime date = OffsetDateTime.now().minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        ResponseEntity<AparcamientoDTO[]> responseSQL;
        ResponseEntity<AparcamientoMongoDTO[]> responseMongo;

        List<AparcamientoDTO> aparcamientosSQL = new ArrayList<AparcamientoDTO>();
        List<AparcamientoMongoDTO> aparcamientoMongo = new ArrayList<AparcamientoMongoDTO>();
        List<AparcamientoMongoDTO> aparcamientosExistentes = new ArrayList<AparcamientoMongoDTO>();

        try{
            responseSQL = restTemplate.getForEntity(aparcamientoSQLUrl + "/aparcamientos", AparcamientoDTO[].class);
        }catch(ResourceAccessException e){
            return new ResponseEntity<List<AparcamientoMongoDTO>>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        if(responseSQL.getStatusCode() == HttpStatus.OK){
            aparcamientosSQL = Arrays.asList(responseSQL.getBody());
            
            for(AparcamientoDTO aparcamiento_SQL : aparcamientosSQL){
                Integer idAparcamiento = aparcamiento_SQL.getId();

                try{
                    responseMongo = restTemplate.getForEntity(aparcamientoMongoUrl + "/aparcamiento/"+ idAparcamiento +"/status", AparcamientoMongoDTO[].class);
                }catch(ResourceAccessException e){
                    return new ResponseEntity<List<AparcamientoMongoDTO>>(HttpStatus.SERVICE_UNAVAILABLE);
                }

                if (responseMongo.getStatusCode() == HttpStatus.OK){
                    aparcamientoMongo = Arrays.asList(responseMongo.getBody());
                    if(aparcamiento_SQL.getId().equals(aparcamientoMongo.get(0).getId()) && OffsetDateTime.parse(aparcamientoMongo.get(0).getTimestamp(), formatter).isAfter(date)){
                        aparcamientosExistentes.add(aparcamientoMongo.get(0));
                    }
                }
            }

            List<AparcamientoMongoDTO> aparcamientosTop10 = new ArrayList<AparcamientoMongoDTO>();
            Set<Integer> aparcamientosInsertados = new HashSet<Integer>();

            for (AparcamientoMongoDTO aparcamiento : aparcamientosExistentes) {
                if (aparcamientosInsertados.add(aparcamiento.getId()) && aparcamientosTop10.size() <= 10) {
                    aparcamientosTop10.add(aparcamiento);
                }
            }
            return new ResponseEntity<List<AparcamientoMongoDTO>>(aparcamientosTop10, HttpStatus.OK);
        }
        return new ResponseEntity<List<AparcamientoMongoDTO>>(HttpStatus.SERVICE_UNAVAILABLE);
    }

}
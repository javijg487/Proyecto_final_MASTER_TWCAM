package com.proyectofinal.ayuntamiento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.proyectofinal.ayuntamiento.models.AgregadoMongoDTO;

@RestController
@RequestMapping("/api/v1")
public class AggregatedDataController {

     @Autowired
    private RestTemplate restTemplate;

    @Value("${estacion.SQL.url}")
    private String estacionSQLUrl;

    @Value("${estacion.Mongo.url}")
    private String estacionMongoUrl;

    @Value("${aggregatedData.url}")
    private String aggregatedDataUrl;


    @GetMapping("/aggregatedData")
    public ResponseEntity<AgregadoMongoDTO> findAggregatedData() {
        ResponseEntity<AgregadoMongoDTO> response;
        AgregadoMongoDTO aggregatedData = new AgregadoMongoDTO();
        try {
            response = restTemplate.getForEntity(aggregatedDataUrl + "/aggregatedData", AgregadoMongoDTO.class);
        }catch(ResourceAccessException e){
            return new ResponseEntity<>(aggregatedData, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if(response.getStatusCode() == HttpStatus.OK){
            aggregatedData = response.getBody();
            return new ResponseEntity<AgregadoMongoDTO>(aggregatedData, HttpStatus.OK);
        }
        return new ResponseEntity<>(aggregatedData, HttpStatus.NOT_FOUND);
    }

}

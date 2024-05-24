package com.proyectofinal.ayuntamiento.controllers;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.proyectofinal.ayuntamiento.models.AparcamientoCercanoDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoMongoDTO;
import com.proyectofinal.ayuntamiento.services.AggregatedDataService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class AggregatedDataController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AggregatedDataController.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AggregatedDataService ads;

    @Value("${aparcamiento.API.url}")
    private String aparcamientoAPIUrl;

    // @Value("${estacion.Mongo.url}")
    // private String estacionMongoUrl;

    @Value("${aggregatedData.url}")
    private String aggregatedDataUrl;

    @GetMapping("/aggregatedData")
    public ResponseEntity<AgregadoMongoDTO> findAggregatedData() {
        ResponseEntity<AgregadoMongoDTO> response;
        AgregadoMongoDTO aggregatedData = new AgregadoMongoDTO();
        try {
            response = restTemplate.getForEntity(aggregatedDataUrl + "/aggregatedData", AgregadoMongoDTO.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(aggregatedData, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            aggregatedData = response.getBody();
            return new ResponseEntity<AgregadoMongoDTO>(aggregatedData, HttpStatus.OK);
        }
        return new ResponseEntity<>(aggregatedData, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/aparcamientoCercano")
    public ResponseEntity<AparcamientoCercanoDTO> getMethodName(
            @RequestParam(value = "lat", required = true) String lat,
            @RequestParam(value = "lon", required = true) String lon) {

        Double distanciaCercano = Double.MAX_VALUE;
        ResponseEntity<AparcamientoDTO[]> response;
        ResponseEntity<AparcamientoMongoDTO[]> responseMongo;
        AparcamientoCercanoDTO aparcamiento = new AparcamientoCercanoDTO();
        try {
            response = restTemplate.getForEntity(aparcamientoAPIUrl + "/api/v1/aparcamientos", AparcamientoDTO[].class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(aparcamiento, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            List<AparcamientoDTO> aparcamientosDTO = Arrays.asList(response.getBody());
            for (AparcamientoDTO aparcamientoSQL : aparcamientosDTO) {
                Integer id = aparcamientoSQL.getId();
                try {
                    responseMongo = restTemplate.getForEntity(
                            aparcamientoAPIUrl + "/api/v1/aparcamiento/" + id + "/status",
                            AparcamientoMongoDTO[].class);
                } catch (ResourceAccessException e) {

                    return new ResponseEntity<>(aparcamiento, HttpStatus.SERVICE_UNAVAILABLE);
                }
                AparcamientoMongoDTO aparcamientoMongo = responseMongo.getBody()[0];

                if (aparcamientoMongo != null && aparcamientoMongo.getFreeParkingSpots() > 0) {
                    Double distancia = ads.calculateDistancia(aparcamientoSQL.getLatitude(),
                            aparcamientoSQL.getLongitude(), Float.parseFloat(lat), Float.parseFloat(lon));
                    if (distanciaCercano > distancia) {
                        distanciaCercano = distancia;
                        aparcamiento = ads.putAparcamientoCercano(aparcamientoSQL, aparcamientoMongo,
                                distancia.floatValue());
                    }

                }

            }
            return new ResponseEntity<AparcamientoCercanoDTO>(aparcamiento, HttpStatus.OK);
        }
        return new ResponseEntity<AparcamientoCercanoDTO>(aparcamiento, HttpStatus.NOT_FOUND);
    }

}

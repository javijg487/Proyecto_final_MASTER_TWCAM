package com.proyectofinal.ayuntamiento.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.proyectofinal.ayuntamiento.models.AggregatedDataDTO;
import com.proyectofinal.ayuntamiento.models.AgregadoMongoDTO;
import com.proyectofinal.ayuntamiento.models.AirQualityDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoCercanoDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoMongoDTO;
import com.proyectofinal.ayuntamiento.models.EstacionDTO;
import com.proyectofinal.ayuntamiento.models.EstacionMongoDTO;
import com.proyectofinal.ayuntamiento.services.AggregatedDataService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class AggregatedDataController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AggregatedDataService ads;

    @Value("${aparcamiento.API.url}")
    private String aparcamientoAPIUrl;

    @Value("${estacion.API.url}")
    private String estacionAPIUrl;

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
    public ResponseEntity<AparcamientoCercanoDTO> getAparcamientoCercano(
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

    @GetMapping("/aggregateData")
    public ResponseEntity<AgregadoMongoDTO> aggregateData() {

        ResponseEntity<AparcamientoDTO[]> response;
        ResponseEntity<AparcamientoMongoDTO[]> responseMongo;
        ResponseEntity<EstacionDTO[]> responseEstacion;
        ResponseEntity<EstacionMongoDTO[]> responseEstacionMongo;
        ResponseEntity<AgregadoMongoDTO> responseAggregatedData;
        AgregadoMongoDTO aggregateDataFinal = new AgregadoMongoDTO();
        List<AggregatedDataDTO> aggregatedDataList = new ArrayList<>();
        
        List<String> fechas = ads.getFecha();

        try {
            response = restTemplate.getForEntity(aparcamientoAPIUrl + "/api/v1/aparcamientos", AparcamientoDTO[].class);
            responseEstacion = restTemplate.getForEntity(estacionAPIUrl + "/api/v1/estaciones",
                    EstacionDTO[].class);
        } catch (ResourceAccessException e) {

            return new ResponseEntity<>(aggregateDataFinal, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.OK && responseEstacion.getStatusCode() == HttpStatus.OK) {
            List<AparcamientoDTO> aparcamientosDTO = Arrays.asList(response.getBody());
            List<EstacionDTO> estacionesDTO = Arrays.asList(responseEstacion.getBody());
            for (AparcamientoDTO aparcamientoSQL : aparcamientosDTO) {
                Double distanciaEstacionCercana = Double.MAX_VALUE;
                Integer idEstacion = 0;
                Integer idAparcamiento = aparcamientoSQL.getId();
                for (EstacionDTO estacionSQL : estacionesDTO) {
                    Double distancia = ads.calculateDistancia(aparcamientoSQL.getLatitude(),
                            aparcamientoSQL.getLongitude(), estacionSQL.getLatitud(), estacionSQL.getLongitud());
                    if (distanciaEstacionCercana > distancia) {
                        distanciaEstacionCercana = distancia;
                        idEstacion = estacionSQL.getId();
                    }
                }
                String urlEstacionMongo = UriComponentsBuilder
                        .fromHttpUrl(estacionAPIUrl + "/api/v1/estacion/" + idEstacion + "/status")
                        .queryParam("from", fechas.get(0))
                        .queryParam("to", fechas.get(1)).toUriString();
                try {
                    responseEstacionMongo = restTemplate.getForEntity(
                            urlEstacionMongo, EstacionMongoDTO[].class);
                } catch (ResourceAccessException e) {
                    return new ResponseEntity<>(aggregateDataFinal, HttpStatus.SERVICE_UNAVAILABLE);
                }

                AirQualityDTO airQuality = new AirQualityDTO();

                if (responseEstacionMongo.getStatusCode() == HttpStatus.OK) {
                    List<EstacionMongoDTO> estacionesMongoDTO = Arrays.asList(responseEstacionMongo.getBody());
                    float sumaNitricOxi = 0;
                    float sumaNitroDiox = 0;
                    float sumaVocs = 0;
                    float sumaPM25 = 0;

                    for (EstacionMongoDTO estacionMongo : estacionesMongoDTO) {
                        sumaNitricOxi +=estacionMongo.getNitricOxides();
                        sumaNitroDiox +=estacionMongo.getNitrogenDioxides();
                        sumaVocs +=estacionMongo.getVOCsNMHC();
                        sumaPM25 +=estacionMongo.getPM25();
                    }

                    airQuality.setNitricOxides(sumaNitricOxi / estacionesMongoDTO.size());
                    airQuality.setNitrogenDioxides(sumaNitroDiox / estacionesMongoDTO.size());
                    airQuality.setVOCsNMHC(sumaVocs / estacionesMongoDTO.size());
                    airQuality.setPM25(sumaPM25 / estacionesMongoDTO.size());
                }

                // Obtiene media de bicis disponibles aparcamiento
                String urlAparcamientoMongo = UriComponentsBuilder
                        .fromHttpUrl(aparcamientoAPIUrl + "/api/v1/aparcamiento/" + idAparcamiento + "/status")
                        .queryParam("from", fechas.get(0))
                        .queryParam("to", fechas.get(1)).toUriString();
                try {
                    responseMongo = restTemplate.getForEntity(
                            urlAparcamientoMongo, AparcamientoMongoDTO[].class);
                } catch (ResourceAccessException e) {
           
                    return new ResponseEntity<>(aggregateDataFinal, HttpStatus.SERVICE_UNAVAILABLE);
                }

                if (responseMongo.getStatusCode() == HttpStatus.OK) {
                    List<AparcamientoMongoDTO> aparcamientosMongoDTO = Arrays.asList(responseMongo.getBody());
                    int suma = 0;

                    for (AparcamientoMongoDTO aparcamientoMongo : aparcamientosMongoDTO) {
                        suma += aparcamientoMongo.getBikesAvailable();
                    }
                    AggregatedDataDTO aggregatedData = new AggregatedDataDTO();
                    aggregatedData.setIdentificador(idAparcamiento);
                    aggregatedData.setAverageBikesAvailable((float) suma / aparcamientosMongoDTO.size());
                    aggregatedData.setAirQuality(airQuality);
                    aggregatedDataList.add(aggregatedData);
                }
            }
            aggregateDataFinal = ads.createAgregadoMongo(aggregateDataFinal, aggregatedDataList);
            try {
                responseAggregatedData = restTemplate.postForEntity(aggregatedDataUrl + "/aggregatedData",
                        aggregateDataFinal,
                        AgregadoMongoDTO.class);
            } catch (ResourceAccessException e) {
                
                return new ResponseEntity<AgregadoMongoDTO>(new AgregadoMongoDTO(), HttpStatus.SERVICE_UNAVAILABLE);
            }
            if (responseAggregatedData.getStatusCode() == HttpStatus.CREATED) {
                return new ResponseEntity<AgregadoMongoDTO>(aggregateDataFinal, HttpStatus.OK);
            } else {
            
                return new ResponseEntity<AgregadoMongoDTO>(aggregateDataFinal, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
        return new ResponseEntity<>(aggregateDataFinal, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/estacion")
    public ResponseEntity<?> createEstacion(@RequestBody EstacionDTO Estacion) throws IOException {
        ResponseEntity<EstacionDTO> response;

        try {
            response = restTemplate.postForEntity(estacionAPIUrl + "api/v1/estacion", Estacion, EstacionDTO.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<EstacionDTO>(new EstacionDTO(), HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (response.getStatusCode() == HttpStatus.CREATED) {

            return new ResponseEntity<EstacionDTO>(response.getBody(), HttpStatus.CREATED);
        }
        return new ResponseEntity<EstacionDTO>(new EstacionDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping("/estacion/{id}")
    public ResponseEntity<?> deleteEstacion(@PathVariable Integer id) {
        try {
            restTemplate.delete(estacionAPIUrl + "api/v1/estacion/" + id);
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<String>("Not available", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/estacion/{id}")
    public ResponseEntity<?> editEstacion(@PathVariable Integer id, @RequestBody EstacionDTO estacion) {
        try {
            restTemplate.put(estacionAPIUrl + "api/v1/estacion/" + id, estacion);
            return new ResponseEntity<EstacionDTO>(estacion, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<String>("Not available", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @DeleteMapping("/aparcamiento/{id}")
    public ResponseEntity<?> deleteAparcamiento(@PathVariable("id") Integer id) {
        try {
            restTemplate.delete(aparcamientoAPIUrl + "api/v1/aparcamiento/" + id);
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<String>("Not available", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}

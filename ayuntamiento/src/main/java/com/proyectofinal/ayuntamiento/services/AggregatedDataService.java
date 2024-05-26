package com.proyectofinal.ayuntamiento.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectofinal.ayuntamiento.models.AggregatedDataDTO;
import com.proyectofinal.ayuntamiento.models.AgregadoMongoDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoCercanoDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoDTO;
import com.proyectofinal.ayuntamiento.models.AparcamientoMongoDTO;

@Service
public class AggregatedDataService {

    public double calculateDistancia(float lat1, float lon1, float lat2, float lon2) {
        double radioTierra = 6371; // en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return radioTierra * c;

    }

    public AparcamientoCercanoDTO putAparcamientoCercano(AparcamientoDTO aparcamientoDto,
            AparcamientoMongoDTO aparcamientoMongoDTO, Float distancia) {
        AparcamientoCercanoDTO aparcamiento = new AparcamientoCercanoDTO();
        aparcamiento.setId(aparcamientoDto.getId());
        aparcamiento.setBikesCapacity(aparcamientoDto.getBikesCapacity());
        aparcamiento.setBikesAvailable(aparcamientoMongoDTO.getBikesAvailable());
        aparcamiento.setFreeParkingSpots(aparcamientoMongoDTO.getFreeParkingSpots());
        aparcamiento.setDistance(distancia.floatValue());

        return aparcamiento;
    }

    public List<String> getFecha() {
        List<String> fechas = new ArrayList<String>();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime oneMonthAgoDate = currentDate.minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedCurrentDate = currentDate.format(formatter);
        String formattedOneMonthAgoDate = oneMonthAgoDate.format(formatter);

        fechas.add(formattedOneMonthAgoDate);
        fechas.add(formattedCurrentDate);

        return fechas;
    }

    public AgregadoMongoDTO createAgregadoMongo(AgregadoMongoDTO agregadoMongoDTOMongo, List<AggregatedDataDTO> aggregatedData) {
        agregadoMongoDTOMongo.setAggregatedData(aggregatedData);
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedString = date.format(formatter);
        agregadoMongoDTOMongo.setTimeStamp(formattedString);
        return agregadoMongoDTOMongo;
    }

}

package com.proyectofinal.bicicletasmongo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class AparcamientoMongo {
    @Id
    private String _id;

    @JsonProperty("id")
    private Integer identificador;
    private String operation;
    private Integer bikesAvailable;
    private Integer freeParkingSpots;
    private String timestamp;

    public AparcamientoMongo(){}
    
    public AparcamientoMongo(Integer id, String operation, Integer bikesAvailable, Integer freeParkingSpots, String timestamp){
        this.identificador = id;
        this.operation = operation;
        this.bikesAvailable = bikesAvailable;
        this.freeParkingSpots = freeParkingSpots;
        this.timestamp = timestamp;
    }

    public Integer getIdentificador(){
        return identificador;
    }

    public void setIdentificador(Integer id){
        this.identificador = id;
    }

    public String getOperation(){
        return operation;
    }

    public void setOperation(String operation){
        this.operation = operation;
    }

    public Integer getBikesAvailable(){
        return bikesAvailable;
    }

    public void setBikesAvailable(Integer bikesAvailable){
        this.bikesAvailable = bikesAvailable;
    }

    public Integer getFreeParkingSpots(){
        return freeParkingSpots;
    }

    public void setFreeParkingSpots(Integer freeParkingSpots){
        this.freeParkingSpots = freeParkingSpots;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
}


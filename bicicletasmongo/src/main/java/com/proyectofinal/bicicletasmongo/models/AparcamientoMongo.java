package com.proyectofinal.bicicletasmongo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AparcamientoMongo {

    @Id
    private Integer id;
    private String operation;
    private Integer bikesAvailable;
    private Integer freeParkingSpots;
    private String timestamp;

    public AparcamientoMongo(){}
    
    public AparcamientoMongo(Integer id, String operation, Integer bikesAvailable, Integer freeParkingSpots, String timestamp){
        this.id = id;
        this.operation = operation;
        this.bikesAvailable = bikesAvailable;
        this.freeParkingSpots = freeParkingSpots;
        this.timestamp = timestamp;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
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


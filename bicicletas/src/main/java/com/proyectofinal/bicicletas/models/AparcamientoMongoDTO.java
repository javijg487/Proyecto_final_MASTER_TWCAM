package com.proyectofinal.bicicletas.models;

public class AparcamientoMongoDTO {
    
    private Integer identificador;
    private String operation;
    private Integer bikesAvailable;
    private Integer freeParkingSpots;
    private String timestamp;

    public AparcamientoMongoDTO(){}

    public AparcamientoMongoDTO(Integer id, String operation, Integer bikesAvailable, Integer freeParkingSpots, String timestamp){
        this.identificador = id;
        this.operation = operation;
        this.bikesAvailable = bikesAvailable;
        this.freeParkingSpots = freeParkingSpots;
        this.timestamp = timestamp;
    }

    public Integer getId(){
        return identificador;
    }

    public void setId(Integer id){
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

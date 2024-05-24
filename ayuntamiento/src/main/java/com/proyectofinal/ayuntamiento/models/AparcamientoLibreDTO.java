package com.proyectofinal.ayuntamiento.models;

public class AparcamientoLibreDTO {
    private Integer identificador;
    private int bikesCapacity;
    private Integer bikesAvailable;
    private Integer freeParkingSpots;
    private Float latitude;
    private Float longitude;
    
    public AparcamientoLibreDTO(){}

    public AparcamientoLibreDTO(Integer id, int bikesCapacity, Integer bikesAvailable, Integer freeParkingSpots, Float latitude, Float longitude){
        this.identificador = id;
        this.bikesCapacity = bikesCapacity;
        this.bikesAvailable = bikesAvailable;
        this.freeParkingSpots = freeParkingSpots;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdentificador(){
        return identificador;
    }

    public void setIdentificador(Integer id){
        this.identificador = id;
    }

    public int getBikesCapacity(){
        return bikesCapacity;
    }

    public void setBikesCapacity(int bikesCapacity){
        this.bikesCapacity = bikesCapacity;
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

    public Float getLatitude(){
        return latitude;
    }

    public void setLatitude(Float latitude){
        this.latitude = latitude;
    }

    public Float getLongitude(){
        return longitude;
    }

    public void setLongitude(Float longitude){
        this.longitude = longitude;
    }

}

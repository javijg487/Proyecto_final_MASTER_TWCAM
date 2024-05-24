package com.proyectofinal.ayuntamiento.models;

public class AparcamientoCercanoDTO {
    private Integer id;
    private int bikesCapacity;
    private Integer bikesAvailable;
    private Integer freeParkingSpots;
    private Float distance;
    
    public AparcamientoCercanoDTO(){}

    public AparcamientoCercanoDTO(Integer id, int bikesCapacity, Integer bikesAvailable, Integer freeParkingSpots, Float distance){
        this.id = id;
        this.bikesCapacity = bikesCapacity;
        this.bikesAvailable = bikesAvailable;
        this.freeParkingSpots = freeParkingSpots;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
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

    public Float getDistance(){
        return distance;
    }

    public void setDistance(Float distance){
        this.distance = distance;
    }
}

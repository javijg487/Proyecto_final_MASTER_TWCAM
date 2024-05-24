package com.proyectofinal.ayuntamiento.models;

public class AparcamientoDTO {
    
    private Integer id;
    private String direction;
    private int bikesCapacity;
    private Float latitude;
    private Float longitude;

    public AparcamientoDTO() {}

    public AparcamientoDTO(Integer id, String direction, int bikesCapacity, Float latitude, Float longitude) {
        this.id = id;
        this.direction = direction;
        this.bikesCapacity = bikesCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getBikesCapacity() {
        return bikesCapacity;
    }

    public void setBikesCapacity(int bikesCapacity) {
        this.bikesCapacity = bikesCapacity;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}

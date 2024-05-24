package com.proyectofinal.bicicletassql.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
@Table(name = "aparcamiento")
public class Aparcamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "direction")
    private String direction;

    @Column(name = "bikes_capacity")
    private Integer bikesCapacity;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    public Aparcamiento() {
    }

    public Aparcamiento(Integer id, String direction, Integer bikesCapacity, Float latitude, Float longitude) {
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

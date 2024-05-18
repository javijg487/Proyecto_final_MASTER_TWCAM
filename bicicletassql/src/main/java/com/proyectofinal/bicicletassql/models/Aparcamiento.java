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
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "direction")
    private String Direccion;

    @Column(name = "bikescapacity")
    private Integer CapacidadBici;

    @Column(name = "latitude")
    private Float Latitud;

    @Column(name = "longitude")
    private Float Longitud;

    public Aparcamiento() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public Integer getCapacidadBici() {
        return CapacidadBici;
    }

    public void setCapacidadBici(Integer capacidadBicicletas) {
        CapacidadBici = capacidadBicicletas;
    }

    public Float getLatitud() {
        return Latitud;
    }

    public void setLatitud(Float latitud) {
        Latitud = latitud;
    }

    public Float getLongitud() {
        return Longitud;
    }

    public void setLongitud(Float longitud) {
        Longitud = longitud;
    }
}

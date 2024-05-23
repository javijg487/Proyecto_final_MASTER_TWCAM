package com.proyectofinal.polucion.models;


public class EstacionDTO {

    private Integer id;
    private String Direccion;
    private Float Latitud;
    private Float Longitud;

    public EstacionDTO() {}

    public EstacionDTO(Integer id, String direccion, Float latitud, Float longitud) {
        this.id = id;
        Direccion = direccion;
        Latitud = latitud;
        Longitud = longitud;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
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

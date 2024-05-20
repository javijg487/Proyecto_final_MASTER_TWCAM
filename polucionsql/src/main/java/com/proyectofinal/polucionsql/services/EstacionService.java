package com.proyectofinal.polucionsql.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectofinal.polucionsql.repositories.EstacionRepository;
import com.proyectofinal.polucionsql.models.Estacion;
import java.util.List;

@Service
@Transactional
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    public List<Estacion> findAll() {
        return estacionRepository.findAll();
    }

    public Estacion create(Estacion Estacion) {
        try {
            Estacion u = this.estacionRepository.save(Estacion);
            return u;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Estacion findById(Integer id) {
        return this.estacionRepository.findById(id).orElse(null);
    }

    public void delete(Estacion Estacion) {
        this.estacionRepository.delete(Estacion);
    }

    public Estacion editEstacion(Estacion estacion, Integer id) {
        Estacion e = findById(id);
        
        if (e == null){
            return e;
        }

       
        e.setDireccion(estacion.getDireccion());
        e.setLatitud(estacion.getLatitud());
        e.setLongitud(estacion.getLongitud());

        return this.estacionRepository.save(e);
    }

}

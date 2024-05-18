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

    

}

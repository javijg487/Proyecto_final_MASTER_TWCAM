package com.proyectofinal.bicicletassql.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectofinal.bicicletassql.repositories.AparcamientoRepository;
import com.proyectofinal.bicicletassql.models.Aparcamiento;
import java.util.List;

@Service
@Transactional
public class AparcamientoService {

    @Autowired
    private AparcamientoRepository aparcamientoRepository;
    
    public List<Aparcamiento> findAll() {
        return aparcamientoRepository.findAll();
    }

    

}

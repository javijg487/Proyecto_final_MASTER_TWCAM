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
        return this.aparcamientoRepository.findAll();
    }

    public Aparcamiento create(Aparcamiento aparcamiento) {
        try {
			Aparcamiento a = this.aparcamientoRepository.save(aparcamiento);
			return a;
		} catch (IllegalArgumentException e) {
			return null;
		}
    }

    public void delete(Aparcamiento aparcamiento) {
        this.aparcamientoRepository.delete(aparcamiento);
    }

    public Aparcamiento findById(Integer id) {
        return this.aparcamientoRepository.findById(id).orElse(null);
    }

    public Aparcamiento editAparcamiento(Aparcamiento aparcamiento, Integer id) {
        Aparcamiento a = findById(id);
        
        if (a == null){
            return a;
        }

        a.setCapacidadBici(aparcamiento.getCapacidadBici());
        a.setDireccion(aparcamiento.getDireccion());
        a.setLatitud(aparcamiento.getLatitud());
        a.setLongitud(aparcamiento.getLongitud());

        return this.aparcamientoRepository.save(a);
    }
}

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
    private AparcamientoRepository ar;

    public List<Aparcamiento> findAll() {
        return this.ar.findAll();
    }

    public Aparcamiento create(Aparcamiento aparcamiento) {
        try {
			Aparcamiento a = this.ar.save(aparcamiento);
			return a;
		} catch (IllegalArgumentException e) {
			return null;
		}
    }

    public void delete(Aparcamiento aparcamiento) {
        this.ar.delete(aparcamiento);
    }

    public Aparcamiento findById(Integer id) {
        return this.ar.findById(id).orElse(null);
    }
}

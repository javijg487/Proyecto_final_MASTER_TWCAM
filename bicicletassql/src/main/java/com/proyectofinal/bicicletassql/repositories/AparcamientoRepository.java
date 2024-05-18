package com.proyectofinal.bicicletassql.repositories;
import com.proyectofinal.bicicletassql.models.Aparcamiento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AparcamientoRepository extends JpaRepository<Aparcamiento, String>{
    
}

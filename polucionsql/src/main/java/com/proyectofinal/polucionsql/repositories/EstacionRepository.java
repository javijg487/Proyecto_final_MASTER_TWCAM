package com.proyectofinal.polucionsql.repositories;
import com.proyectofinal.polucionsql.models.Estacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionRepository extends JpaRepository<Estacion, Integer>{
    
}
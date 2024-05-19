package com.proyectofinal.bicicletasmongo.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyectofinal.bicicletasmongo.models.AparcamientoMongo;
import com.proyectofinal.bicicletasmongo.repositories.AparcamientoMongoRepository;

@Service
public class AparcamientoMongoService {
    
    @Autowired
    AparcamientoMongoRepository amr;

    public List<AparcamientoMongo> findAll(){
        return amr.findAll();
    }
}

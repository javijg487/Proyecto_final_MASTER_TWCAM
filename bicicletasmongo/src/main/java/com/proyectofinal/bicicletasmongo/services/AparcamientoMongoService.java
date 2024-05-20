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
        return this.amr.findAll();
    }

    public void delete(AparcamientoMongo a){
        this.amr.delete(a);
    }

    public List<AparcamientoMongo> findAllById(Integer id){
        return this.amr.findAllByIdentificador(id);
    }
    
    public AparcamientoMongo create(AparcamientoMongo a){
        return this.amr.save(a);
    }

    public List<AparcamientoMongo> findByIdAndTimestampBetween(Integer id, String from, String to){
        return this.amr.findByIdentificadorAndTimestampBetween(id, from, to);
    }
}

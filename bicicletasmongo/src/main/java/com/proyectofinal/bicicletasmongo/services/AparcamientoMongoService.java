package com.proyectofinal.bicicletasmongo.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    
    public AparcamientoMongo create(AparcamientoMongo a, Integer id){
        try{
            LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			String formattedString = date.format(formatter);
            a.setIdentificador(id);
            a.setTimestamp(formattedString);
            AparcamientoMongo am = this.amr.save(a);
            return am;
        }catch (IllegalArgumentException e) {
			return null;
		}
    }

    public List<AparcamientoMongo> findByIdAndTimestampBetween(Integer id, String from, String to){
        return this.amr.findByIdentificadorAndTimestampBetween(id, from, to);
    }

    public AparcamientoMongo findFirstByIdentificadorOrderByTimestampDesc(Integer id){
        return this.amr.findFirstByIdentificadorOrderByTimestampDesc(id);
    }

}

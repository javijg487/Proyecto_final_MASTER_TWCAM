package com.proyectofinal.bicicletasmongo.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.bicicletasmongo.models.AparcamientoMongo;
import com.proyectofinal.bicicletasmongo.services.AparcamientoMongoService;

@RestController
@RequestMapping("aparcamiento")
public class AparacamientoMongoController {
    
    @Autowired
	AparcamientoMongoService ams;

    @GetMapping("/status")
	public ResponseEntity<List<AparcamientoMongo>> getAll() {
		List<AparcamientoMongo> users = new ArrayList<>();
		
		users = ams.findAll();
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

    @DeleteMapping("aparcamiento/{id}")
    public void deleteAparcamiento(){
        System.out.println("Deleting aparacamiento");
    }

    @PostMapping("/evento/{id}")
    public void getEventoById(){
        
    }

}

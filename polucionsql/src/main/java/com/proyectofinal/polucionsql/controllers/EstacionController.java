package com.proyectofinal.polucionsql.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.proyectofinal.polucionsql.models.Estacion;
import com.proyectofinal.polucionsql.services.EstacionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("")
public class EstacionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(EstacionController.class);
    @Autowired
    private EstacionService estacionService;

    @GetMapping("/estaciones")
    public ResponseEntity<List<Estacion>> findEstaciones() {
        LOGGER.debug("View all estaciones");
        List<Estacion> estaciones = new ArrayList<Estacion>();
        estaciones = estacionService.findAll();
        return new ResponseEntity<>(estaciones, HttpStatus.OK);
    }

    @PostMapping("/estacion")
    public ResponseEntity<?> create(@RequestBody Estacion Estacion) throws IOException {
        Estacion e = estacionService.create(Estacion);
        if (e == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(e, HttpStatus.CREATED);
    }

    @DeleteMapping("/estacion/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Estacion e = estacionService.findById(id);
        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        estacionService.delete(e);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @PutMapping("estacion/{id}")
    public ResponseEntity<Estacion> editEstacion(@PathVariable Integer id, @RequestBody Estacion estacion) {
        Estacion e = estacionService.editEstacion(estacion, id);

        if(e == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
    }

}

package com.proyectofinal.bicicletassql.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;

import com.proyectofinal.bicicletassql.models.Aparcamiento;
import com.proyectofinal.bicicletassql.services.AparcamientoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("")
public class AparcamientoController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AparcamientoController.class);
    @Autowired
    private AparcamientoService aparcamientoService;

    @GetMapping("/aparcamientos")
    public ResponseEntity<List<Aparcamiento>> findAparcamientos() {
        LOGGER.debug("View all aparcamientos");
        List<Aparcamiento> aparcamientos = aparcamientoService.findAll();
        return new ResponseEntity<>(aparcamientos, HttpStatus.OK);
    }
    
    @PostMapping("/aparcamiento")
    public ResponseEntity<?> create(@RequestBody Aparcamiento aparcamiento) throws IOException{
        Aparcamiento a = aparcamientoService.create(aparcamiento);
        if(a == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @DeleteMapping("/aparcamiento/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Aparcamiento a = aparcamientoService.findById(id);
        if(a == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        aparcamientoService.delete(a);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @PutMapping("/aparcamiento/{id}")
    public ResponseEntity<Aparcamiento> editAparcamiento(@PathVariable Integer id, @RequestBody Aparcamiento aparcamiento) {
        Aparcamiento a = aparcamientoService.editAparcamiento(aparcamiento, id);

        if(a == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            
            return new ResponseEntity<>(a, HttpStatus.OK);
        }
    }
}

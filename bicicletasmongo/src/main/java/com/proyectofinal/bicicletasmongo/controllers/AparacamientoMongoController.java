package com.proyectofinal.bicicletasmongo.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}/status")
    public ResponseEntity<List<AparcamientoMongo>> getAparcamientoById(@PathVariable Integer id,
            @RequestParam("from") Optional<String> from, @RequestParam("to") Optional<String> to) {

        if (from.isPresent() && to.isPresent()) {
            List<AparcamientoMongo> am = ams.findByIdAndTimestampBetween(id, from.get(), to.get());
            if (am.isEmpty()) {
                System.out.println("No se encontraron registros");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(am, HttpStatus.OK);
        } else {
            List<AparcamientoMongo> am = ams.findAllById(id);

            if (am.isEmpty()) {
                System.out.println("No se encontraron registros");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(am, HttpStatus.OK);
        }
    }

    // @DeleteMapping("aparcamiento/{id}")
    // public ResponseEntity<?> delete(){
    // AparcamientoMongo
    // System.out.println("Deleting aparacamiento");
    // }

    @PostMapping("/aparcamiento/{id}")
    public ResponseEntity<?> create(@RequestBody AparcamientoMongo a) {
        AparcamientoMongo aparcamiento = this.ams.create(a);

        if (aparcamiento == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(aparcamiento, HttpStatus.CREATED);
    }
}

package com.proyectofinal.polucionsql.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.slf4j.Logger;

import com.proyectofinal.polucionsql.models.Estacion;
import com.proyectofinal.polucionsql.services.EstacionService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("")
public class EstacionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(EstacionController.class);
    @Autowired
    private EstacionService estacionService;

    @GetMapping("/estaciones")
    public List<Estacion> findEstaciones() {
        LOGGER.debug("View all estaciones");
        return estacionService.findAll();
    }
    
}

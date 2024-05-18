package com.proyectofinal.bicicletassql.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.slf4j.Logger;

import com.proyectofinal.bicicletassql.models.Aparcamiento;
import com.proyectofinal.bicicletassql.services.AparcamientoService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("")
public class AparcamientoController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AparcamientoController.class);
    @Autowired
    private AparcamientoService aparcamientoService;

    @GetMapping("/aparcamientos")
    public List<Aparcamiento> findAparcamientos() {
        LOGGER.debug("View all aparcamientos");
        return aparcamientoService.findAll();
    }
    
}

package com.escuela.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Profesor;
import com.escuela.service.ProfesorService;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private static final Logger logger =
            LogManager.getLogger(ProfesorController.class);

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    // GET /profesores
    @GetMapping
    public List<Profesor> listarTodos() {

        logger.info("GET /profesores");

        List<Profesor> profesores = profesorService.listar();

        logger.info("Total de profesores encontrados: {}", profesores.size());
        return profesores;
    }

    // GET /profesores/{id}
    @GetMapping("/{id}")
    public Profesor obtenerPorId(@PathVariable Long id) {

        logger.info("GET /profesores/{}", id);

        Profesor profesor = profesorService.buscarPorId(id);

        logger.debug("Profesor encontrado id={}, nombre={}",
                profesor.getId(), profesor.getNombre());

        return profesor;
    }

    // POST /profesores
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Profesor guardar(@Valid @RequestBody Profesor profesor) {

        logger.info("POST /profesores");
        logger.debug("Datos recibidos: nombre={}, especialidad={}",
                profesor.getNombre(), profesor.getEspecialidad());

        Profesor guardado = profesorService.guardar(profesor);

        logger.info("Profesor creado con id={}", guardado.getId());
        return guardado;
    }

    // PUT /profesores/{id}
    @PutMapping("/{id}")
    public Profesor actualizar(
            @PathVariable Long id,
            @RequestBody Profesor profesor) {

        logger.info("PUT /profesores/{}", id);
        logger.debug("Datos a actualizar: nombre={}, especialidad={}",
                profesor.getNombre(), profesor.getEspecialidad());

        Profesor actualizado = profesorService.actualizar(id, profesor);

        logger.info("Profesor actualizado id={}", actualizado.getId());
        return actualizado;
    }

    // DELETE /profesores/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {

        logger.warn("DELETE /profesores/{} - Eliminando profesor", id);

        profesorService.eliminarProfesor(id);

        logger.info("Profesor eliminado id={}", id);
    }
}


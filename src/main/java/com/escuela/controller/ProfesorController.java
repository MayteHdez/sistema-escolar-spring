package com.escuela.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Profesor;
import com.escuela.service.ProfesorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    // GET /profesores
    @GetMapping
    public List<Profesor> listarTodos() {
        return profesorService.listar();
    }

    // GET /profesores/{id}
    @GetMapping("/{id}")
    public Profesor obtenerPorId(@PathVariable Long id) {
        return profesorService.buscarPorId(id);
    }

    // POST /profesores
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Profesor guardar(@Valid @RequestBody Profesor profesor) {
        return profesorService.guardar(profesor);
    }
    
    // PUT /profesores/{id}
    @PutMapping("/{id}")
    public Profesor actualizar(
            @PathVariable Long id,
            @RequestBody Profesor profesor) {

        return profesorService.actualizar(id, profesor);
    }


    // DELETE /profesores/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        profesorService.eliminarProfesor(id);
    }
}

package com.escuela.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Inscripcion;
import com.escuela.service.InscripcionService;


@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // GET /inscripciones
    @GetMapping
    public List<Inscripcion> listarTodas() {
        return inscripcionService.listarInscripciones();
    }

    // GET /inscripciones/{id}
    @GetMapping("/{id}")
    public Inscripcion obtenerPorId(@PathVariable Long id) {
        return inscripcionService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inscripcion inscribirAlumno(
            @RequestParam Long alumnoId,
            @RequestParam Long materiaId) {

        return inscripcionService.inscribirAlumno(alumnoId, materiaId);
    }


    // DELETE /inscripciones/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        inscripcionService.eliminarInscripcion(id);
    }
}


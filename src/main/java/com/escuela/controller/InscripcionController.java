package com.escuela.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Inscripcion;
import com.escuela.service.InscripcionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private static final Logger logger =
            LogManager.getLogger(InscripcionController.class);

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // GET /inscripciones
    @GetMapping
    public List<Inscripcion> listarTodas() {

        logger.info("GET /inscripciones");

        List<Inscripcion> inscripciones = inscripcionService.listarInscripciones();

        logger.info("Total de inscripciones encontradas: {}", inscripciones.size());
        return inscripciones;
    }

    // GET /inscripciones/{id}
    @GetMapping("/{id}")
    public Inscripcion obtenerPorId(@PathVariable Long id) {

        logger.info("GET /inscripciones/{}", id);

        Inscripcion inscripcion = inscripcionService.buscarPorId(id);

        logger.debug("Inscripción encontrada id={}, alumnoId={}, materiaId={}",
                inscripcion.getId(),
                inscripcion.getAlumno().getId(),
                inscripcion.getMateria().getId());

        return inscripcion;
    }

    // POST /inscripciones?alumnoId=1&materiaId=2
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inscripcion inscribirAlumno(
            @RequestParam Long alumnoId,
            @RequestParam Long materiaId) {

        logger.info("POST /inscripciones - alumnoId={}, materiaId={}",
                alumnoId, materiaId);

        Inscripcion inscripcion =
                inscripcionService.inscribirAlumno(alumnoId, materiaId);

        logger.info("Inscripción creada con id={}", inscripcion.getId());
        return inscripcion;
    }

    // DELETE /inscripciones/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {

        logger.warn("DELETE /inscripciones/{} - Eliminando inscripción", id);

        inscripcionService.eliminarInscripcion(id);

        logger.info("Inscripción eliminada id={}", id);
    }
}



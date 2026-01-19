package com.escuela.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Alumno;
import com.escuela.service.AlumnoService;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private static final Logger logger = LogManager.getLogger(AlumnoController.class);

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // GET /alumnos
    @GetMapping
    public List<Alumno> listarTodos() {
        logger.info("Solicitud GET /alumnos - Listando todos los alumnos");
        List<Alumno> alumnos = alumnoService.listarTodos();
        logger.debug("Cantidad de alumnos encontrados: {}", alumnos.size());
        return alumnos;
    }

    // GET /alumnos/{id}
    @GetMapping("/{id}")
    public Alumno obtenerPorId(@PathVariable Long id) {
        logger.info("Solicitud GET /alumnos/{} - Buscando alumno", id);
        try {
            Alumno alumno = alumnoService.buscarPorId(id);
            logger.debug("Alumno encontrado: {}", alumno.getNombre());
            return alumno;
        } catch (RuntimeException e) {
            logger.error("Error al buscar alumno con id {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // POST /alumnos
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alumno guardar(@Valid @RequestBody Alumno alumno) {
        logger.info("Solicitud POST /alumnos - Guardando alumno: {}", alumno.getNombre());
        try {
            Alumno saved = alumnoService.guardar(alumno);
            logger.debug("Alumno guardado con ID: {}", saved.getId());
            return saved;
        } catch (RuntimeException e) {
            logger.error("Error al guardar alumno {}: {}", alumno.getNombre(), e.getMessage());
            throw e;
        }
    }

    // PUT /alumnos/{id}
    @PutMapping("/{id}")
    public Alumno actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Alumno alumno) {

        logger.info("Solicitud PUT /alumnos/{} - Actualizando alumno", id);
        try {
            Alumno updated = alumnoService.actualizar(id, alumno);
            logger.debug("Alumno actualizado: {}", updated.getNombre());
            return updated;
        } catch (RuntimeException e) {
            logger.error("Error al actualizar alumno con id {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // DELETE /alumnos/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        logger.info("Solicitud DELETE /alumnos/{} - Eliminando alumno", id);
        try {
            alumnoService.eliminar(id);
            logger.debug("Alumno eliminado con id {}", id);
        } catch (RuntimeException e) {
            logger.error("Error al eliminar alumno con id {}: {}", id, e.getMessage());
            throw e;
        }
    }
}



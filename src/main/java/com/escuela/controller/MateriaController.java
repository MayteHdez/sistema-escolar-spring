package com.escuela.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Materia;
import com.escuela.service.MateriaService;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    private static final Logger logger =
            LogManager.getLogger(MateriaController.class);

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    // GET /materias
    @GetMapping
    public List<Materia> listarTodas() {

        logger.info("GET /materias");

        List<Materia> materias = materiaService.listarTodas();

        logger.info("Total de materias encontradas: {}", materias.size());
        return materias;
    }

    // GET /materias/{id}
    @GetMapping("/{id}")
    public Materia obtenerPorId(@PathVariable Long id) {

        logger.info("GET /materias/{}", id);

        Materia materia = materiaService.buscarPorId(id);

        logger.debug("Materia encontrada id={}, nombre={}",
                materia.getId(), materia.getNombre());

        return materia;
    }

    // POST /materias
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Materia guardar(@Valid @RequestBody Materia materia) {

        logger.info("POST /materias");
        logger.debug("Datos recibidos: nombre={}, creditos={}",
                materia.getNombre(), materia.getCreditos());

        Materia guardada = materiaService.guardar(materia);

        logger.info("Materia creada con id={}", guardada.getId());
        return guardada;
    }

    // PUT /materias/{id}
    @PutMapping("/{id}")
    public Materia actualizar(
            @PathVariable Long id,
            @RequestBody Materia materia) {

        logger.info("PUT /materias/{}", id);
        logger.debug("Datos a actualizar: nombre={}, creditos={}",
                materia.getNombre(), materia.getCreditos());

        Materia actualizada = materiaService.actualizar(id, materia);

        logger.info("Materia actualizada id={}", actualizada.getId());
        return actualizada;
    }

    // DELETE /materias/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {

        logger.warn("DELETE /materias/{} - Eliminando materia", id);

        materiaService.eliminarMateria(id);

        logger.info("Materia eliminada id={}", id);
    }
}



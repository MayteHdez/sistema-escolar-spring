package com.escuela.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Materia;
import com.escuela.service.MateriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    // GET /materias
    @GetMapping
    public List<Materia> listarTodas() {
        return materiaService.listarTodas();
    }

    // GET /materias/{id}
    @GetMapping("/{id}")
    public Materia obtenerPorId(@PathVariable Long id) {
        return materiaService.buscarPorId(id);
    }

    // POST /materias
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Materia guardar(@Valid @RequestBody Materia materia) {
        return materiaService.guardar(materia);
    }
    
    // PUT /materias/{id}
    @PutMapping("/{id}")
    public Materia actualizar(
            @PathVariable Long id,
            @RequestBody Materia materia) {

        return materiaService.actualizar(id, materia);
    }


    // DELETE /materias/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
    }
}


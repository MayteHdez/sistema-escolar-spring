package com.escuela.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.escuela.model.Calificacion;
import com.escuela.service.CalificacionService;
import com.escuela.dto.CalificacionRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {
	
	private static final Logger logger =
	           LogManager.getLogger(CalificacionController.class);

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    // POST /calificaciones?inscripcionId=1
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Calificacion asignarCalificacion(
            @RequestParam Long inscripcionId,
            @RequestBody CalificacionRequest request) {
    	
    	logger.info("POST /calificaciones - inscripcionId={}", inscripcionId);

        return calificacionService.asignarCalificacion(
                inscripcionId,
                request.getValor(),
                request.getObservaciones()
        );
    }


    // GET /calificaciones/inscripcion/1
    @GetMapping("/inscripcion/{id}")
    public Calificacion obtenerPorInscripcion(@PathVariable Long id) {
        return calificacionService.buscarPorInscripcion(id);
    }

    // PUT /calificaciones/{id}
    @PutMapping("/{id}")
    public Calificacion actualizar(
            @PathVariable Long id,
            @RequestBody CalificacionRequest request) {

        return calificacionService.actualizarCalificacion(
                id,
                request.getValor(),
                request.getObservaciones()
        );
    }
    
    // DELETE /calificaciones/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
    	logger.warn("DELETE /calificaciones/{}", id);
        calificacionService.eliminarCalificacion(id);
    }



}

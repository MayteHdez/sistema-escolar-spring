package com.escuela.service;

import org.springframework.stereotype.Service;

import com.escuela.model.Calificacion;
import com.escuela.model.Inscripcion;
import com.escuela.repository.CalificacionRepository;
import com.escuela.repository.InscripcionRepository;

@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;

    public CalificacionService(
            CalificacionRepository calificacionRepository,
            InscripcionRepository inscripcionRepository) {
        this.calificacionRepository = calificacionRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    // Asignar calificación a una inscripción
    public Calificacion asignarCalificacion(
            Long inscripcionId,
            Double valor,
            String observaciones) {

        if (valor < 0 || valor > 10) {
            throw new RuntimeException("La calificación debe estar entre 0 y 10");
        }

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        // Verificar que no exista ya calificación
        if (calificacionRepository.existsByInscripcion(inscripcion)) {
            throw new RuntimeException("Esta inscripción ya tiene calificación");
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setCalificacion(valor);
        calificacion.setObservaciones(observaciones);
        calificacion.setInscripcion(inscripcion);

        return calificacionRepository.save(calificacion);
    }

    // Buscar por inscripción
    public Calificacion buscarPorInscripcion(Long inscripcionId) {

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        return calificacionRepository.findByInscripcion(inscripcion)
                .orElseThrow(() -> new RuntimeException("No hay calificación registrada"));
    }

    public Calificacion actualizarCalificacion(
            Long calificacionId,
            Double valor,
            String observaciones) {

        Calificacion calificacion = calificacionRepository.findById(calificacionId)
                .orElseThrow(() -> new RuntimeException("Calificación no encontrada"));

        //Solo validar si viene valor
        if (valor != null) {
            if (valor < 0.0 || valor > 10.0) {
                throw new RuntimeException("La calificación debe estar entre 0 y 10");
            }
            calificacion.setCalificacion(valor);
        }

        // Solo actualizar si vienen observaciones
        if (observaciones != null) {
            calificacion.setObservaciones(observaciones);
        }

        return calificacionRepository.save(calificacion);
    }
    
    public void eliminarCalificacion(Long calificacionId) {

        Calificacion calificacion = calificacionRepository.findById(calificacionId)
                .orElseThrow(() -> new RuntimeException("Calificación no encontrada"));

        // Romper relación bidireccional
        Inscripcion inscripcion = calificacion.getInscripcion();
        if (inscripcion != null) {
            inscripcion.setCalificacion(null);
        }

        calificacionRepository.delete(calificacion);
    }



}

package com.escuela.service;

import org.springframework.stereotype.Service;

import com.escuela.model.Calificacion;
import com.escuela.model.Inscripcion;
import com.escuela.repository.CalificacionRepository;
import com.escuela.repository.InscripcionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class CalificacionService {

    private static final Logger logger =
            LogManager.getLogger(CalificacionService.class);

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

        logger.info("Iniciando asignación de calificación a inscripción {}", inscripcionId);

        // Validación de negocio
        if (valor < 0 || valor > 10) {
            logger.warn("Valor de calificación inválido: {}", valor);
            throw new RuntimeException("La calificación debe estar entre 0 y 10");
        }

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> {
                    logger.error("Inscripción {} no encontrada", inscripcionId);
                    return new RuntimeException("Inscripción no encontrada");
                });

        // Regla de negocio: una inscripción solo puede tener una calificación
        if (calificacionRepository.existsByInscripcion(inscripcion)) {
            logger.warn("La inscripción {} ya tiene calificación asignada", inscripcionId);
            throw new RuntimeException("Esta inscripción ya tiene calificación");
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setCalificacion(valor);
        calificacion.setObservaciones(observaciones);
        calificacion.setInscripcion(inscripcion);

        Calificacion guardada = calificacionRepository.save(calificacion);

        logger.info("Calificación creada exitosamente con id={}", guardada.getId());
        return guardada;
    }

    // Buscar por inscripción
    public Calificacion buscarPorInscripcion(Long inscripcionId) {

        logger.info("Buscando calificación por inscripción {}", inscripcionId);

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> {
                    logger.error("Inscripción {} no encontrada al buscar calificación", inscripcionId);
                    return new RuntimeException("Inscripción no encontrada");
                });

        Calificacion calificacion = calificacionRepository.findByInscripcion(inscripcion)
                .orElseThrow(() -> {
                    logger.warn("No existe calificación para inscripción {}", inscripcionId);
                    return new RuntimeException("No hay calificación registrada");
                });

        logger.debug("Calificación encontrada id={}, valor={}",
                calificacion.getId(), calificacion.getCalificacion());

        return calificacion;
    }

    // Actualizar calificación
    public Calificacion actualizarCalificacion(
            Long calificacionId,
            Double valor,
            String observaciones) {

        logger.info("Actualizando calificación {}", calificacionId);

        Calificacion calificacion = calificacionRepository.findById(calificacionId)
                .orElseThrow(() -> {
                    logger.error("Calificación {} no encontrada para actualizar", calificacionId);
                    return new RuntimeException("Calificación no encontrada");
                });

        boolean cambios = false;

        // Solo validar si viene valor
        if (valor != null) {
            logger.debug("Nuevo valor recibido: {}", valor);

            if (valor < 0.0 || valor > 10.0) {
                logger.warn("Valor inválido al actualizar calificación {}: {}", calificacionId, valor);
                throw new RuntimeException("La calificación debe estar entre 0 y 10");
            }
            calificacion.setCalificacion(valor);
            cambios = true;
        }

        // Solo actualizar si vienen observaciones
        if (observaciones != null) {
            logger.debug("Actualizando observaciones de calificación {}", calificacionId);
            calificacion.setObservaciones(observaciones);
            cambios = true;
        }

        if (!cambios) {
            logger.warn("No se enviaron cambios para la calificación {}", calificacionId);
        }

        Calificacion actualizada = calificacionRepository.save(calificacion);

        logger.info("Calificación {} actualizada correctamente", actualizada.getId());
        return actualizada;
    }

    // Eliminar calificación
    public void eliminarCalificacion(Long calificacionId) {

        logger.warn("Eliminando calificación {}", calificacionId);

        Calificacion calificacion = calificacionRepository.findById(calificacionId)
                .orElseThrow(() -> {
                    logger.error("Calificación {} no encontrada para eliminar", calificacionId);
                    return new RuntimeException("Calificación no encontrada");
                });

        // Romper relación bidireccional
        Inscripcion inscripcion = calificacion.getInscripcion();
        if (inscripcion != null) {
            logger.debug("Rompiendo relación con inscripción {}", inscripcion.getId());
            inscripcion.setCalificacion(null);
        }

        calificacionRepository.delete(calificacion);

        logger.info("Calificación {} eliminada correctamente", calificacionId);
    }
}

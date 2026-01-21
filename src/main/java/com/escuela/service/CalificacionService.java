package com.escuela.service;

import com.escuela.model.Calificacion;

public interface CalificacionService {

    Calificacion asignarCalificacion(Long inscripcionId, Double valor, String observaciones);

    Calificacion buscarPorInscripcion(Long inscripcionId);

    Calificacion actualizarCalificacion(Long calificacionId, Double valor, String observaciones);

    void eliminarCalificacion(Long calificacionId);
}

package com.escuela.service;

import java.util.List;
import com.escuela.model.Inscripcion;

public interface InscripcionService {

    List<Inscripcion> listarInscripciones();

    Inscripcion buscarPorId(Long id);

    Inscripcion inscribirAlumno(Long alumnoId, Long materiaId);

    void eliminarInscripcion(Long id);
}

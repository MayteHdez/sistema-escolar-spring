package com.escuela.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.escuela.model.Alumno;
import com.escuela.model.Materia;
import com.escuela.model.Inscripcion;
import com.escuela.repository.AlumnoRepository;
import com.escuela.repository.MateriaRepository;
import com.escuela.repository.InscripcionRepository;
import java.time.LocalDate;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    public InscripcionService(
            InscripcionRepository inscripcionRepository,
            AlumnoRepository alumnoRepository,
            MateriaRepository materiaRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.alumnoRepository = alumnoRepository;
        this.materiaRepository = materiaRepository;
    }

    // LISTAR TODAS LAS INSCRIPCIONES
    public List<Inscripcion> listarInscripciones() {
        return inscripcionRepository.findAll();
    }

    // BUSCAR INSCRIPCIÓN POR ID
    public Inscripcion buscarPorId(Long id) {
        return inscripcionRepository.findById(id).orElse(null);
    }

    // INSCRIBIR ALUMNO A MATERIA (REGLA DE NEGOCIO)
    public Inscripcion inscribirAlumno(Long alumnoId, Long materiaId) {

        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no existe"));

        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Materia no existe"));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setAlumno(alumno);
        inscripcion.setMateria(materia);

        //FECHA AUTOMÁTICA DE INSCRIPCIÓN
        inscripcion.setFechaInscripcion(LocalDate.now());

        return inscripcionRepository.save(inscripcion);
    }

    // ELIMINAR INSCRIPCIÓN
    public void eliminarInscripcion(Long id) {
        inscripcionRepository.deleteById(id);
    }
}


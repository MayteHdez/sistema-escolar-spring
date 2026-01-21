package com.escuela.service;

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.escuela.model.Alumno;
import com.escuela.model.Materia;
import com.escuela.model.Inscripcion;
import com.escuela.repository.AlumnoRepository;
import com.escuela.repository.MateriaRepository;
import com.escuela.repository.InscripcionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class InscripcionServiceImpl implements InscripcionService{

    private static final Logger logger =
            LogManager.getLogger(InscripcionServiceImpl.class);

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    public InscripcionServiceImpl(
            InscripcionRepository inscripcionRepository,
            AlumnoRepository alumnoRepository,
            MateriaRepository materiaRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.alumnoRepository = alumnoRepository;
        this.materiaRepository = materiaRepository;
    }

    // LISTAR TODAS LAS INSCRIPCIONES
    @Override
    public List<Inscripcion> listarInscripciones() {

        logger.info("Listando todas las inscripciones");

        List<Inscripcion> inscripciones = inscripcionRepository.findAll();

        logger.info("Total de inscripciones encontradas: {}", inscripciones.size());
        return inscripciones;
    }

    // BUSCAR INSCRIPCIÓN POR ID
    @Override
    public Inscripcion buscarPorId(Long id) {

        logger.info("Buscando inscripción por id={}", id);

        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Inscripción {} no encontrada", id);
                    return new RuntimeException("Inscripción no encontrada");
                });

        logger.debug("Inscripción encontrada id={}, alumnoId={}, materiaId={}",
                inscripcion.getId(),
                inscripcion.getAlumno().getId(),
                inscripcion.getMateria().getId());

        return inscripcion;
    }

    // INSCRIBIR ALUMNO A MATERIA (REGLA DE NEGOCIO)
    @Override
    public Inscripcion inscribirAlumno(Long alumnoId, Long materiaId) {

        logger.info("Iniciando inscripción: alumnoId={}, materiaId={}",
                alumnoId, materiaId);

        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> {
                    logger.error("Alumno {} no existe", alumnoId);
                    return new RuntimeException("Alumno no existe");
                });

        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> {
                    logger.error("Materia {} no existe", materiaId);
                    return new RuntimeException("Materia no existe");
                });

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setAlumno(alumno);
        inscripcion.setMateria(materia);

        // FECHA AUTOMÁTICA DE INSCRIPCIÓN
        inscripcion.setFechaInscripcion(LocalDate.now());

        Inscripcion guardada = inscripcionRepository.save(inscripcion);

        logger.info("Inscripción creada exitosamente con id={}", guardada.getId());
        return guardada;
    }

    // ELIMINAR INSCRIPCIÓN
    @Override
    public void eliminarInscripcion(Long id) {

        logger.warn("Eliminando inscripción id={}", id);

        if (!inscripcionRepository.existsById(id)) {
            logger.error("Intento de eliminar inscripción inexistente id={}", id);
            throw new RuntimeException("Inscripción no encontrada");
        }

        inscripcionRepository.deleteById(id);

        logger.info("Inscripción eliminada correctamente id={}", id);
    }
}

package com.escuela.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.escuela.model.Alumno;
import com.escuela.repository.AlumnoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;


@Service
public class AlumnoService {

    private static final Logger logger = LogManager.getLogger(AlumnoService.class);

    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    // Listar todos los alumnos
    public List<Alumno> listarTodos() {
        logger.info("Listando todos los alumnos");
        List<Alumno> alumnos = alumnoRepository.findAll();
        logger.debug("Cantidad de alumnos encontrados: {}", alumnos.size());
        return alumnos;
    }

    // Guardar alumno
    public Alumno guardar(Alumno alumno) {
        logger.info("Intentando guardar alumno: {}", alumno.getNombre());
        try {
            if (alumno.getNombre() == null || alumno.getNombre().isEmpty()) {
                throw new RuntimeException("El nombre del alumno es obligatorio");
            }

            //ASIGNAR FECHA AUTOMÁTICAMENTE
            alumno.setFechaRegistro(LocalDate.now());

            Alumno saved = alumnoRepository.save(alumno);
            logger.info("Alumno guardado con ID: {}", saved.getId());
            return saved;

        } catch (RuntimeException e) {
            logger.error("Error al guardar alumno {}: {}", alumno.getNombre(), e.getMessage());
            throw e;
        }
    }


    // Buscar alumno por ID
    public Alumno buscarPorId(Long id) {
        logger.info("Buscando alumno con ID: {}", id);
        try {
            Alumno alumno = alumnoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
            logger.debug("Alumno encontrado: {}", alumno.getNombre());
            return alumno;
        } catch (RuntimeException e) {
            logger.error("Error al buscar alumno con ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Eliminar alumno
    public void eliminar(Long id) {
        logger.info("Eliminando alumno con ID: {}", id);
        try {
            alumnoRepository.deleteById(id);
            logger.debug("Alumno eliminado con ID: {}", id);
        } catch (RuntimeException e) {
            logger.error("Error al eliminar alumno con ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Actualizar alumno
    public Alumno actualizar(Long id, Alumno alumno) {
        logger.info("Actualizando alumno con ID: {}", id);
        try {
            Alumno alumnoExistente = alumnoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

            logger.debug("Alumno antes de actualizar: {}", alumnoExistente);

            if (alumno.getNombre() != null) {
                alumnoExistente.setNombre(alumno.getNombre());
            }

            if (alumno.getEmail() != null) {
                alumnoExistente.setEmail(alumno.getEmail());
            }

            Alumno updated = alumnoRepository.save(alumnoExistente);
            logger.debug("Alumno actualizado: {}", updated);
            logger.info("Alumno con ID {} actualizado correctamente", id);

            return updated;
        } catch (RuntimeException e) {
            logger.error("Error al actualizar alumno con ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}


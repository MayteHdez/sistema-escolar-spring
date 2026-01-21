package com.escuela.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.escuela.model.Alumno;
import com.escuela.repository.AlumnoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private static final Logger logger = LogManager.getLogger(AlumnoServiceImpl.class);

    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImpl(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public List<Alumno> listarTodos() {
        logger.info("Listando todos los alumnos");
        List<Alumno> alumnos = alumnoRepository.findAll();
        logger.debug("Cantidad de alumnos encontrados: {}", alumnos.size());
        return alumnos;
    }

    @Override
    public Alumno guardar(Alumno alumno) {
        logger.info("Intentando guardar alumno: {}", alumno.getNombre());
        if (alumno.getNombre() == null || alumno.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del alumno es obligatorio");
        }
        alumno.setFechaRegistro(LocalDate.now());
        Alumno saved = alumnoRepository.save(alumno);
        logger.info("Alumno guardado con ID: {}", saved.getId());
        return saved;
    }

    @Override
    public Alumno buscarPorId(Long id) {
        logger.info("Buscando alumno con ID: {}", id);
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        logger.info("Eliminando alumno con ID: {}", id);
        alumnoRepository.deleteById(id);
        logger.debug("Alumno eliminado con ID: {}", id);
    }

    @Override
    public Alumno actualizar(Long id, Alumno alumno) {
        logger.info("Actualizando alumno con ID: {}", id);
        Alumno alumnoExistente = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        if (alumno.getNombre() != null) alumnoExistente.setNombre(alumno.getNombre());
        if (alumno.getEmail() != null) alumnoExistente.setEmail(alumno.getEmail());

        Alumno updated = alumnoRepository.save(alumnoExistente);
        logger.info("Alumno con ID {} actualizado correctamente", id);
        return updated;
    }
}

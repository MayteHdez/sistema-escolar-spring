package com.escuela.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.escuela.model.Profesor;
import com.escuela.repository.ProfesorRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class ProfesorService {

    private static final Logger logger =
            LogManager.getLogger(ProfesorService.class);

    private final ProfesorRepository profesorRepository;

    // Inyección por constructor
    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    // Guardar profesor (con validación básica)
    public Profesor guardar(Profesor profesor) {

        logger.info("Creando nuevo profesor");

        if (profesor.getNombre() == null || profesor.getNombre().isEmpty()) {
            logger.warn("Intento de crear profesor sin nombre");
            throw new RuntimeException("El nombre del profesor es obligatorio");
        }

        logger.debug("Datos profesor: nombre={}, email={}, especialidad={}",
                profesor.getNombre(),
                profesor.getEmail(),
                profesor.getEspecialidad());

        Profesor guardado = profesorRepository.save(profesor);

        logger.info("Profesor creado exitosamente con id={}", guardado.getId());
        return guardado;
    }

    // Listar todos
    public List<Profesor> listar() {

        logger.info("Listando todos los profesores");

        List<Profesor> profesores = profesorRepository.findAll();

        logger.info("Total de profesores encontrados: {}", profesores.size());
        return profesores;
    }

    // Buscar por ID
    public Profesor buscarPorId(Long id) {

        logger.info("Buscando profesor por id={}", id);

        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Profesor {} no encontrado", id);
                    return new RuntimeException("Profesor no encontrado");
                });

        logger.debug("Profesor encontrado id={}, nombre={}",
                profesor.getId(), profesor.getNombre());

        return profesor;
    }

    // Actualizar
    public Profesor actualizar(Long id, Profesor profesor) {

        logger.info("Actualizando profesor id={}", id);

        Profesor profesorExistente = profesorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Profesor {} no encontrado para actualizar", id);
                    return new RuntimeException("Profesor con id " + id + " no encontrado");
                });

        boolean cambios = false;

        if (profesor.getNombre() != null) {
            logger.debug("Actualizando nombre a '{}'", profesor.getNombre());
            profesorExistente.setNombre(profesor.getNombre());
            cambios = true;
        }

        if (profesor.getEmail() != null) {
            logger.debug("Actualizando email a '{}'", profesor.getEmail());
            profesorExistente.setEmail(profesor.getEmail());
            cambios = true;
        }

        if (profesor.getEspecialidad() != null) {
            logger.debug("Actualizando especialidad a '{}'", profesor.getEspecialidad());
            profesorExistente.setEspecialidad(profesor.getEspecialidad());
            cambios = true;
        }

        if (!cambios) {
            logger.warn("No se enviaron cambios para profesor id={}", id);
        }

        Profesor actualizado = profesorRepository.save(profesorExistente);

        logger.info("Profesor actualizado correctamente id={}", actualizado.getId());
        return actualizado;
    }

    // Eliminar
    public void eliminarProfesor(Long id) {

        logger.warn("Eliminando profesor id={}", id);

        if (!profesorRepository.existsById(id)) {
            logger.error("Intento de eliminar profesor inexistente id={}", id);
            throw new RuntimeException("Profesor no encontrado");
        }

        profesorRepository.deleteById(id);

        logger.info("Profesor eliminado correctamente id={}", id);
    }
}


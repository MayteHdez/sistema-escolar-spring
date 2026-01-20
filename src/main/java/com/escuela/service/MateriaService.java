package com.escuela.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.escuela.model.Materia;
import com.escuela.repository.MateriaRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class MateriaService {

    private static final Logger logger =
            LogManager.getLogger(MateriaService.class);

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    // LISTAR TODAS
    public List<Materia> listarTodas() {

        logger.info("Listando todas las materias");

        List<Materia> materias = materiaRepository.findAll();

        logger.info("Total de materias encontradas: {}", materias.size());
        return materias;
    }

    // GUARDAR
    public Materia guardar(Materia materia) {

        logger.info("Creando nueva materia");

        if (materia.getNombre() == null || materia.getNombre().isEmpty()) {
            logger.warn("Intento de crear materia sin nombre");
            throw new RuntimeException("El nombre de la materia es obligatorio");
        }

        logger.debug("Datos materia: nombre={}, creditos={}",
                materia.getNombre(), materia.getCreditos());

        Materia guardada = materiaRepository.save(materia);

        logger.info("Materia creada exitosamente con id={}", guardada.getId());
        return guardada;
    }

    // BUSCAR POR ID
    public Materia buscarPorId(Long id) {

        logger.info("Buscando materia por id={}", id);

        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Materia {} no encontrada", id);
                    return new RuntimeException("Materia no encontrada");
                });

        logger.debug("Materia encontrada id={}, nombre={}",
                materia.getId(), materia.getNombre());

        return materia;
    }

    // ACTUALIZAR
    public Materia actualizar(Long id, Materia materia) {

        logger.info("Actualizando materia id={}", id);

        Materia materiaExistente = materiaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Materia {} no encontrada para actualizar", id);
                    return new RuntimeException("Materia con id " + id + " no encontrada");
                });

        boolean cambios = false;

        if (materia.getNombre() != null) {
            logger.debug("Actualizando nombre a '{}'", materia.getNombre());
            materiaExistente.setNombre(materia.getNombre());
            cambios = true;
        }

        // creditos es primitivo
        if (materia.getCreditos() > 0) {
            logger.debug("Actualizando créditos a {}", materia.getCreditos());
            materiaExistente.setCreditos(materia.getCreditos());
            cambios = true;
        }

        if (!cambios) {
            logger.warn("No se enviaron cambios para materia id={}", id);
        }

        Materia actualizada = materiaRepository.save(materiaExistente);

        logger.info("Materia actualizada correctamente id={}", actualizada.getId());
        return actualizada;
    }

    // ELIMINAR
    public void eliminarMateria(Long id) {

        logger.warn("Intentando eliminar materia id={}", id);

        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Materia {} no encontrada para eliminar", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Materia no encontrada"
                    );
                });

        if (!materia.getInscripciones().isEmpty()) {
            logger.warn("Materia {} tiene inscripciones asociadas, no se puede eliminar", id);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede eliminar la materia porque tiene inscripciones"
            );
        }

        materiaRepository.delete(materia);

        logger.info("Materia eliminada correctamente id={}", id);
    }
}


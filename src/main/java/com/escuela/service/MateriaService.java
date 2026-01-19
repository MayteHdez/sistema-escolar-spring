package com.escuela.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.escuela.model.Materia;
import com.escuela.repository.MateriaRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public List<Materia> listarTodas() {
        return materiaRepository.findAll();
    }

    public Materia guardar(Materia materia) {
        if (materia.getNombre() == null || materia.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre de la materia es obligatorio");
        }
        return materiaRepository.save(materia);
    }

    public Materia buscarPorId(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
    }
    
    public Materia actualizar(Long id, Materia materia) {

        Materia materiaExistente = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Materia con id " + id + " no encontrada"
                ));

        if (materia.getNombre() != null) {
            materiaExistente.setNombre(materia.getNombre());
        }

        // creditos es int (primitivo)
        if (materia.getCreditos() > 0) {
            materiaExistente.setCreditos(materia.getCreditos());
        }

        // NO se actualiza aquí:
        // profesor (FK)
        // inscripciones (relación)

        return materiaRepository.save(materiaExistente);
    }

    public void eliminarMateria(Long id) {

        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Materia no encontrada"
                ));

        if (!materia.getInscripciones().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede eliminar la materia porque tiene inscripciones"
            );
        }

        materiaRepository.delete(materia);
    }


    
}

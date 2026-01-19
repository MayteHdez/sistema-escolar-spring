package com.escuela.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.escuela.model.Profesor;
import com.escuela.repository.ProfesorRepository;

@Service
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    // Inyección por constructor
    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    // Guardar profesor (con validación básica)
    public Profesor guardar(Profesor profesor) {
        if (profesor.getNombre() == null || profesor.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del profesor es obligatorio");
        }
        return profesorRepository.save(profesor);
    }

    // Listar todos
    public List<Profesor> listar() {
        return profesorRepository.findAll();
    }

    // Buscar por ID
    public Profesor buscarPorId(Long id) {
        return profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
    }
    
    public Profesor actualizar(Long id, Profesor profesor) {

        Profesor profesorExistente = profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Profesor con id " + id + " no encontrado"
                ));

        if (profesor.getNombre() != null) {
            profesorExistente.setNombre(profesor.getNombre());
        }

        if (profesor.getEmail() != null) {
            profesorExistente.setEmail(profesor.getEmail());
        }

        if (profesor.getEspecialidad() != null) {
            profesorExistente.setEspecialidad(profesor.getEspecialidad());
        }

        // NO se actualizan:
        // id
        // materias (relación)

        return profesorRepository.save(profesorExistente);
    }

    
    public void eliminarProfesor(Long id) {
        profesorRepository.deleteById(id);
    }

}


package com.escuela.service;

import java.util.List;
import com.escuela.model.Profesor;

public interface ProfesorService {

    Profesor guardar(Profesor profesor);

    List<Profesor> listar();

    Profesor buscarPorId(Long id);

    Profesor actualizar(Long id, Profesor profesor);

    void eliminarProfesor(Long id);
}


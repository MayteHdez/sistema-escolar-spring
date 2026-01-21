package com.escuela.service;

import com.escuela.model.Alumno;
import java.util.List;

public interface AlumnoService {

    List<Alumno> listarTodos();

    Alumno guardar(Alumno alumno);

    Alumno buscarPorId(Long id);

    void eliminar(Long id);

    Alumno actualizar(Long id, Alumno alumno);
}

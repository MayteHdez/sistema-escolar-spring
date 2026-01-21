package com.escuela.service;

import java.util.List;
import com.escuela.model.Materia;

public interface MateriaService {

    List<Materia> listarTodas();

    Materia guardar(Materia materia);

    Materia buscarPorId(Long id);

    Materia actualizar(Long id, Materia materia);

    void eliminarMateria(Long id);
}

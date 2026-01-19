package com.escuela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.escuela.model.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
}

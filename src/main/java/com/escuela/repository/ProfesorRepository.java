package com.escuela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.escuela.model.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
}

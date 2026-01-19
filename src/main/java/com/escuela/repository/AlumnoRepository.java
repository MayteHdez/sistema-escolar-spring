package com.escuela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.escuela.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}

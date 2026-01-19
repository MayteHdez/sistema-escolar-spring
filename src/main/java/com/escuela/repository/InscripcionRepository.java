package com.escuela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.escuela.model.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}

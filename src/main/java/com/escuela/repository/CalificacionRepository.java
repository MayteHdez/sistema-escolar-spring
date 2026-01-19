package com.escuela.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.escuela.model.Calificacion;
import com.escuela.model.Inscripcion;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    Optional<Calificacion> findByInscripcion(Inscripcion inscripcion);

    boolean existsByInscripcion(Inscripcion inscripcion);
}

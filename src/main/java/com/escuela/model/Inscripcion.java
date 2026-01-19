package com.escuela.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("alumno-inscripcion")
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @JsonBackReference("materia-inscripcion")
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;


    private LocalDate fechaInscripcion;

    // Relación 1 → 1 con Calificacion
    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL)
    @JsonManagedReference("inscripcion-calificacion")
    private Calificacion calificacion;


    // Getters y Setters
    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    }
    
    public Alumno getAlumno() { 
    	return alumno; 
    }
    
    public void setAlumno(Alumno alumno) { 
    	this.alumno = alumno; 
    }
    
    public Materia getMateria() { 
    	return materia; 
    }
    
    public void setMateria(Materia materia) { 
    	this.materia = materia; 
    }
    
    public LocalDate getFechaInscripcion() { 
    	return fechaInscripcion;
    }
    
    public void setFechaInscripcion(LocalDate fechaInscripcion) { 
    	this.fechaInscripcion = fechaInscripcion; 
    }
    
    public Calificacion getCalificacion() { 
    	return calificacion; 
    }
    
    public void setCalificacion(Calificacion calificacion) { 
        this.calificacion = calificacion; 
        if(calificacion != null){
            calificacion.setInscripcion(this); // mantener bidireccionalidad
        }
    }

}


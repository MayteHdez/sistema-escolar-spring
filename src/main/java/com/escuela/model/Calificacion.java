package com.escuela.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "inscripcion_id")
    @JsonBackReference("inscripcion-calificacion")
    private Inscripcion inscripcion;

    private Double calificacion;

    private String observaciones;

    // Getters y Setters
    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    }
    
    public Inscripcion getInscripcion() { 
    	return inscripcion; 
    }
    
    public void setInscripcion(Inscripcion inscripcion) { 
    	this.inscripcion = inscripcion; 
    }
    
    public Double getCalificacion() { 
    	return calificacion; 
    }
    
    public void setCalificacion(Double calificacion) { 
    	this.calificacion = calificacion; 
    }
    
    public String getObservaciones() { 
    	return observaciones; 
    }
    
    public void setObservaciones(String observaciones) { 
    	this.observaciones = observaciones; 
    }

}

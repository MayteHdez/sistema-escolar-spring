package com.escuela.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private int creditos;

    // FK a Profesor
    // @JsonBackReference para manejar error JSON
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    @JsonBackReference("profesor-materia")
    private Profesor profesor;

    // Relación con Inscripcion (Materia 1 → N Inscripcion)
    @JsonManagedReference("materia-inscripcion")
    @OneToMany(mappedBy = "materia")
    private List<Inscripcion> inscripciones;


    // Getters y Setters
    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    }
    
    public String getNombre() { 
    	return nombre; 
    }
    
    public void setNombre(String nombre) { 
    	this.nombre = nombre; 
    }
    
    public int getCreditos() { 
    	return creditos; 
    }
    
    public void setCreditos(int creditos) { 
    	this.creditos = creditos; 
    }
    
    public Profesor getProfesor() { 
    	return profesor; 
    }
    
    public void setProfesor(Profesor profesor) { 
    	this.profesor = profesor; 
    }
    
    public List<Inscripcion> getInscripciones() { 
    	return inscripciones; 
    }
    
    public void setInscripciones(List<Inscripcion> inscripciones) { 
    	this.inscripciones = inscripciones; 
    }

}

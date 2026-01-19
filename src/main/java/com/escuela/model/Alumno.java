package com.escuela.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    @Column(unique = true)
    private String matricula;

    private LocalDate fechaRegistro;

    // Relación con Inscripcion (Alumno N ↔ N Materia)
    @JsonManagedReference("alumno-inscripcion")
    @OneToMany(mappedBy = "alumno")
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
    
    public String getEmail() { 
    	return email; 
    }
    
    public void setEmail(String email) { 
    	this.email = email; 
    }
    
    public String getMatricula() { 
    	return matricula; 
    }
    
    public void setMatricula(String matricula) { 
    	this.matricula = matricula; 
    }
    
    public LocalDate getFechaRegistro() { 
    	return fechaRegistro; 
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) { 
    	this.fechaRegistro = fechaRegistro; 
    }
    
    public List<Inscripcion> getInscripciones() { 
    	return inscripciones; 
    }
    
    public void setInscripciones(List<Inscripcion> inscripciones) { 
    	this.inscripciones = inscripciones; 
    }
    
}

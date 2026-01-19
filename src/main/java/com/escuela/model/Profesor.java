package com.escuela.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "profesor")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String especialidad;

    // Profesor 1 → N Materia
    // @JsonManagedReference para manejar error JSON
    @OneToMany(mappedBy = "profesor")
    @JsonManagedReference("profesor-materia")
    private List<Materia> materias;


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
    
    public String getEspecialidad() { 
    	return especialidad; 
    }
    
    public void setEspecialidad(String especialidad) { 
    	this.especialidad = especialidad; 
    }
    
    public List<Materia> getMaterias() { 
    	return materias; 
    }
    
    public void setMaterias(List<Materia> materias) { 
    	this.materias = materias; 
    }

}

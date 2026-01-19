package com.escuela.service;

import com.escuela.model.Materia;
import com.escuela.repository.MateriaRepository;
import com.escuela.model.Inscripcion;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.web.server.ResponseStatusException;

class MateriaServiceTest {

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private MateriaService materiaService;

    private Materia mat1;
    private Materia mat2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mat1 = new Materia();
        mat1.setId(1L);
        mat1.setNombre("Matemáticas");
        mat1.setCreditos(6);
        mat1.setInscripciones(new ArrayList<>());

        mat2 = new Materia();
        mat2.setId(2L);
        mat2.setNombre("Física");
        mat2.setCreditos(5);
        mat2.setInscripciones(new ArrayList<>());
    }

    // Test listarTodas
    @Test
    void testListarTodas() {
        when(materiaRepository.findAll()).thenReturn(Arrays.asList(mat1, mat2));

        List<Materia> resultado = materiaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(materiaRepository, times(1)).findAll();
    }

    // Test guardar - caso exitoso
    @Test
    void testGuardar() {
        when(materiaRepository.save(mat1)).thenReturn(mat1);

        Materia resultado = materiaService.guardar(mat1);

        assertNotNull(resultado);
        assertEquals("Matemáticas", resultado.getNombre());
        verify(materiaRepository, times(1)).save(mat1);
    }

    // Test guardar - nombre vacío
    @Test
    void testGuardarNombreVacio() {
        Materia mat = new Materia();
        mat.setNombre("");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> materiaService.guardar(mat));

        assertEquals("El nombre de la materia es obligatorio", exception.getMessage());
        verify(materiaRepository, never()).save(any());
    }

    // Test buscarPorId - caso exitoso
    @Test
    void testBuscarPorId() {
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(mat1));

        Materia resultado = materiaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Matemáticas", resultado.getNombre());
        verify(materiaRepository, times(1)).findById(1L);
    }

    // Test buscarPorId - no encontrado
    @Test
    void testBuscarPorIdNoEncontrado() {
        when(materiaRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> materiaService.buscarPorId(3L));

        assertEquals("Materia no encontrada", exception.getMessage());
        verify(materiaRepository, times(1)).findById(3L);
    }

    // Test actualizar
    @Test
    void testActualizar() {
        Materia updated = new Materia();
        updated.setId(1L);
        updated.setNombre("Matemáticas Avanzadas");
        updated.setCreditos(8);

        when(materiaRepository.findById(1L)).thenReturn(Optional.of(mat1));
        when(materiaRepository.save(any(Materia.class))).thenReturn(updated);

        Materia resultado = materiaService.actualizar(1L, updated);

        assertNotNull(resultado);
        assertEquals("Matemáticas Avanzadas", resultado.getNombre());
        assertEquals(8, resultado.getCreditos());
        verify(materiaRepository, times(1)).findById(1L);
        verify(materiaRepository, times(1)).save(any(Materia.class));
    }

    // Test eliminarMateria - caso exitoso
    @Test
    void testEliminarMateria() {
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(mat1));
        doNothing().when(materiaRepository).delete(mat1);

        assertDoesNotThrow(() -> materiaService.eliminarMateria(1L));
        verify(materiaRepository, times(1)).findById(1L);
        verify(materiaRepository, times(1)).delete(mat1);
    }

    // Test eliminarMateria - con inscripciones
    @Test
    void testEliminarMateriaConInscripciones() {
        Inscripcion inscripcionDummy = new Inscripcion(); 
        mat1.getInscripciones().add(inscripcionDummy);

        when(materiaRepository.findById(1L)).thenReturn(Optional.of(mat1));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> materiaService.eliminarMateria(1L));

        assertEquals("No se puede eliminar la materia porque tiene inscripciones", exception.getReason());
        verify(materiaRepository, times(1)).findById(1L);
        verify(materiaRepository, never()).delete(any());
    }


    // Test eliminarMateria - no encontrada
    @Test
    void testEliminarMateriaNoEncontrada() {
        when(materiaRepository.findById(5L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> materiaService.eliminarMateria(5L));

        assertEquals("Materia no encontrada", exception.getReason());
        verify(materiaRepository, times(1)).findById(5L);
        verify(materiaRepository, never()).delete(any());
    }
}


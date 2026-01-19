package com.escuela.controller;

import com.escuela.model.Profesor;
import com.escuela.service.ProfesorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfesorControllerTest {

    @Mock
    private ProfesorService profesorService;

    @InjectMocks
    private ProfesorController profesorController;

    private Profesor prof1;
    private Profesor prof2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        prof1 = new Profesor();
        prof1.setId(1L);
        prof1.setNombre("Carlos Sanchez");

        prof2 = new Profesor();
        prof2.setId(2L);
        prof2.setNombre("Laura Gomez");
    }

    // Test listarTodos
    @Test
    void testListarTodos() {
        when(profesorService.listar()).thenReturn(Arrays.asList(prof1, prof2));

        List<Profesor> resultado = profesorController.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(profesorService, times(1)).listar();
    }

    // Test obtenerPorId - caso exitoso
    @Test
    void testObtenerPorId() {
        when(profesorService.buscarPorId(1L)).thenReturn(prof1);

        Profesor resultado = profesorController.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Carlos Sanchez", resultado.getNombre());
        verify(profesorService, times(1)).buscarPorId(1L);
    }

    // Test obtenerPorId - caso no encontrado
    @Test
    void testObtenerPorIdNoEncontrado() {
        when(profesorService.buscarPorId(3L)).thenThrow(new RuntimeException("Profesor no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> profesorController.obtenerPorId(3L));

        assertEquals("Profesor no encontrado", exception.getMessage());
        verify(profesorService, times(1)).buscarPorId(3L);
    }

    // Test guardar
    @Test
    void testGuardar() {
        when(profesorService.guardar(prof1)).thenReturn(prof1);

        Profesor resultado = profesorController.guardar(prof1);

        assertNotNull(resultado);
        assertEquals("Carlos Sanchez", resultado.getNombre());
        verify(profesorService, times(1)).guardar(prof1);
    }

    // Test actualizar
    @Test
    void testActualizar() {
        Profesor actualizado = new Profesor();
        actualizado.setId(1L);
        actualizado.setNombre("Carlos Actualizado");

        when(profesorService.actualizar(1L, prof1)).thenReturn(actualizado);

        Profesor resultado = profesorController.actualizar(1L, prof1);

        assertNotNull(resultado);
        assertEquals("Carlos Actualizado", resultado.getNombre());
        verify(profesorService, times(1)).actualizar(1L, prof1);
    }

    // Test eliminar
    @Test
    void testEliminar() {
        doNothing().when(profesorService).eliminarProfesor(1L);

        assertDoesNotThrow(() -> profesorController.eliminar(1L));
        verify(profesorService, times(1)).eliminarProfesor(1L);
    }
}

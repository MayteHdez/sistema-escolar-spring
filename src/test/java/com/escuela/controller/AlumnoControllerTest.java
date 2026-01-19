package com.escuela.controller;

import com.escuela.model.Alumno;
import com.escuela.service.AlumnoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlumnoControllerTest {

    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    private Alumno alumno1;
    private Alumno alumno2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear alumnos de ejemplo
        alumno1 = new Alumno();
        alumno1.setId(1L);
        alumno1.setNombre("Juan Perez");

        alumno2 = new Alumno();
        alumno2.setId(2L);
        alumno2.setNombre("Maria Lopez");
    }

    // Test listarTodos
    @Test
    void testListarTodos() {
        when(alumnoService.listarTodos()).thenReturn(Arrays.asList(alumno1, alumno2));

        List<Alumno> resultado = alumnoController.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(alumnoService, times(1)).listarTodos();
    }

    // Test obtenerPorId - caso exitoso
    @Test
    void testObtenerPorId() {
        when(alumnoService.buscarPorId(1L)).thenReturn(alumno1);

        Alumno resultado = alumnoController.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(alumnoService, times(1)).buscarPorId(1L);
    }

    // Test obtenerPorId - caso no encontrado
    @Test
    void testObtenerPorIdNoEncontrado() {
        when(alumnoService.buscarPorId(3L)).thenThrow(new RuntimeException("Alumno no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> alumnoController.obtenerPorId(3L));

        assertEquals("Alumno no encontrado", exception.getMessage());
        verify(alumnoService, times(1)).buscarPorId(3L);
    }

    // Test guardar
    @Test
    void testGuardar() {
        when(alumnoService.guardar(alumno1)).thenReturn(alumno1);

        Alumno resultado = alumnoController.guardar(alumno1);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(alumnoService, times(1)).guardar(alumno1);
    }

    // Test actualizar
    @Test
    void testActualizar() {
        Alumno alumnoActualizado = new Alumno();
        alumnoActualizado.setId(1L);
        alumnoActualizado.setNombre("Juan Actualizado");

        when(alumnoService.actualizar(1L, alumno1)).thenReturn(alumnoActualizado);

        Alumno resultado = alumnoController.actualizar(1L, alumno1);

        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
        verify(alumnoService, times(1)).actualizar(1L, alumno1);
    }

    // Test eliminar
    @Test
    void testEliminar() {
        // No necesitamos devolver nada, solo verificar la llamada
        doNothing().when(alumnoService).eliminar(1L);

        assertDoesNotThrow(() -> alumnoController.eliminar(1L));
        verify(alumnoService, times(1)).eliminar(1L);
    }
}

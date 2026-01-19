package com.escuela.controller;

import com.escuela.model.Inscripcion;
import com.escuela.service.InscripcionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InscripcionControllerTest {

    @Mock
    private InscripcionService inscripcionService;

    @InjectMocks
    private InscripcionController inscripcionController;

    private Inscripcion ins1;
    private Inscripcion ins2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ins1 = new Inscripcion();
        ins1.setId(1L);
        // si quieres, puedes setear alumno y materia dummy

        ins2 = new Inscripcion();
        ins2.setId(2L);
    }

    // Test listarTodas
    @Test
    void testListarTodas() {
        when(inscripcionService.listarInscripciones()).thenReturn(Arrays.asList(ins1, ins2));

        List<Inscripcion> resultado = inscripcionController.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(inscripcionService, times(1)).listarInscripciones();
    }

    // Test obtenerPorId - caso exitoso
    @Test
    void testObtenerPorId() {
        when(inscripcionService.buscarPorId(1L)).thenReturn(ins1);

        Inscripcion resultado = inscripcionController.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(inscripcionService, times(1)).buscarPorId(1L);
    }

    // Test obtenerPorId - caso no encontrado
    @Test
    void testObtenerPorIdNoEncontrado() {
        when(inscripcionService.buscarPorId(3L)).thenThrow(new RuntimeException("Inscripción no encontrada"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> inscripcionController.obtenerPorId(3L));

        assertEquals("Inscripción no encontrada", exception.getMessage());
        verify(inscripcionService, times(1)).buscarPorId(3L);
    }

    // Test inscribirAlumno
    @Test
    void testInscribirAlumno() {
        when(inscripcionService.inscribirAlumno(1L, 2L)).thenReturn(ins1);

        Inscripcion resultado = inscripcionController.inscribirAlumno(1L, 2L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(inscripcionService, times(1)).inscribirAlumno(1L, 2L);
    }

    // Test eliminar
    @Test
    void testEliminar() {
        doNothing().when(inscripcionService).eliminarInscripcion(1L);

        assertDoesNotThrow(() -> inscripcionController.eliminar(1L));
        verify(inscripcionService, times(1)).eliminarInscripcion(1L);
    }
}

package com.escuela.controller;

import com.escuela.model.Calificacion;
import com.escuela.service.CalificacionService;
import com.escuela.dto.CalificacionRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalificacionControllerTest {

    @Mock
    private CalificacionService calificacionService;

    @InjectMocks
    private CalificacionController calificacionController;

    private Calificacion calificacion;
    private CalificacionRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        calificacion = new Calificacion();
        calificacion.setId(1L);
        calificacion.setCalificacion(9.5);
        calificacion.setObservaciones("Muy bien");

        request = new CalificacionRequest();
        request.setValor(9.5);
        request.setObservaciones("Muy bien");
    }

    // Test POST asignarCalificacion
    @Test
    void testAsignarCalificacion() {
        when(calificacionService.asignarCalificacion(1L, 9.5, "Muy bien"))
                .thenReturn(calificacion);

        Calificacion resultado = calificacionController.asignarCalificacion(1L, request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(9.5, resultado.getCalificacion());
        assertEquals("Muy bien", resultado.getObservaciones());

        verify(calificacionService, times(1)).asignarCalificacion(1L, 9.5, "Muy bien");
    }

    // Test GET obtenerPorInscripcion
    @Test
    void testObtenerPorInscripcion() {
        when(calificacionService.buscarPorInscripcion(1L)).thenReturn(calificacion);

        Calificacion resultado = calificacionController.obtenerPorInscripcion(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(calificacionService, times(1)).buscarPorInscripcion(1L);
    }

    // Test PUT actualizar
    @Test
    void testActualizar() {
        when(calificacionService.actualizarCalificacion(1L, 9.5, "Muy bien")).thenReturn(calificacion);

        Calificacion resultado = calificacionController.actualizar(1L, request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(9.5, resultado.getCalificacion());
        assertEquals("Muy bien", resultado.getObservaciones());

        verify(calificacionService, times(1)).actualizarCalificacion(1L, 9.5, "Muy bien");
    }

    // Test DELETE eliminar
    @Test
    void testEliminar() {
        doNothing().when(calificacionService).eliminarCalificacion(1L);

        assertDoesNotThrow(() -> calificacionController.eliminar(1L));

        verify(calificacionService, times(1)).eliminarCalificacion(1L);
    }
}

package com.escuela.service;

import com.escuela.model.Calificacion;
import com.escuela.model.Inscripcion;
import com.escuela.repository.CalificacionRepository;
import com.escuela.repository.InscripcionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalificacionServiceTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @InjectMocks
    private CalificacionService calificacionService;

    private Inscripcion inscripcion;
    private Calificacion calificacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        inscripcion = new Inscripcion();
        inscripcion.setId(1L);

        calificacion = new Calificacion();
        calificacion.setId(1L);
        calificacion.setCalificacion(9.0);
        calificacion.setObservaciones("Muy bien");
        calificacion.setInscripcion(inscripcion);
    }

    // Test asignarCalificacion - éxito
    @Test
    void testAsignarCalificacion() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        when(calificacionRepository.existsByInscripcion(inscripcion)).thenReturn(false);
        when(calificacionRepository.save(any(Calificacion.class))).thenReturn(calificacion);

        Calificacion resultado = calificacionService.asignarCalificacion(1L, 9.0, "Muy bien");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(9.0, resultado.getCalificacion());
        assertEquals("Muy bien", resultado.getObservaciones());

        verify(inscripcionRepository, times(1)).findById(1L);
        verify(calificacionRepository, times(1)).existsByInscripcion(inscripcion);
        verify(calificacionRepository, times(1)).save(any(Calificacion.class));
    }

    // Test asignarCalificacion - inscripcion no existe
    @Test
    void testAsignarCalificacionInscripcionNoExiste() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> calificacionService.asignarCalificacion(1L, 9.0, "Muy bien"));

        assertEquals("Inscripción no encontrada", exception.getMessage());
        verify(calificacionRepository, never()).save(any());
    }

    // Test asignarCalificacion - calificación ya existe
    @Test
    void testAsignarCalificacionYaExiste() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        when(calificacionRepository.existsByInscripcion(inscripcion)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> calificacionService.asignarCalificacion(1L, 9.0, "Muy bien"));

        assertEquals("Esta inscripción ya tiene calificación", exception.getMessage());
        verify(calificacionRepository, never()).save(any());
    }

    // Test asignarCalificacion - valor inválido
    @Test
    void testAsignarCalificacionValorInvalido() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> calificacionService.asignarCalificacion(1L, 12.0, "Muy bien"));

        assertEquals("La calificación debe estar entre 0 y 10", exception.getMessage());
        verify(calificacionRepository, never()).save(any());
        verify(inscripcionRepository, never()).findById(anyLong());
    }

    // Test buscarPorInscripcion - éxito
    @Test
    void testBuscarPorInscripcion() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        when(calificacionRepository.findByInscripcion(inscripcion)).thenReturn(Optional.of(calificacion));

        Calificacion resultado = calificacionService.buscarPorInscripcion(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(inscripcionRepository, times(1)).findById(1L);
        verify(calificacionRepository, times(1)).findByInscripcion(inscripcion);
    }

    // Test buscarPorInscripcion - no hay calificación
    @Test
    void testBuscarPorInscripcionNoExiste() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        when(calificacionRepository.findByInscripcion(inscripcion)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> calificacionService.buscarPorInscripcion(1L));

        assertEquals("No hay calificación registrada", exception.getMessage());
    }

    // Test actualizarCalificacion - éxito
    @Test
    void testActualizarCalificacion() {
        when(calificacionRepository.findById(1L)).thenReturn(Optional.of(calificacion));
        when(calificacionRepository.save(calificacion)).thenReturn(calificacion);

        Calificacion resultado = calificacionService.actualizarCalificacion(1L, 10.0, "Excelente");

        assertEquals(10.0, resultado.getCalificacion());
        assertEquals("Excelente", resultado.getObservaciones());
        verify(calificacionRepository, times(1)).save(calificacion);
    }

    // Test actualizarCalificacion - valor inválido
    @Test
    void testActualizarCalificacionValorInvalido() {
        when(calificacionRepository.findById(1L)).thenReturn(Optional.of(calificacion));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> calificacionService.actualizarCalificacion(1L, 12.0, null));

        assertEquals("La calificación debe estar entre 0 y 10", exception.getMessage());
        verify(calificacionRepository, never()).save(any());
    }

    // Test eliminarCalificacion - éxito
    @Test
    void testEliminarCalificacion() {
        when(calificacionRepository.findById(1L)).thenReturn(Optional.of(calificacion));
        doNothing().when(calificacionRepository).delete(calificacion);

        assertDoesNotThrow(() -> calificacionService.eliminarCalificacion(1L));
        verify(calificacionRepository, times(1)).delete(calificacion);
        assertNull(calificacion.getInscripcion().getCalificacion());
    }

    // Test eliminarCalificacion - no encontrada
    @Test
    void testEliminarCalificacionNoExiste() {
        when(calificacionRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> calificacionService.eliminarCalificacion(1L));

        assertEquals("Calificación no encontrada", exception.getMessage());
        verify(calificacionRepository, never()).delete(any());
    }
}

package com.escuela.service;

import com.escuela.model.Alumno;
import com.escuela.model.Materia;
import com.escuela.model.Inscripcion;
import com.escuela.repository.AlumnoRepository;
import com.escuela.repository.MateriaRepository;
import com.escuela.repository.InscripcionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    private Alumno alumno;
    private Materia materia;
    private Inscripcion inscripcion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Juan");

        materia = new Materia();
        materia.setId(2L);
        materia.setNombre("Matemáticas");

        inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setAlumno(alumno);
        inscripcion.setMateria(materia);
    }

    // Test listarInscripciones
    @Test
    void testListarInscripciones() {
        when(inscripcionRepository.findAll()).thenReturn(Arrays.asList(inscripcion));

        List<Inscripcion> resultado = inscripcionService.listarInscripciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(inscripcionRepository, times(1)).findAll();
    }

    // Test buscarPorId - caso exitoso
    @Test
    void testBuscarPorId() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));

        Inscripcion resultado = inscripcionService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(inscripcionRepository, times(1)).findById(1L);
    }

    // Test buscarPorId - no encontrado
    @Test
    void testBuscarPorIdNoEncontrado() {
        when(inscripcionRepository.findById(3L)).thenReturn(Optional.empty());

        Inscripcion resultado = inscripcionService.buscarPorId(3L);

        assertNull(resultado);
        verify(inscripcionRepository, times(1)).findById(3L);
    }

    // Test inscribirAlumno - caso exitoso
    @Test
    void testInscribirAlumno() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(materiaRepository.findById(2L)).thenReturn(Optional.of(materia));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcion);

        Inscripcion resultado = inscripcionService.inscribirAlumno(1L, 2L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(alumno, resultado.getAlumno());
        assertEquals(materia, resultado.getMateria());

        verify(alumnoRepository, times(1)).findById(1L);
        verify(materiaRepository, times(1)).findById(2L);
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }

    // Test inscribirAlumno - alumno no existe
    @Test
    void testInscribirAlumnoAlumnoNoExiste() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> inscripcionService.inscribirAlumno(1L, 2L));

        assertEquals("Alumno no existe", exception.getMessage());
        verify(alumnoRepository, times(1)).findById(1L);
        verify(materiaRepository, never()).findById(anyLong());
        verify(inscripcionRepository, never()).save(any());
    }

    // Test inscribirAlumno - materia no existe
    @Test
    void testInscribirAlumnoMateriaNoExiste() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(materiaRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> inscripcionService.inscribirAlumno(1L, 2L));

        assertEquals("Materia no existe", exception.getMessage());
        verify(alumnoRepository, times(1)).findById(1L);
        verify(materiaRepository, times(1)).findById(2L);
        verify(inscripcionRepository, never()).save(any());
    }

    // Test eliminarInscripcion
    @Test
    void testEliminarInscripcion() {
        doNothing().when(inscripcionRepository).deleteById(1L);

        assertDoesNotThrow(() -> inscripcionService.eliminarInscripcion(1L));
        verify(inscripcionRepository, times(1)).deleteById(1L);
    }
}

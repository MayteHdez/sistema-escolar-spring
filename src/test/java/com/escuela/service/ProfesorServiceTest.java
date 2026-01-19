package com.escuela.service;

import com.escuela.model.Profesor;
import com.escuela.repository.ProfesorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorService profesorService;

    private Profesor prof1;
    private Profesor prof2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        prof1 = new Profesor();
        prof1.setId(1L);
        prof1.setNombre("Carlos Sanchez");
        prof1.setEmail("carlos@example.com");
        prof1.setEspecialidad("Matemáticas");

        prof2 = new Profesor();
        prof2.setId(2L);
        prof2.setNombre("Laura Gomez");
        prof2.setEmail("laura@example.com");
        prof2.setEspecialidad("Física");
    }

    // Test listar todos los profesores
    @Test
    void testListar() {
        when(profesorRepository.findAll()).thenReturn(Arrays.asList(prof1, prof2));

        List<Profesor> resultado = profesorService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(profesorRepository, times(1)).findAll();
    }

    // Test guardar profesor exitoso
    @Test
    void testGuardar() {
        when(profesorRepository.save(prof1)).thenReturn(prof1);

        Profesor resultado = profesorService.guardar(prof1);

        assertNotNull(resultado);
        assertEquals("Carlos Sanchez", resultado.getNombre());
        verify(profesorRepository, times(1)).save(prof1);
    }

    // Test guardar profesor sin nombre
    @Test
    void testGuardarSinNombre() {
        Profesor profesorSinNombre = new Profesor();
        profesorSinNombre.setEmail("sin@example.com");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> profesorService.guardar(profesorSinNombre));

        assertEquals("El nombre del profesor es obligatorio", exception.getMessage());
        verify(profesorRepository, never()).save(any());
    }

    // Test buscar por ID exitoso
    @Test
    void testBuscarPorId() {
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(prof1));

        Profesor resultado = profesorService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Carlos Sanchez", resultado.getNombre());
        verify(profesorRepository, times(1)).findById(1L);
    }

    // Test buscar por ID no encontrado
    @Test
    void testBuscarPorIdNoEncontrado() {
        when(profesorRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> profesorService.buscarPorId(3L));

        assertEquals("Profesor no encontrado", exception.getMessage());
        verify(profesorRepository, times(1)).findById(3L);
    }

    // Test actualizar profesor exitoso
    @Test
    void testActualizar() {
        Profesor datosActualizados = new Profesor();
        datosActualizados.setNombre("Carlos Actualizado");
        datosActualizados.setEmail("carlos.actualizado@example.com");
        datosActualizados.setEspecialidad("Química");

        when(profesorRepository.findById(1L)).thenReturn(Optional.of(prof1));
        when(profesorRepository.save(any(Profesor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Profesor resultado = profesorService.actualizar(1L, datosActualizados);

        assertNotNull(resultado);
        assertEquals("Carlos Actualizado", resultado.getNombre());
        assertEquals("carlos.actualizado@example.com", resultado.getEmail());
        assertEquals("Química", resultado.getEspecialidad());
        verify(profesorRepository, times(1)).findById(1L);
        verify(profesorRepository, times(1)).save(any(Profesor.class));
    }

    // Test actualizar profesor no encontrado
    @Test
    void testActualizarNoEncontrado() {
        Profesor datosActualizados = new Profesor();
        datosActualizados.setNombre("No Existe");

        when(profesorRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> profesorService.actualizar(3L, datosActualizados));

        assertEquals("Profesor con id 3 no encontrado", exception.getMessage());
        verify(profesorRepository, times(1)).findById(3L);
        verify(profesorRepository, never()).save(any());
    }

    // Test eliminar profesor
    @Test
    void testEliminarProfesor() {
        doNothing().when(profesorRepository).deleteById(1L);

        assertDoesNotThrow(() -> profesorService.eliminarProfesor(1L));
        verify(profesorRepository, times(1)).deleteById(1L);
    }
}


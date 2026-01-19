package com.escuela.service;

import com.escuela.model.Alumno;
import com.escuela.repository.AlumnoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno alumno1;
    private Alumno alumno2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        alumno1 = new Alumno();
        alumno1.setId(1L);
        alumno1.setNombre("Juan Perez");
        alumno1.setEmail("juan@example.com");

        alumno2 = new Alumno();
        alumno2.setId(2L);
        alumno2.setNombre("Maria Lopez");
        alumno2.setEmail("maria@example.com");
    }

    // Test listarTodos
    @Test
    void testListarTodos() {
        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(alumno1, alumno2));

        List<Alumno> resultado = alumnoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(alumnoRepository, times(1)).findAll();
    }

    // Test guardar alumno exitoso
    @Test
    void testGuardar() {
        when(alumnoRepository.save(alumno1)).thenReturn(alumno1);

        Alumno resultado = alumnoService.guardar(alumno1);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(alumnoRepository, times(1)).save(alumno1);
    }

    // Test guardar alumno sin nombre
    @Test
    void testGuardarSinNombre() {
        Alumno alumnoSinNombre = new Alumno();
        alumnoSinNombre.setEmail("sinnombre@example.com");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> alumnoService.guardar(alumnoSinNombre));

        assertEquals("El nombre del alumno es obligatorio", exception.getMessage());
        verify(alumnoRepository, never()).save(any());
    }

    // Test buscarPorId exitoso
    @Test
    void testBuscarPorId() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno1));

        Alumno resultado = alumnoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(alumnoRepository, times(1)).findById(1L);
    }

    // Test buscarPorId no encontrado
    @Test
    void testBuscarPorIdNoEncontrado() {
        when(alumnoRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> alumnoService.buscarPorId(3L));

        assertEquals("Alumno no encontrado", exception.getMessage());
        verify(alumnoRepository, times(1)).findById(3L);
    }

    // Test eliminar
    @Test
    void testEliminar() {
        doNothing().when(alumnoRepository).deleteById(1L);

        assertDoesNotThrow(() -> alumnoService.eliminar(1L));
        verify(alumnoRepository, times(1)).deleteById(1L);
    }

    // Test actualizar exitoso
    @Test
    void testActualizar() {
        Alumno datosActualizados = new Alumno();
        datosActualizados.setNombre("Juan Actualizado");
        datosActualizados.setEmail("juan.actualizado@example.com");

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno1));
        when(alumnoRepository.save(any(Alumno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Alumno resultado = alumnoService.actualizar(1L, datosActualizados);

        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals("juan.actualizado@example.com", resultado.getEmail());
        verify(alumnoRepository, times(1)).findById(1L);
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }

    // Test actualizar alumno no encontrado
    @Test
    void testActualizarNoEncontrado() {
        Alumno datosActualizados = new Alumno();
        datosActualizados.setNombre("No Existe");

        when(alumnoRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> alumnoService.actualizar(3L, datosActualizados));

        assertEquals("Alumno no encontrado", exception.getMessage());
        verify(alumnoRepository, times(1)).findById(3L);
        verify(alumnoRepository, never()).save(any());
    }
}


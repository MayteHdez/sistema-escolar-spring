package com.escuela.controller;

import com.escuela.model.Materia;
import com.escuela.service.MateriaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MateriaControllerTest {

    @Mock
    private MateriaService materiaService;

    @InjectMocks
    private MateriaController materiaController;

    private Materia mat1;
    private Materia mat2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mat1 = new Materia();
        mat1.setId(1L);
        mat1.setNombre("Matemáticas");

        mat2 = new Materia();
        mat2.setId(2L);
        mat2.setNombre("Física");
    }

    // Test listarTodas
    @Test
    void testListarTodas() {
        when(materiaService.listarTodas()).thenReturn(Arrays.asList(mat1, mat2));

        List<Materia> resultado = materiaController.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(materiaService, times(1)).listarTodas();
    }

    // Test obtenerPorId - caso exitoso
    @Test
    void testObtenerPorId() {
        when(materiaService.buscarPorId(1L)).thenReturn(mat1);

        Materia resultado = materiaController.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Matemáticas", resultado.getNombre());
        verify(materiaService, times(1)).buscarPorId(1L);
    }

    // Test obtenerPorId - caso no encontrado
    @Test
    void testObtenerPorIdNoEncontrado() {
        when(materiaService.buscarPorId(3L)).thenThrow(new RuntimeException("Materia no encontrada"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> materiaController.obtenerPorId(3L));

        assertEquals("Materia no encontrada", exception.getMessage());
        verify(materiaService, times(1)).buscarPorId(3L);
    }

    // Test guardar
    @Test
    void testGuardar() {
        when(materiaService.guardar(mat1)).thenReturn(mat1);

        Materia resultado = materiaController.guardar(mat1);

        assertNotNull(resultado);
        assertEquals("Matemáticas", resultado.getNombre());
        verify(materiaService, times(1)).guardar(mat1);
    }

    // Test actualizar
    @Test
    void testActualizar() {
        Materia actualizado = new Materia();
        actualizado.setId(1L);
        actualizado.setNombre("Matemáticas Avanzadas");

        when(materiaService.actualizar(1L, mat1)).thenReturn(actualizado);

        Materia resultado = materiaController.actualizar(1L, mat1);

        assertNotNull(resultado);
        assertEquals("Matemáticas Avanzadas", resultado.getNombre());
        verify(materiaService, times(1)).actualizar(1L, mat1);
    }

    // Test eliminar
    @Test
    void testEliminar() {
        doNothing().when(materiaService).eliminarMateria(1L);

        assertDoesNotThrow(() -> materiaController.eliminar(1L));
        verify(materiaService, times(1)).eliminarMateria(1L);
    }
}

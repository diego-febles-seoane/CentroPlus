package es.ies.puerto.service;

import es.ies.puerto.entity.Incidencia;
import es.ies.puerto.repository.IncidenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenciaServiceTest {

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @InjectMocks
    private IncidenciaService incidenciaService;

    private Incidencia incidencia;

    @BeforeEach
    void setUp() {
        incidencia = new Incidencia(1, 1, "Problema con reserva", "No puedo reservar una plaza", "2025-01-12", "ABIERTA");
    }

    @Test
    void testFindAll() {
        List<Incidencia> incidencias = Arrays.asList(
                incidencia,
                new Incidencia(2, 2, "Cambio de horario", "El horario no coincide", "2025-01-13", "EN_PROCESO")
        );

        when(incidenciaRepository.findAll()).thenReturn(incidencias);

        List<Incidencia> result = incidenciaService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(incidenciaRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(incidenciaRepository.findById(1)).thenReturn(Optional.of(incidencia));

        Optional<Incidencia> result = incidenciaService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Problema con reserva", result.get().getAsunto());
        verify(incidenciaRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        when(incidenciaRepository.save(any(Incidencia.class))).thenReturn(incidencia);

        Incidencia result = incidenciaService.save(incidencia);

        assertNotNull(result);
        assertEquals("ABIERTA", result.getEstado());
        verify(incidenciaRepository, times(1)).save(any(Incidencia.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(incidenciaRepository).deleteById(1);

        incidenciaService.deleteById(1);

        verify(incidenciaRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdate() {
        Incidencia incidenciaActualizada = new Incidencia(1, 1, "Problema con reserva", "Resuelto", "2025-01-12", "CERRADA");

        when(incidenciaRepository.findById(1)).thenReturn(Optional.of(incidencia));
        when(incidenciaRepository.save(any(Incidencia.class))).thenReturn(incidenciaActualizada);

        Incidencia result = incidenciaService.update(1, incidenciaActualizada);

        assertNotNull(result);
        assertEquals("CERRADA", result.getEstado());
    }
}

package es.ies.puerto.service;

import es.ies.puerto.entity.Actividad;
import es.ies.puerto.repository.ActividadRepository;
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
class ActividadServiceTest {

    @Mock
    private ActividadRepository actividadRepository;

    @InjectMocks
    private ActividadService actividadService;

    private Actividad actividad;

    @BeforeEach
    void setUp() {
        actividad = new Actividad(1, "Yoga", "DEPORTIVA", 60, 25.50, 15, 8);
    }

    @Test
    void testFindAll() {
        List<Actividad> actividades = Arrays.asList(
                actividad,
                new Actividad(2, "Programación Java", "ACADEMICA", 90, 40.00, 20, 12)
        );

        when(actividadRepository.findAll()).thenReturn(actividades);

        List<Actividad> result = actividadService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(actividadRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(actividadRepository.findById(1)).thenReturn(Optional.of(actividad));

        Optional<Actividad> result = actividadService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Yoga", result.get().getNombre());
        verify(actividadRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        when(actividadRepository.save(any(Actividad.class))).thenReturn(actividad);

        Actividad result = actividadService.save(actividad);

        assertNotNull(result);
        assertEquals("Yoga", result.getNombre());
        verify(actividadRepository, times(1)).save(any(Actividad.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(actividadRepository).deleteById(1);

        actividadService.deleteById(1);

        verify(actividadRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdate() {
        Actividad actividadActualizada = new Actividad(1, "Yoga Avanzado", "DEPORTIVA", 90, 30.00, 10, 5);

        when(actividadRepository.findById(1)).thenReturn(Optional.of(actividad));
        when(actividadRepository.save(any(Actividad.class))).thenReturn(actividadActualizada);

        Actividad result = actividadService.update(1, actividadActualizada);

        assertNotNull(result);
        assertEquals("Yoga Avanzado", result.getNombre());
        assertEquals(90, result.getDuracion());
    }
}

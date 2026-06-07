package es.ies.puerto.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.repository.IActividadesRepository;
import es.ies.puerto.service.sqlite.ActividadesService;

@ExtendWith(MockitoExtension.class)
public class ActividadesServiceTest {

    @Mock
    private IActividadesRepository actividadesRepository;

    @InjectMocks
    private ActividadesService actividadesService;

    private Actividades actividad;

    @BeforeEach
    public void setUp() {
        actividad = new Actividades(999, "Prueba Actividad", "Test", 60, 25.0, 20, 5);
    }

    @Test
    public void constructorDefaultTest() {
        ActividadesService service = new ActividadesService();
        assertNotNull(service);
    }

    @Test
    public void findAllTestOk() {
        List<Actividades> actividades = new ArrayList<>();
        actividades.add(actividad);
        when(actividadesRepository.findAll()).thenReturn(actividades);

        List<Actividades> resultado = actividadesService.findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void findByIdTestOk() {
        when(actividadesRepository.findById(999)).thenReturn(actividad);

        Actividades encontrada = actividadesService.findById(999);
        assertNotNull(encontrada);
        assertEquals("Prueba Actividad", encontrada.getNombre());
    }

    @Test
    public void findByIdTestNotFound() {
        when(actividadesRepository.findById(-1)).thenReturn(null);

        Actividades resultado = actividadesService.findById(-1);
        assertNull(resultado);
    }

    @Test
    public void saveTestOk() {
        when(actividadesRepository.save(actividad)).thenReturn(true);

        boolean guardado = actividadesService.save(actividad);
        assertTrue(guardado);
    }

    @Test
    public void saveTestNull() {
        assertFalse(actividadesService.save(null));
    }

    @Test
    public void updateTestOk() {
        when(actividadesRepository.update(actividad)).thenReturn(true);

        boolean actualizado = actividadesService.update(actividad);
        assertTrue(actualizado);
    }

    @Test
    public void updateTestNull() {
        assertFalse(actividadesService.update(null));
    }

    @Test
    public void deleteTestOk() {
        when(actividadesRepository.delete(999)).thenReturn(true);

        boolean eliminado = actividadesService.delete(999);
        assertTrue(eliminado);
    }

    @Test
    public void deleteTestNotFound() {
        when(actividadesRepository.delete(-1)).thenReturn(false);

        boolean eliminado = actividadesService.delete(-1);
        assertFalse(eliminado);
    }
}

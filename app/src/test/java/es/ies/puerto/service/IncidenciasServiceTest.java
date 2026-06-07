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

import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.repository.IIncidenciasRepository;
import es.ies.puerto.service.sqlite.IncidenciasService;

@ExtendWith(MockitoExtension.class)
public class IncidenciasServiceTest {

    @Mock
    private IIncidenciasRepository incidenciasRepository;

    @InjectMocks
    private IncidenciasService incidenciasService;

    private Incidencias incidencia;

    @BeforeEach
    public void setUp() {
        incidencia = new Incidencias(999, 1, "Prueba Asunto", "Prueba Descripción", "2026-12-31", "Abierta");
    }

    @Test
    public void constructorDefaultTest() {
        IncidenciasService service = new IncidenciasService();
        assertNotNull(service);
    }

    @Test
    public void findAllTestOk() {
        List<Incidencias> incidencias = new ArrayList<>();
        incidencias.add(incidencia);
        when(incidenciasRepository.findAll()).thenReturn(incidencias);

        List<Incidencias> resultado = incidenciasService.findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void findByIdTestOk() {
        when(incidenciasRepository.findById(999)).thenReturn(incidencia);

        Incidencias encontrada = incidenciasService.findById(999);
        assertNotNull(encontrada);
        assertEquals("Prueba Asunto", encontrada.getAsunto());
    }

    @Test
    public void findByIdTestNotFound() {
        when(incidenciasRepository.findById(-1)).thenReturn(null);

        Incidencias resultado = incidenciasService.findById(-1);
        assertNull(resultado);
    }

    @Test
    public void saveTestOk() {
        when(incidenciasRepository.save(incidencia)).thenReturn(true);

        boolean guardado = incidenciasService.save(incidencia);
        assertTrue(guardado);
    }

    @Test
    public void saveTestNull() {
        assertFalse(incidenciasService.save(null));
    }

    @Test
    public void updateTestOk() {
        when(incidenciasRepository.update(incidencia)).thenReturn(true);

        boolean actualizado = incidenciasService.update(incidencia);
        assertTrue(actualizado);
    }

    @Test
    public void updateTestNull() {
        assertFalse(incidenciasService.update(null));
    }

    @Test
    public void deleteTestOk() {
        when(incidenciasRepository.delete(999)).thenReturn(true);

        boolean eliminado = incidenciasService.delete(999);
        assertTrue(eliminado);
    }

    @Test
    public void deleteTestNotFound() {
        when(incidenciasRepository.delete(-1)).thenReturn(false);

        boolean eliminado = incidenciasService.delete(-1);
        assertFalse(eliminado);
    }
}

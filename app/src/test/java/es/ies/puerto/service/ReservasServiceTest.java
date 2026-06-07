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

import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.repository.IReservasRepository;
import es.ies.puerto.service.sqlite.ReservasService;

@ExtendWith(MockitoExtension.class)
public class ReservasServiceTest {

    @Mock
    private IReservasRepository reservasRepository;

    @InjectMocks
    private ReservasService reservasService;

    private Reservas reserva;

    @BeforeEach
    public void setUp() {
        reserva = new Reservas(999, 1, 1, "2026-12-31", "Prueba");
    }

    @Test
    public void constructorDefaultTest() {
        ReservasService service = new ReservasService();
        assertNotNull(service);
    }

    @Test
    public void findAllTestOk() {
        List<Reservas> reservas = new ArrayList<>();
        reservas.add(reserva);
        when(reservasRepository.findAll()).thenReturn(reservas);

        List<Reservas> resultado = reservasService.findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void findByIdTestOk() {
        when(reservasRepository.findById(999)).thenReturn(reserva);

        Reservas encontrada = reservasService.findById(999);
        assertNotNull(encontrada);
        assertEquals("Prueba", encontrada.getEstado());
    }

    @Test
    public void findByIdTestNotFound() {
        when(reservasRepository.findById(-1)).thenReturn(null);

        Reservas resultado = reservasService.findById(-1);
        assertNull(resultado);
    }

    @Test
    public void saveTestOk() {
        when(reservasRepository.save(reserva)).thenReturn(true);

        boolean guardado = reservasService.save(reserva);
        assertTrue(guardado);
    }

    @Test
    public void saveTestNull() {
        assertFalse(reservasService.save(null));
    }

    @Test
    public void updateTestOk() {
        when(reservasRepository.update(reserva)).thenReturn(true);

        boolean actualizado = reservasService.update(reserva);
        assertTrue(actualizado);
    }

    @Test
    public void updateTestNull() {
        assertFalse(reservasService.update(null));
    }

    @Test
    public void deleteTestOk() {
        when(reservasRepository.delete(999)).thenReturn(true);

        boolean eliminado = reservasService.delete(999);
        assertTrue(eliminado);
    }

    @Test
    public void deleteTestNotFound() {
        when(reservasRepository.delete(-1)).thenReturn(false);

        boolean eliminado = reservasService.delete(-1);
        assertFalse(eliminado);
    }
}

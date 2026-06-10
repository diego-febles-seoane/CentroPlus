package es.ies.puerto.service;

import es.ies.puerto.entity.Reserva;
import es.ies.puerto.repository.ReservaRepository;
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
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva(1, 1, 1, "2025-01-10", "ACTIVA");
    }

    @Test
    void testFindAll() {
        List<Reserva> reservas = Arrays.asList(
                reserva,
                new Reserva(2, 2, 2, "2025-01-11", "ACTIVA")
        );

        when(reservaRepository.findAll()).thenReturn(reservas);

        List<Reserva> result = reservaService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));

        Optional<Reserva> result = reservaService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("ACTIVA", result.get().getEstado());
        verify(reservaRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva result = reservaService.save(reserva);

        assertNotNull(result);
        assertEquals("ACTIVA", result.getEstado());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(reservaRepository).deleteById(1);

        reservaService.deleteById(1);

        verify(reservaRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdate() {
        Reserva reservaActualizada = new Reserva(1, 1, 1, "2025-01-15", "CANCELADA");

        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaActualizada);

        Reserva result = reservaService.update(1, reservaActualizada);

        assertNotNull(result);
        assertEquals("CANCELADA", result.getEstado());
    }
}

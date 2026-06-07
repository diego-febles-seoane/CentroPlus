package es.ies.puerto.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.service.sqlite.ReservasService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservasServiceTest {

    private static ReservasService reservasService;

    @BeforeAll
    public static void setUp() {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        reservasService = new ReservasService();
    }

    @Test
    @Order(1)
    public void testFindAll() {
        List<Reservas> reservas = reservasService.findAll();
        assertNotNull(reservas);
        System.out.println("Número de reservas encontradas: " + reservas.size());
    }

    @Test
    @Order(2)
    public void testSaveFindById() {
        Reservas reserva = new Reservas(999, 1, 1, "2026-12-31", "Prueba");
        boolean guardado = reservasService.save(reserva);
        assertTrue(guardado);

        Reservas encontrada = reservasService.findById(999);
        assertNotNull(encontrada);
        assertEquals("Prueba", encontrada.getEstado());
    }

    @Test
    @Order(3)
    public void testUpdate() {
        Reservas reserva = reservasService.findById(999);
        reserva.setEstado("Actualizada");
        boolean actualizado = reservasService.update(reserva);
        assertTrue(actualizado);

        Reservas actualizada = reservasService.findById(999);
        assertEquals("Actualizada", actualizada.getEstado());
    }

    @Test
    @Order(4)
    public void testDelete() {
        boolean eliminado = reservasService.delete(999);
        assertTrue(eliminado);

        Reservas eliminada = reservasService.findById(999);
        assertNull(eliminada);
    }
}

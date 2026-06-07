package es.ies.puerto.repository.sqlite;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.modelo.Actividades;

public class ReservasRepositoryTest {

    private static ReservasRepository repository;
    private static UsuarioRepository usuarioRepository;
    private static ActividadesRepository actividadesRepository;

    @BeforeAll
    public static void setUpAll() {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        repository = new ReservasRepository();
        usuarioRepository = new UsuarioRepository();
        actividadesRepository = new ActividadesRepository();
        
        // Asegurar dependencias
        if (usuarioRepository.findById(1) == null) {
            usuarioRepository.save(new Usuario(1, "Usuario Base", "11111111A", "base@test.com", "600000001", "Alumno"));
        }
        if (actividadesRepository.findById(1) == null) {
            actividadesRepository.save(new Actividades(1, "Actividad Base", "Tipo", 60, 10.0, 10, 0));
        }
    }

    @BeforeEach
    public void setUp() {
        repository.delete(999);
    }

    @Test
    public void findAllTestOk() {
        List<Reservas> reservas = repository.findAll();
        assertNotNull(reservas);
    }

    @Test
    public void saveAndFindByIdTestOk() {
        Reservas reserva = new Reservas(999, 1, 1, "2026-01-01", "Confirmada");
        assertTrue(repository.save(reserva));

        Reservas encontrada = repository.findById(999);
        assertNotNull(encontrada);
        assertEquals("Confirmada", encontrada.getEstado());
    }

    @Test
    public void updateTestOk() {
        Reservas reserva = new Reservas(999, 1, 1, "2026-01-01", "Confirmada");
        repository.save(reserva);

        reserva.setEstado("Cancelada");
        assertTrue(repository.update(reserva));

        Reservas encontrada = repository.findById(999);
        assertEquals("Cancelada", encontrada.getEstado());
    }

    @Test
    public void deleteTestOk() {
        Reservas reserva = new Reservas(999, 1, 1, "2026-01-01", "Confirmada");
        repository.save(reserva);

        assertTrue(repository.delete(999));
        assertNull(repository.findById(999));
    }

    @Test
    public void findByIdTestNotFound() {
        assertNull(repository.findById(-1));
    }

    @Test
    public void deleteTestNotFound() {
        assertFalse(repository.delete(-1));
    }
}

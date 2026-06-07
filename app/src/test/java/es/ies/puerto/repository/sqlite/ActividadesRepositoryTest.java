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
import es.ies.puerto.modelo.Actividades;

public class ActividadesRepositoryTest {

    private static ActividadesRepository repository;

    @BeforeAll
    public static void setUpAll() {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        repository = new ActividadesRepository();
    }

    @BeforeEach
    public void setUp() {
        repository.delete(999);
    }

    @Test
    public void findAllTestOk() {
        List<Actividades> actividades = repository.findAll();
        assertNotNull(actividades);
    }

    @Test
    public void saveAndFindByIdTestOk() {
        Actividades actividad = new Actividades(999, "Actividad Repo", "Deporte", 60, 10.0, 20, 0);
        assertTrue(repository.save(actividad));

        Actividades encontrada = repository.findById(999);
        assertNotNull(encontrada);
        assertEquals("Actividad Repo", encontrada.getNombre());
    }

    @Test
    public void updateTestOk() {
        Actividades actividad = new Actividades(999, "Actividad Repo", "Deporte", 60, 10.0, 20, 0);
        repository.save(actividad);

        actividad.setNombre("Nombre Modificado");
        assertTrue(repository.update(actividad));

        Actividades encontrada = repository.findById(999);
        assertEquals("Nombre Modificado", encontrada.getNombre());
    }

    @Test
    public void deleteTestOk() {
        Actividades actividad = new Actividades(999, "Actividad Repo", "Deporte", 60, 10.0, 20, 0);
        repository.save(actividad);

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

package es.ies.puerto.repository.sqlite;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Usuario;

public class RepositoryErrorTest {

    @Test
    public void usuarioRepositorySaveErrorTest() {
        try (MockedStatic<SQLiteConnectionManager> mocked = mockStatic(SQLiteConnectionManager.class)) {
            mocked.when(SQLiteConnectionManager::getConnection).thenThrow(new SQLException("Error de conexión"));

            UsuarioRepository repository = new UsuarioRepository();
            Usuario usuario = new Usuario(1, "Test", "12345678A", "test@test.com", "600000000", "Alumno");

            assertFalse(repository.save(usuario));
            assertFalse(repository.update(usuario));
            assertNull(repository.findById(1));
            assertNotNull(repository.findAll());
            assertTrue(repository.findAll().isEmpty());
            assertFalse(repository.delete(1));

            // Probar bloques catch específicos de ResultSet si es posible forzando error en el statement
            mocked.when(SQLiteConnectionManager::getConnection).thenThrow(new RuntimeException("Error inesperado"));
            assertNull(repository.findById(1));
            assertTrue(repository.findAll().isEmpty());
        }
    }

    @Test
    public void actividadesRepositoryErrorTest() {
        try (MockedStatic<SQLiteConnectionManager> mocked = mockStatic(SQLiteConnectionManager.class)) {
            mocked.when(SQLiteConnectionManager::getConnection).thenThrow(new SQLException("Error de conexión"));

            ActividadesRepository repository = new ActividadesRepository();
            assertFalse(repository.save(null));
            assertFalse(repository.update(null));
            assertNull(repository.findById(1));
            assertTrue(repository.findAll().isEmpty());
            assertFalse(repository.delete(1));
        }
    }

    @Test
    public void incidenciasRepositoryErrorTest() {
        try (MockedStatic<SQLiteConnectionManager> mocked = mockStatic(SQLiteConnectionManager.class)) {
            mocked.when(SQLiteConnectionManager::getConnection).thenThrow(new SQLException("Error de conexión"));

            IncidenciasRepository repository = new IncidenciasRepository();
            assertFalse(repository.save(null));
            assertFalse(repository.update(null));
            assertNull(repository.findById(1));
            assertTrue(repository.findAll().isEmpty());
            assertFalse(repository.delete(1));
        }
    }

    @Test
    public void reservasRepositoryErrorTest() {
        try (MockedStatic<SQLiteConnectionManager> mocked = mockStatic(SQLiteConnectionManager.class)) {
            mocked.when(SQLiteConnectionManager::getConnection).thenThrow(new SQLException("Error de conexión"));

            ReservasRepository repository = new ReservasRepository();
            assertFalse(repository.save(null));
            assertFalse(repository.update(null));
            assertNull(repository.findById(1));
            assertTrue(repository.findAll().isEmpty());
            assertFalse(repository.delete(1));
        }
    }
}

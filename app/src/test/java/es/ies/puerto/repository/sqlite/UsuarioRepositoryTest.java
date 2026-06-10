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
import es.ies.puerto.modelo.Usuario;

public class UsuarioRepositoryTest {

    private static UsuarioRepository repository;

    @BeforeAll
    public static void setUpAll() {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        repository = new UsuarioRepository();
    }

    @BeforeEach
    public void setUp() {
        repository.delete(999);
    }

    @Test
    public void findAllTestOk() {
        List<Usuario> usuarios = repository.findAll();
        assertNotNull(usuarios);
    }

    @Test
    public void saveAndFindByIdTestOk() {
        Usuario usuario = new Usuario(999, "Test Repo", "99999999R", "repo@test.com", "600000000", "Socio");
        assertTrue(repository.save(usuario));

        Usuario encontrado = repository.findById(999);
        assertNotNull(encontrado);
        assertEquals("Test Repo", encontrado.getNombre());
    }

    @Test
    public void saveTestDuplicateId() {
        Usuario usuario = new Usuario(999, "Test Repo", "99999999R", "repo@test.com", "600000000", "Socio");
        repository.save(usuario);
        assertFalse(repository.save(usuario));
    }

    @Test
    public void updateTestOk() {
        Usuario usuario = new Usuario(999, "Test Repo", "99999999R", "repo@test.com", "600000000", "Socio");
        repository.save(usuario);

        usuario.setNombre("Nombre Modificado");
        assertTrue(repository.update(usuario));

        Usuario encontrado = repository.findById(999);
        assertEquals("Nombre Modificado", encontrado.getNombre());
    }

    @Test
    public void updateTestIdNotFound() {
        Usuario usuario = new Usuario(-1, "Test No Existe", "99999999R", "repo@test.com", "600000000", "Socio");
        assertFalse(repository.update(usuario));
    }

    @Test
    public void deleteTestOk() {
        Usuario usuario = new Usuario(999, "Test Repo", "99999999R", "repo@test.com", "600000000", "Socio");
        repository.save(usuario);

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

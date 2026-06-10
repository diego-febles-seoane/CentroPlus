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
import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.modelo.Usuario;

public class IncidenciasRepositoryTest {

    private static IncidenciasRepository repository;
    private static UsuarioRepository usuarioRepository;

    @BeforeAll
    public static void setUpAll() {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        repository = new IncidenciasRepository();
        usuarioRepository = new UsuarioRepository();
        
        // Asegurar que el usuario 1 existe para las pruebas de incidencias
        if (usuarioRepository.findById(1) == null) {
            usuarioRepository.save(new Usuario(1, "Usuario Base", "11111111A", "base@test.com", "600000001", "Alumno"));
        }
    }

    @BeforeEach
    public void setUp() {
        repository.delete(999);
    }

    @Test
    public void findAllTestOk() {
        List<Incidencias> incidencias = repository.findAll();
        assertNotNull(incidencias);
    }

    @Test
    public void saveAndFindByIdTestOk() {
        Incidencias incidencia = new Incidencias(999, 1, "Asunto Repo", "Desc Repo", "2026-01-01", "Abierta");
        assertTrue(repository.save(incidencia));

        Incidencias encontrada = repository.findById(999);
        assertNotNull(encontrada);
        assertEquals("Asunto Repo", encontrada.getAsunto());
    }

    @Test
    public void saveTestDuplicateId() {
        Incidencias incidencia = new Incidencias(999, 1, "Asunto Repo", "Desc Repo", "2026-01-01", "Abierta");
        repository.save(incidencia);
        assertFalse(repository.save(incidencia));
    }

    @Test
    public void updateTestOk() {
        Incidencias incidencia = new Incidencias(999, 1, "Asunto Repo", "Desc Repo", "2026-01-01", "Abierta");
        repository.save(incidencia);

        incidencia.setAsunto("Asunto Modificado");
        assertTrue(repository.update(incidencia));

        Incidencias encontrada = repository.findById(999);
        assertEquals("Asunto Modificado", encontrada.getAsunto());
    }

    @Test
    public void updateTestIdNotFound() {
        Incidencias incidencia = new Incidencias(-1, 1, "Asunto No Existe", "Desc", "2026-01-01", "Abierta");
        assertFalse(repository.update(incidencia));
    }

    @Test
    public void deleteTestOk() {
        Incidencias incidencia = new Incidencias(999, 1, "Asunto Repo", "Desc Repo", "2026-01-01", "Abierta");
        repository.save(incidencia);

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

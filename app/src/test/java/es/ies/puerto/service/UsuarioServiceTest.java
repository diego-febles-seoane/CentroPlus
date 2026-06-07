package es.ies.puerto.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.service.sqlite.UsuarioService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioServiceTest {

    private static UsuarioService usuarioService;

    @BeforeAll
    public static void setUp() {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        usuarioService = new UsuarioService();
        usuarioService.delete(999);
    }

    @Test
    @Order(1)
    @DisplayName("findAll devuelve una lista no nula")
    public void testFindAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios, "La lista de usuarios no debe ser nula");
        System.out.println("Número de usuarios encontrados: " + usuarios.size());
    }

    @Test
    @Order(2)
    @DisplayName("save guarda un usuario y findById lo recupera correctamente")
    public void testSaveAndFindById() {
        Usuario usuario = new Usuario(999, "Test Usuario", "99999999T", "test@test.com", "600000001", "Socio");
        boolean guardado = usuarioService.save(usuario);
        assertTrue(guardado, "El usuario debe guardarse correctamente");

        Usuario encontrado = usuarioService.findById(999);
        assertNotNull(encontrado, "El usuario guardado debe poder recuperarse");
        assertEquals("Test Usuario", encontrado.getNombre(), "El nombre debe coincidir");
        assertEquals("99999999T", encontrado.getDni(), "El DNI debe coincidir");
        assertEquals("test@test.com", encontrado.getEmail(), "El email debe coincidir");
        assertEquals("600000001", encontrado.getTelefono(), "El teléfono debe coincidir");
        assertEquals("Socio", encontrado.getTipo_usuario(), "El tipo de usuario debe coincidir");
    }

    @Test
    @Order(3)
    @DisplayName("update modifica los datos de un usuario existente")
    public void testUpdate() {
        Usuario usuario = usuarioService.findById(999);
        assertNotNull(usuario, "El usuario debe existir para poder actualizarlo");

        usuario.setNombre("Usuario Actualizado");
        usuario.setEmail("actualizado@test.com");
        usuario.setTipo_usuario("Administrador");
        boolean actualizado = usuarioService.update(usuario);
        assertTrue(actualizado, "La actualización debe realizarse correctamente");

        Usuario actualizada = usuarioService.findById(999);
        assertEquals("Usuario Actualizado", actualizada.getNombre(), "El nombre actualizado debe coincidir");
        assertEquals("actualizado@test.com", actualizada.getEmail(), "El email actualizado debe coincidir");
        assertEquals("Administrador", actualizada.getTipo_usuario(), "El tipo actualizado debe coincidir");
    }

    @Test
    @Order(4)
    @DisplayName("delete elimina un usuario y findById devuelve null")
    public void testDelete() {
        boolean eliminado = usuarioService.delete(999);
        assertTrue(eliminado, "La eliminación debe realizarse correctamente");

        Usuario eliminada = usuarioService.findById(999);
        assertNull(eliminada, "Tras eliminar, findById debe devolver null");
    }

    @Test
    @Order(5)
    @DisplayName("findById devuelve null para un id inexistente")
    public void testFindByIdNoExistente() {
        Usuario resultado = usuarioService.findById(-1);
        assertNull(resultado, "findById con id inexistente debe devolver null");
    }

    @Test
    @Order(6)
    @DisplayName("delete devuelve false para un id inexistente")
    public void testDeleteNoExistente() {
        boolean eliminado = usuarioService.delete(-1);
        assertFalse(eliminado, "delete con id inexistente debe devolver false");
    }
}

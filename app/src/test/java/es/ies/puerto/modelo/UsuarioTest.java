package es.ies.puerto.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioTest {

    Usuario usuario;
    int id = 1;
    String nombre = "Ana García";
    String dni = "12345678A";
    String email = "ana@example.com";
    String telefono = "600123456";
    String tipoUsuario = "Socio";

    @BeforeEach
    void setup() {
        usuario = new Usuario(id, nombre, dni, email, telefono, tipoUsuario);
    }

    @DisplayName("El objeto Usuario no debe ser nulo")
    @Order(1)
    @Test
    void usuarioNotNullTest() {
        Assertions.assertNotNull(usuario, "El objeto Usuario no puede ser nulo");
    }

    @DisplayName("Constructor por defecto crea objeto no nulo")
    @Order(2)
    @Test
    void usuarioConstructorPorDefectoTest() {
        Usuario usuarioVacio = new Usuario();
        Assertions.assertNotNull(usuarioVacio, "El Usuario creado con constructor por defecto no puede ser nulo");
    }

    @DisplayName("Constructor con id crea objeto no nulo")
    @Order(3)
    @Test
    void usuarioConstructorConIdTest() {
        Usuario usuarioConId = new Usuario(5);
        Assertions.assertNotNull(usuarioConId, "El Usuario creado con solo id no puede ser nulo");
        Assertions.assertEquals(5, usuarioConId.getId());
    }

    @DisplayName("Equals devuelve true para usuarios con mismo id")
    @Order(4)
    @Test
    void usuarioEqualsTrueTest() {
        Usuario usuarioNuevo = new Usuario(1);
        Assertions.assertEquals(usuario, usuarioNuevo, "Dos usuarios con el mismo id deben ser iguales");
    }

    @DisplayName("Equals devuelve false para usuarios con distinto id")
    @Order(5)
    @Test
    void usuarioEqualsNotTrueTest() {
        Usuario usuarioNuevo = new Usuario(2);
        Assertions.assertNotEquals(usuario, usuarioNuevo, "Dos usuarios con distinto id no deben ser iguales");
    }

    @DisplayName("Equals sobre la misma instancia devuelve true")
    @Order(6)
    @Test
    void usuarioEqualsMismaInstanciaTest() {
        Assertions.assertEquals(usuario, usuario, "Un usuario debe ser igual a sí mismo");
    }

    @DisplayName("Equals con null devuelve false")
    @Order(7)
    @Test
    void usuarioEqualsNullTest() {
        Assertions.assertNotEquals(usuario, null, "Un usuario no debe ser igual a null");
    }

    @DisplayName("Equals con objeto de otro tipo devuelve false")
    @Order(8)
    @Test
    void usuarioEqualsOtroTipoTest() {
        Assertions.assertNotEquals(usuario, "cadena", "Un usuario no debe ser igual a un objeto de otro tipo");
    }

    @DisplayName("HashCode es consistente para el mismo id")
    @Order(9)
    @Test
    void usuarioHashCodeTest() {
        Usuario usuarioNuevo = new Usuario(1);
        Assertions.assertEquals(usuario.hashCode(), usuarioNuevo.hashCode(),
                "Dos usuarios iguales deben tener el mismo hashCode");
    }

    @DisplayName("Getter y setter de id funcionan correctamente")
    @Order(10)
    @Test
    void usuarioGetSetIdTest() {
        usuario.setId(99);
        Assertions.assertEquals(99, usuario.getId());
    }

    @DisplayName("Getter y setter de nombre funcionan correctamente")
    @Order(11)
    @Test
    void usuarioGetSetNombreTest() {
        usuario.setNombre("Carlos López");
        Assertions.assertEquals("Carlos López", usuario.getNombre());
    }

    @DisplayName("Getter y setter de DNI funcionan correctamente")
    @Order(12)
    @Test
    void usuarioGetSetDniTest() {
        usuario.setDni("87654321B");
        Assertions.assertEquals("87654321B", usuario.getDni());
    }

    @DisplayName("Getter y setter de email funcionan correctamente")
    @Order(13)
    @Test
    void usuarioGetSetEmailTest() {
        usuario.setEmail("nuevo@example.com");
        Assertions.assertEquals("nuevo@example.com", usuario.getEmail());
    }

    @DisplayName("Getter y setter de teléfono funcionan correctamente")
    @Order(14)
    @Test
    void usuarioGetSetTelefonoTest() {
        usuario.setTelefono("611987654");
        Assertions.assertEquals("611987654", usuario.getTelefono());
    }

    @DisplayName("Getter y setter de tipo_usuario funcionan correctamente")
    @Order(15)
    @Test
    void usuarioGetSetTipoUsuarioTest() {
        usuario.setTipo_usuario("Administrador");
        Assertions.assertEquals("Administrador", usuario.getTipo_usuario());
    }

    @DisplayName("toString no es nulo ni vacío")
    @Order(16)
    @Test
    void usuarioToStringTest() {
        String resultado = usuario.toString();
        Assertions.assertNotNull(resultado, "toString no debe devolver null");
        Assertions.assertFalse(resultado.isEmpty(), "toString no debe devolver una cadena vacía");
        Assertions.assertTrue(resultado.contains(nombre), "toString debe contener el nombre del usuario");
        Assertions.assertTrue(resultado.contains(dni), "toString debe contener el DNI del usuario");
    }
}

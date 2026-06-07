package es.ies.puerto.tools;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ValidacionesTest {

    @Test
    public void constructorTest() {
        Validaciones validaciones = new Validaciones();
        assertNotNull(validaciones);
    }

    @Test
    public void esNombreValidoTestOk() {
        assertTrue(Validaciones.esNombreValido("A"));
        assertTrue(Validaciones.esNombreValido("Nombre Largo"));
    }

    @Test
    public void esNombreValidoTestFail() {
        assertFalse(Validaciones.esNombreValido(null));
        assertFalse(Validaciones.esNombreValido(""));
        assertFalse(Validaciones.esNombreValido(" "));
        assertFalse(Validaciones.esNombreValido("Nombre123"));
        assertFalse(Validaciones.esNombreValido("Este es un nombre que definitivamente tiene mas de cincuenta caracteres para forzar el fallo de la regex"));
        assertFalse(Validaciones.esNombreValido("!@#$%^&*"));
    }

    @Test
    public void esEmailValidoTestOk() {
        assertTrue(Validaciones.esEmailValido("test@test.com"));
        assertTrue(Validaciones.esEmailValido("usuario.uno@ies.es"));
    }

    @Test
    public void esEmailValidoTestFail() {
        assertFalse(Validaciones.esEmailValido(null));
        assertFalse(Validaciones.esEmailValido(""));
        assertFalse(Validaciones.esEmailValido("email_sin_arroba"));
        assertFalse(Validaciones.esEmailValido("email@sin_punto"));
        assertFalse(Validaciones.esEmailValido("a@b.c")); // TLD demasiado corto
    }

    @Test
    public void esTelefonoValidoTestOk() {
        assertTrue(Validaciones.esTelefonoValido("123456789"));
    }

    @Test
    public void esTelefonoValidoTestFail() {
        assertFalse(Validaciones.esTelefonoValido(null));
        assertFalse(Validaciones.esTelefonoValido("123"));
        assertFalse(Validaciones.esTelefonoValido("1234567890"));
        assertFalse(Validaciones.esTelefonoValido("abc123456"));
        assertFalse(Validaciones.esTelefonoValido(""));
    }

    @Test
    public void esDniValidoTestOk() {
        assertTrue(Validaciones.esDniValido("12345678Z"));
    }

    @Test
    public void esDniValidoTestFail() {
        assertFalse(Validaciones.esDniValido(null));
        assertFalse(Validaciones.esDniValido(""));
        assertFalse(Validaciones.esDniValido("1234567Z"));
        assertFalse(Validaciones.esDniValido("123456789Z"));
        assertFalse(Validaciones.esDniValido("123456789"));
        assertFalse(Validaciones.esDniValido("12345678a")); // Letra minúscula
    }

    @Test
    public void esDescripcionValidaTestOk() {
        assertTrue(Validaciones.esDescripcionValida("Una descripción válida"));
    }

    @Test
    public void esDescripcionValidaTestFail() {
        assertFalse(Validaciones.esDescripcionValida(null));
        assertFalse(Validaciones.esDescripcionValida(""));
        assertFalse(Validaciones.esDescripcionValida("   "));
    }
}

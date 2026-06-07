package es.ies.puerto.tools;

import java.util.regex.Pattern;

public class Validaciones {

    public static boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        String regex = "[a-zA-Z\\s]{1,50}";
        return Pattern.matches(regex, nombre);
    }

    public static boolean esEmailValido(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        String regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return Pattern.matches(regex, email);
    }

    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            return false;
        }
        String regex = "^[0-9]{9}$";
        return Pattern.matches(regex, telefono);
    }

    public static boolean esDniValido(String dni) {
        if (dni == null || dni.isBlank()) {
            return false;
        }
        String regex = "^[0-9]{8}[A-Z]$";
        return Pattern.matches(regex, dni);
    }

    public static boolean esDescripcionValida(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            return false;
        }
        return true;
    }
}

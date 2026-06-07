package es.ies.puerto.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActividadesTest {

    Actividades actividad;
    int id = 1;
    String nombre = "Piscina";
    String tipo = "Acuatico";
    int duracion = 2;
    double precio = 30.5;
    int plazas_maximas = 16;
    int plazas_ocupadas = 15;

    @BeforeEach
    void setup() {
        actividad = new Actividades(id, nombre, tipo, duracion, precio, plazas_maximas, plazas_ocupadas);
    }

    @DisplayName("La actividad no debe ser nula")
    @Order(1)
    @Test
    void actividadNotNullTest() {
        Assertions.assertNotNull(actividad, "La clase actividad no puede ser nulo");
    }

    @DisplayName("Constructor por defecto crea objeto no nulo")
    @Order(2)
    @Test
    void actividadConstructorPorDefectoTest() {
        Actividades actividadVacia = new Actividades();
        Assertions.assertNotNull(actividadVacia, "La actividad con constructor por defecto no puede ser nula");
    }

    @DisplayName("Constructor con id crea objeto con id correcto")
    @Order(3)
    @Test
    void actividadConstructorConIdTest() {
        Actividades actividadConId = new Actividades(5);
        Assertions.assertNotNull(actividadConId);
        Assertions.assertEquals(5, actividadConId.getId());
    }

    @DisplayName("Equals devuelve true para actividades con mismo id")
    @Order(4)
    @Test
    void actividadEqualsTrueTest() {
        Actividades actividadNueva = new Actividades(1);
        Assertions.assertEquals(actividad, actividadNueva, "Deben ser iguales por tener el mismo id");
    }

    @DisplayName("Equals devuelve false para actividades con distinto id")
    @Order(5)
    @Test
    void actividadEqualsNotTrueTest() {
        Actividades actividadNueva = new Actividades(2);
        Assertions.assertNotEquals(actividad, actividadNueva, "No deben ser iguales por tener distinto id");
    }

    @DisplayName("Equals sobre la misma instancia devuelve true")
    @Order(6)
    @Test
    void actividadEqualsMismaInstanciaTest() {
        Assertions.assertEquals(actividad, actividad, "Una actividad debe ser igual a sí misma");
    }

    @DisplayName("Equals con null devuelve false")
    @Order(7)
    @Test
    void actividadEqualsNullTest() {
        Assertions.assertNotEquals(actividad, null, "Una actividad no debe ser igual a null");
    }

    @DisplayName("Equals con objeto de otro tipo devuelve false")
    @Order(8)
    @Test
    void actividadEqualsOtroTipoTest() {
        Assertions.assertNotEquals(actividad, "cadena", "Una actividad no debe ser igual a un objeto de otro tipo");
    }

    @DisplayName("HashCode es consistente para el mismo id")
    @Order(9)
    @Test
    void actividadHashCodeTest() {
        Actividades actividadNueva = new Actividades(1);
        Assertions.assertEquals(actividad.hashCode(), actividadNueva.hashCode(),
                "Dos actividades iguales deben tener el mismo hashCode");
    }

    @DisplayName("cancelarPlaza con plazas disponibles devuelve true")
    @Order(10)
    @Test
    void actividadCancelarPlazaConPlazasTest() {
        boolean resultado = actividad.cancelarPlaza();
        Assertions.assertTrue(resultado, "cancelarPlaza debe devolver true cuando hay plazas ocupadas");
        Assertions.assertEquals(14, actividad.getPlazasOcupadas());
    }

    @DisplayName("cancelarPlaza con 0 plazas ocupadas devuelve false")
    @Order(11)
    @Test
    void actividadCancelarPlazaSinPlazasTest() {
        Actividades actividadSinOcupadas = new Actividades(2, "Yoga", "Bienestar", 1, 10.0, 10, 0);
        boolean resultado = actividadSinOcupadas.cancelarPlaza();
        Assertions.assertFalse(resultado, "cancelarPlaza debe devolver false cuando no hay plazas ocupadas");
    }

    @DisplayName("Getters devuelven los valores correctos tras la construcción")
    @Order(12)
    @Test
    void actividadGettersTest() {
        Assertions.assertEquals(id, actividad.getId());
        Assertions.assertEquals(nombre, actividad.getNombre());
        Assertions.assertEquals(tipo, actividad.getTipoActividad());
        Assertions.assertEquals(duracion, actividad.getDuracion());
        Assertions.assertEquals(precio, actividad.getPrecio(), 0.001);
        Assertions.assertEquals(plazas_maximas, actividad.getPlazasMaximas());
        Assertions.assertEquals(plazas_ocupadas, actividad.getPlazasOcupadas());
    }

    @DisplayName("Setters actualizan los valores correctamente")
    @Order(13)
    @Test
    void actividadSettersTest() {
        actividad.setId(10);
        actividad.setNombre("Natación");
        actividad.setTipoActividad("Deporte");
        actividad.setDuracion(3);
        actividad.setPrecio(40.0);
        actividad.setPlazasMaximas(20);
        actividad.setPlazasOcupadas(10);

        Assertions.assertEquals(10, actividad.getId());
        Assertions.assertEquals("Natación", actividad.getNombre());
        Assertions.assertEquals("Deporte", actividad.getTipoActividad());
        Assertions.assertEquals(3, actividad.getDuracion());
        Assertions.assertEquals(40.0, actividad.getPrecio(), 0.001);
        Assertions.assertEquals(20, actividad.getPlazasMaximas());
        Assertions.assertEquals(10, actividad.getPlazasOcupadas());
    }

    @DisplayName("toString no es nulo ni vacío y contiene el nombre")
    @Order(14)
    @Test
    void actividadToStringTest() {
        String resultado = actividad.toString();
        Assertions.assertNotNull(resultado, "toString no debe devolver null");
        Assertions.assertFalse(resultado.isEmpty(), "toString no debe devolver cadena vacía");
        Assertions.assertTrue(resultado.contains(nombre), "toString debe contener el nombre de la actividad");
    }
}

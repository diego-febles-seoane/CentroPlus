package es.ies.puerto.modelo;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class IncidenciasTest {
    Incidencias incidencia;
    int idIncidencia = 1;
    int idUsuario = 2;
    String asunto = "Averia";
    String descripcion = "Televisor averiado";
    String fecha = LocalDate.now().toString();
    String estado = "En Proceso";

    @BeforeEach
    void setup(){
        incidencia = new Incidencias(idIncidencia, idUsuario, asunto, descripcion, fecha, estado);
    }
    
    @DisplayName("Test verifica que incidencia no sea nulo")
    @Order(1)
    @Test
    void incidenciaNotNullTest(){
        Assertions.assertNotNull(incidencia, "La clase incidencia no puede ser nula");
    }

    @DisplayName("Test verifica getters y setters")
    @Order(2)
    @Test
    void incidenciaGettersSettersTest(){
        Assertions.assertEquals(idIncidencia, incidencia.getIdIncidencia());
        Assertions.assertEquals(idUsuario, incidencia.getId());
        Assertions.assertEquals(asunto, incidencia.getAsunto());
        Assertions.assertEquals(descripcion, incidencia.getDescripcion());
        Assertions.assertEquals(fecha, incidencia.getFecha());
        Assertions.assertEquals(estado, incidencia.getEstado());

        int nuevoIdIncidencia = 10;
        String nuevoAsunto = "Mantenimiento";
        String nuevaDescripcion = "Revisión de aire acondicionado";
        String nuevaFecha = "2026-06-05";
        String nuevoEstado = "Resuelto";

        incidencia.setIdIncidencia(nuevoIdIncidencia);
        incidencia.setAsunto(nuevoAsunto);
        incidencia.setDescripcion(nuevaDescripcion);
        incidencia.setFecha(nuevaFecha);
        incidencia.setEstado(nuevoEstado);

        Assertions.assertEquals(nuevoIdIncidencia, incidencia.getIdIncidencia());
        Assertions.assertEquals(nuevoAsunto, incidencia.getAsunto());
        Assertions.assertEquals(nuevaDescripcion, incidencia.getDescripcion());
        Assertions.assertEquals(nuevaFecha, incidencia.getFecha());
        Assertions.assertEquals(nuevoEstado, incidencia.getEstado());
    }

    @DisplayName("Test que verifica que incidencias sean iguales")
    @Order(3)
    @Test
    void incidenciaEqualsTrueTest(){
        Incidencias incidenciaNueva = new Incidencias(1);
        Assertions.assertEquals(incidencia, incidenciaNueva, "Deben de ser iguales");
    }

    @DisplayName("Test que verifica que incidencias no sean iguales")
    @Order(4)
    @Test
    void incidenciaEqualsNotTrueTest(){
        Incidencias incidenciaNueva = new Incidencias(2);
        Assertions.assertNotEquals(incidencia, incidenciaNueva, "No deben de ser iguales");
    }

    @DisplayName("Test que verifica que incidencia es igual a sí misma")
    @Order(5)
    @Test
    void incidenciaEqualsSelfTest(){
        Assertions.assertEquals(incidencia, incidencia, "Debe de ser igual a sí misma");
    }

    @DisplayName("Test que verifica equals con null y otro tipo")
    @Order(5)
    @Test
    void incidenciaEqualsNullOtherTypeTest(){
        Assertions.assertNotEquals(incidencia, null);
        Assertions.assertNotEquals(incidencia, "cadena");
    }

    @DisplayName("Test que verifica hashCode")
    @Order(5)
    @Test
    void incidenciaHashCodeTest(){
        Incidencias incidencia2 = new Incidencias(1);
        Assertions.assertEquals(incidencia.hashCode(), incidencia2.hashCode());
    }

    @DisplayName("Test que verifica toString")
    @Order(6)
    @Test
    void incidenciaToStringTest(){
        String expected = "{" +
            " idIncidencia='" + incidencia.getIdIncidencia() + "'" +
            ", idUsuario='" + incidencia.getId() + "'" +
            ", asunto='" + incidencia.getAsunto() + "'" +
            ", descripcion='" + incidencia.getDescripcion() + "'" +
            ", fecha='" + incidencia.getFecha() + "'" +
            ", estado='" + incidencia.getEstado() + "'" +
            "}";
        Assertions.assertEquals(expected, incidencia.toString());
    }

    @DisplayName("Test constructores")
    @Order(7)
    @Test
    void incidenciaConstructoresTest(){
        Incidencias incidenciaVacia = new Incidencias();
        Assertions.assertNotNull(incidenciaVacia);

        Incidencias incidenciaId = new Incidencias(5);
        Assertions.assertEquals(5, incidenciaId.getIdIncidencia());
    }

}

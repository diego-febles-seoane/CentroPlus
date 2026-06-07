package es.ies.puerto.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class ReservasTest {
    Reservas reserva;
    int idReserva = 1;
    int idUsuario = 2;
    int idActividad = 3;
    String fecha = "2026-06-04";
    String estado = "Confirmada";

    @BeforeEach
    void setup(){
        reserva = new Reservas(idReserva, idUsuario, idActividad, fecha, estado);
    }

    @DisplayName("Test verifica que reserva no sea nulo")
    @Order(1)
    @Test
    void reservaNotNullTest(){
        Assertions.assertNotNull(reserva, "La clase reserva no puede ser nula");
    }

    @DisplayName("Test verifica getters y setters")
    @Order(2)
    @Test
    void reservaGettersSettersTest(){
        Assertions.assertEquals(idReserva, reserva.getIdReserva());
        Assertions.assertEquals(idUsuario, reserva.getId());
        Assertions.assertEquals(idActividad, reserva.getIdActividad());
        Assertions.assertEquals(fecha, reserva.getFecha());
        Assertions.assertEquals(estado, reserva.getEstado());

        int nuevoIdReserva = 10;
        int nuevoIdActividad = 20;
        String nuevaFecha = "2026-06-05";
        String nuevoEstado = "Cancelada";

        reserva.setIdReserva(nuevoIdReserva);
        reserva.setIdActividad(nuevoIdActividad);
        reserva.setFecha(nuevaFecha);
        reserva.setEstado(nuevoEstado);

        Assertions.assertEquals(nuevoIdReserva, reserva.getIdReserva());
        Assertions.assertEquals(nuevoIdActividad, reserva.getIdActividad());
        Assertions.assertEquals(nuevaFecha, reserva.getFecha());
        Assertions.assertEquals(nuevoEstado, reserva.getEstado());
    }

    @DisplayName("Test que verifica que reservas sean iguales")
    @Order(3)
    @Test
    void reservaEqualsTrueTest(){
        Reservas reservaNueva = new Reservas(1);
        Assertions.assertEquals(reserva, reservaNueva, "Deben de ser iguales");
    }

    @DisplayName("Test que verifica que reservas no sean iguales")
    @Order(4)
    @Test
    void reservaEqualsNotTrueTest(){
        Reservas reservaNueva = new Reservas(2);
        Assertions.assertNotEquals(reserva, reservaNueva, "No deben de ser iguales");
    }

    @DisplayName("Test que verifica que reserva es igual a sí misma")
    @Order(5)
    @Test
    void reservaEqualsSelfTest(){
        Assertions.assertEquals(reserva, reserva, "Debe de ser igual a sí misma");
    }

    @DisplayName("Test que verifica equals con null y otro tipo")
    @Order(5)
    @Test
    void reservaEqualsNullOtherTypeTest(){
        Assertions.assertNotEquals(reserva, null);
        Assertions.assertNotEquals(reserva, "cadena");
    }

    @DisplayName("Test que verifica hashCode")
    @Order(5)
    @Test
    void reservaHashCodeTest(){
        Reservas reserva2 = new Reservas(1);
        Assertions.assertEquals(reserva.hashCode(), reserva2.hashCode());
    }

    @DisplayName("Test que verifica toString")
    @Order(6)
    @Test
    void reservaToStringTest(){
        String expected = "{" +
            " idReserva='" + reserva.getIdReserva() + "'" +
            ", idUsuario='" + reserva.getId() + "'" +
            ", idActividad='" + reserva.getIdActividad() + "'" +
            ", fecha='" + reserva.getFecha() + "'" +
            ", estado='" + reserva.getEstado() + "'" +
            "}";
        Assertions.assertEquals(expected, reserva.toString());
    }

    @DisplayName("Test constructores")
    @Order(7)
    @Test
    void reservaConstructoresTest(){
        Reservas reservaVacia = new Reservas();
        Assertions.assertNotNull(reservaVacia);

        Reservas reservaId = new Reservas(5);
        Assertions.assertEquals(5, reservaId.getIdReserva());
    }

}

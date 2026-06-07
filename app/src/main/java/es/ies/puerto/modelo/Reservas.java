package es.ies.puerto.modelo;

import java.util.Objects;

public class Reservas extends Usuario{
    int idReserva;
    int idActividad;
    String fecha;
    String estado;

    public Reservas() {
        super();
    }

    public Reservas(int idReserva) {
        this.idReserva = idReserva;
    }

    public Reservas(int idReserva, int idUsuario, int idActividad, String fecha, String estado) {
        super(idUsuario);
        this.idReserva = idReserva;
        this.idActividad = idActividad;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdReserva() {
        return this.idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }


    public int getIdActividad() {
        return this.idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Reservas)) {
            return false;
        }
        Reservas reservas = (Reservas) o;
        return idReserva == reservas.idReserva;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }
    

    @Override
    public String toString() {
        return "{" +
            " idReserva='" + getIdReserva() + "'" +
            ", idUsuario='" + getId() + "'" +
            ", idActividad='" + getIdActividad() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }

}

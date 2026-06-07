package es.ies.puerto.modelo;

import java.util.Objects;

public class Incidencias extends Reservas{
    
    int idIncidencia;
    String asunto;
    String descripcion;
    String fecha;
    String estado;

    public Incidencias(){
        super();
    }

    public Incidencias(int idIncidencia){
        this.idIncidencia = idIncidencia;
    }

    public Incidencias(int idIncidencia, int idUsuario, String asunto, String descripcion, String fecha, String estado) {
        super();
        setId(idUsuario);
        this.idIncidencia = idIncidencia;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdIncidencia() {
        return this.idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getAsunto() {
        return this.asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(o instanceof Incidencias)) {
            return false;
        }
        Incidencias incidencias = (Incidencias) o;
        return idIncidencia == incidencias.idIncidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIncidencia);
    }
    

    @Override
    public String toString() {
        return "{" +
            " idIncidencia='" + getIdIncidencia() + "'" +
            ", idUsuario='" + getId() +"'"+
            ", asunto='" + getAsunto() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }

}

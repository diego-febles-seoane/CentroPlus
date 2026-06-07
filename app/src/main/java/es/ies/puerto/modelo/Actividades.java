package es.ies.puerto.modelo;

import java.util.Objects;

public class Actividades {

    int id;
    String nombre;
    String tipoActividad;
    int duracion;
    double precio;
    int plazasMaximas;
    int plazasOcupadas;

    /**
     * Constructor por defecto
     */
    public Actividades() {

    }

    /**
     * Constructor para busqueda
     *
     * @param id Identificador de la actividad
     */
    public Actividades(int id) {
        this.id = id;
    }

    /**
     * Constructor de la clase
     *
     * @param id Identificador de la actividad
     * @param nombre Nombre de la actividad
     * @param tipoActividad Tipo de la actividad
     * @param duracion Duracion de la actividdad
     * @param precio Precio de la actividad
     * @param plazasMaximas Plazas maximas
     * @param plazasOcupadas Plazas ocupadas
     */
    public Actividades(int id, String nombre, String tipoActividad, int duracion, double precio, int plazasMaximas, int plazasOcupadas) {
        this.id = id;
        this.nombre = nombre;
        this.tipoActividad = tipoActividad;
        this.duracion = duracion;
        this.precio = precio;
        this.plazasMaximas = plazasMaximas;
        this.plazasOcupadas = plazasOcupadas;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoActividad() {
        return this.tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public int getDuracion() {
        return this.duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getPlazasMaximas() {
        return this.plazasMaximas;
    }

    public void setPlazasMaximas(int plazasMaximas) {
        this.plazasMaximas = plazasMaximas;
    }

    public int getPlazasOcupadas() {
        return this.plazasOcupadas;
    }

    public void setPlazasOcupadas(int plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Actividades)) {
            return false;
        }
        Actividades actividades = (Actividades) o;
        return id == actividades.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean cancelarPlaza() {
        if (plazasOcupadas <= 0) {
            return false;
        }
        plazasOcupadas--;
        return true;
    }

    @Override
    public String toString() {
        return "{"
                + " id='" + getId() + "'"
                + ", nombre='" + getNombre() + "'"
                + ", tipoActividad='" + getTipoActividad() + "'"
                + ", duracion='" + getDuracion() + "'"
                + ", precio='" + getPrecio() + "'"
                + ", plazasMaximas='" + getPlazasMaximas() + "'"
                + ", plazasOcupadas='" + getPlazasOcupadas() + "'"
                + "}";
    }
}

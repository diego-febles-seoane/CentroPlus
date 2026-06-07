package es.ies.puerto.modelo;

import java.util.Objects;

public class Usuario {
    int id;
    String nombre;
    String dni;
    String email;
    String telefono;
    String tipo_usuario;

    public Usuario(){

    }

    public Usuario(int id){
        this.id = id;
    }

    public Usuario(int id, String nombre, String dni, String email, String telefono, String tipo_usuario) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.tipo_usuario = tipo_usuario;
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

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo_usuario() {
        return this.tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", dni='" + getDni() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", tipo_usuario='" + getTipo_usuario() + "'" +
            "}";
    }

}

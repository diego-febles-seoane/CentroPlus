package es.ies.puerto.repository.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.repository.IIncidenciasRepository;

public class IncidenciasRepository extends SQLiteConnectionManager implements IIncidenciasRepository{
    
    public IncidenciasRepository(){
        super();
    }

    @Override
    public boolean save(Incidencias incidencia) {
        try (Connection connection = getConnection();
            PreparedStatement sentencia = connection.prepareStatement("INSERT INTO incidencias (id, id_usuario, asunto, descripcion, fecha, estado) VALUES (?,?,?,?,?,?)")){
            sentencia.setInt(1, incidencia.getIdIncidencia());
            sentencia.setInt(2, incidencia.getId());
            sentencia.setString(3, incidencia.getAsunto());
            sentencia.setString(4, incidencia.getDescripcion());
            sentencia.setString(5, incidencia.getFecha());
            sentencia.setString(6, incidencia.getEstado());
            return sentencia.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error guardando incidencia: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Incidencias incidencia) {
        try (Connection connection = getConnection();
            PreparedStatement sentencia = connection.prepareStatement("UPDATE incidencias SET id_usuario = ?, asunto = ?, descripcion = ?, fecha = ?, estado = ? WHERE id = ?")){
            sentencia.setInt(1, incidencia.getId());
            sentencia.setString(2, incidencia.getAsunto());
            sentencia.setString(3, incidencia.getDescripcion());
            sentencia.setString(4, incidencia.getFecha());
            sentencia.setString(5, incidencia.getEstado());
            sentencia.setInt(6, incidencia.getIdIncidencia());
            return sentencia.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error actualizando incidencia: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Incidencias findById(int idIncidencia) {
        try (Connection connection = getConnection();
        PreparedStatement setencia = connection.prepareStatement("SELECT * FROM incidencias WHERE id =?")){
            setencia.setInt(1, idIncidencia);
            ResultSet resultado = setencia.executeQuery();
            if (resultado.next()) {
                int idUsuario = resultado.getInt("id_usuario");
                String asunto = resultado.getString("asunto");
                String descripcion = resultado.getString("descripcion");
                String fecha = resultado.getString("fecha");
                String estado = resultado.getString("estado");
                return new Incidencias(idIncidencia, idUsuario, asunto, descripcion, fecha, estado);
            }
        } catch (Exception e) {
            System.err.println("Error buscando incidencia por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Incidencias> findAll() {
        List<Incidencias> incidencias = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement setencia = connection.prepareStatement("SELECT * FROM incidencias")){
            ResultSet resultado = setencia.executeQuery();
            while (resultado.next()) {
                int idIncidencia = resultado.getInt("id");
                int idUsuario = resultado.getInt("id_usuario");
                String asunto = resultado.getString("asunto");
                String descripcion = resultado.getString("descripcion");
                String fecha = resultado.getString("fecha");
                String estado = resultado.getString("estado");
                incidencias.add(new Incidencias(idIncidencia, idUsuario, asunto, descripcion, fecha, estado));
            }
        } catch (Exception e) {
            System.err.println("Error buscando todas las incidencias: " + e.getMessage());
        }
        return incidencias;
    }

    @Override
    public boolean delete(int idIncidencia) {
        try (Connection connection = getConnection();
        PreparedStatement setencia = connection.prepareStatement("DELETE FROM incidencias WHERE id = ?")){
            setencia.setInt(1, idIncidencia);
            return setencia.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error eliminando incidencia: " + e.getMessage());
            return false;
        }
    }
}

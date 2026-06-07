package es.ies.puerto.repository.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.repository.IActividadesRepository;

public class ActividadesRepository extends SQLiteConnectionManager implements IActividadesRepository{

    @Override
    public boolean save(Actividades actividad) {
        try (Connection connection = getConnection();
            PreparedStatement sentencia = connection.prepareStatement("INSERT INTO actividades (id, nombre, tipo_actividad, duracion, precio, plazas_maximas, plazas_ocupadas) VALUES (?,?,?,?,?,?,?)")){
            sentencia.setInt(1, actividad.getId());
            sentencia.setString(2, actividad.getNombre());
            sentencia.setString(3, actividad.getTipoActividad());
            sentencia.setInt(4, actividad.getDuracion());
            sentencia.setDouble(5, actividad.getPrecio());
            sentencia.setInt(6, actividad.getPlazasMaximas());
            sentencia.setInt(7, actividad.getPlazasOcupadas());
            return sentencia.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error guardando actividad: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Actividades actividad) {
        try (Connection connection = getConnection();
            PreparedStatement sentencia = connection.prepareStatement("UPDATE actividades SET nombre = ?, tipo_actividad = ?, duracion = ?, precio = ?, plazas_maximas = ?, plazas_ocupadas = ? WHERE id = ?")){
            sentencia.setString(1, actividad.getNombre());
            sentencia.setString(2, actividad.getTipoActividad());
            sentencia.setInt(3, actividad.getDuracion());
            sentencia.setDouble(4, actividad.getPrecio());
            sentencia.setInt(5, actividad.getPlazasMaximas());
            sentencia.setInt(6, actividad.getPlazasOcupadas());
            sentencia.setInt(7, actividad.getId());
            return sentencia.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error actualizando actividad: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Actividades findById(int id) {
        try (Connection connection = getConnection();
        PreparedStatement setencia = connection.prepareStatement("SELECT * FROM actividades WHERE id =?")){
            setencia.setInt(1, id);
            ResultSet resultado = setencia.executeQuery();
            if (resultado.next()) {
                String nombre = resultado.getString("nombre");
                String tipoActividad = resultado.getString("tipo_actividad");
                int duracion = resultado.getInt("duracion");
                int precio = resultado.getInt("precio");
                int plazas_maximas = resultado.getInt("plazas_maximas");
                int plazas_ocupadas = resultado.getInt("plazas_ocupadas");
                return new Actividades(id, nombre, tipoActividad, duracion, precio, plazas_maximas, plazas_ocupadas);
            }
        } catch (Exception e) {
            System.err.println("Error buscando actividad por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Actividades> findAll() {
        List<Actividades> actividades = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement setencia = connection.prepareStatement("SELECT * FROM actividades")){
            ResultSet resultado = setencia.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String tipoActividad = resultado.getString("tipo_actividad");
                int duracion = resultado.getInt("duracion");
                int precio = resultado.getInt("precio");
                int plazas_maximas = resultado.getInt("plazas_maximas");
                int plazas_ocupadas = resultado.getInt("plazas_ocupadas");
                actividades.add(new Actividades(id, nombre, tipoActividad, duracion, precio, plazas_maximas, plazas_ocupadas));
            }
        } catch (Exception e) {
            System.err.println("Error buscando todas las actividades: " + e.getMessage());
        }
        return actividades;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = getConnection();
        PreparedStatement setencia = connection.prepareStatement("DELETE FROM actividades WHERE id = ?")){
            setencia.setInt(1, id);
            return setencia.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error eliminando actividad: " + e.getMessage());
            return false;
        }
    }
}

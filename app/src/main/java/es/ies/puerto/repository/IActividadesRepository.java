package es.ies.puerto.repository;

import java.util.List;
import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.modelo.Usuario;

public interface IActividadesRepository {
    /**
     * Metodo que crea la actividad
     * @return true/false
     */
    public boolean save(Actividades actividad);
    /**
     * Metodo que actualiza la actividad
     * @return actividad
     */
    public boolean update(Actividades actividad);
    /**
     * Metodo que busca la actividad por su id
     * @param id de actividad
     * @return actividad
     */
    public Actividades findById(int id);
    /**
     * Metodo que busca todo las actividades
     * @return Lista<Actividades>
     */
    public List<Actividades> findAll();
    /**
     * Metodo que elimina la actividad mediante su id
     * @param id de actividad
     * @return actividad
     */
    public boolean delete(int id);
}

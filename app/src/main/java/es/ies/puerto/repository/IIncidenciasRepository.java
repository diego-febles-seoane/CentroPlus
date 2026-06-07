package es.ies.puerto.repository;

import java.util.List;
import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.modelo.Usuario;

public interface IIncidenciasRepository {
    /**
     * Metodo que crea la incidencias
     * @return true/false
     */
    public boolean save(Incidencias incidencia);
    /**
     * Metodo que actualiza la incidencias
     * @return incidencias
     */
    public boolean update(Incidencias incidencia);
    /**
     * Metodo que busca la incidencias por su id
     * @param idIncidencia de incidencias
     * @return incidencias
     */
    public Incidencias findById(int idIncidencia);
    /**
     * Metodo que busca todo las Incidencias
     * @return Lista<Incidencias>
     */
    public List<Incidencias> findAll();
    /**
     * Metodo que elimina la incidencias mediante su id
     * @param idIncidencia de incidencias
     * @return incidencias
     */
    public boolean delete(int idIncidencia);
}

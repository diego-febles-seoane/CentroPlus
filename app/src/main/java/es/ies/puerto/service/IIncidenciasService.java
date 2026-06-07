package es.ies.puerto.service;

import java.util.List;

import es.ies.puerto.modelo.Incidencias;

public interface IIncidenciasService {

    /**
     * Funcion para guardar una incidencia dentro de la base de datos
     * @return true si se añade
     */
    public boolean save(Incidencias incidencia);

    /**
     * Funcion para actualizar una incidencia en la base de datos en funcion del id
     * @param id id de la incidencia a actualizar
     * @return true si se actualiza
     */
    public boolean update(Incidencias incidencias);

    /**
     * Funcion que borra una incidencia de la base de datos
     * @param id id de la incidencia
     * @return
     */
    public boolean delete(int id);

    /**
     * Funcion para listar todas las incidencia de la base de datos
     * @return lista de las incidencia
     */
    public List<Incidencias> findAll();

    /**
     * Funcion para buscar una incidencia por su id
     * @param id id de las incidencia a buscar
     * @return incidencia buscada
     */
    public Incidencias findById(int id);
}

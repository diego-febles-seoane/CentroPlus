package es.ies.puerto.service;

import java.util.List;

import es.ies.puerto.modelo.Actividades;

public interface IActividadesService {

    /**
     * Funcion para guardar una actividad dentro de la base de datos
     * @return true si se añade
     */
    public boolean save(Actividades actividad);

    /**
     * Funcion para actualizar una actividad en la base de datos en funcion del id
     * @param id id de la actividad a actualizar
     * @return si se actualiza true
     */
    public boolean update(Actividades actividade);

    /**
     * Funcion que borra una actividad de la base de datos
     * @param id id de la actividad
     * @return
     */
    public boolean delete(int id);

    /**
     * Funcion para listar todas las actividades de la base de datos
     * @return lista de las actividades
     */
    public List<Actividades> findAll();

    /**
     * Funcion para buscar una actividad por su id
     * @param id id de las actividad a buscar
     * @return actividad buscada
     */
    public Actividades findById(int id);
}

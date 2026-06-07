package es.ies.puerto.service;

import java.util.List;

import es.ies.puerto.modelo.Reservas;

public interface IReservasService {

    /**
     * Funcion para guardar una reserva dentro de la base de datos
     * @return true si se añade
     */
    public boolean save(Reservas reserva);

    /**
     * Funcion para actualizar una reserva en la base de datos en funcion del id
     * @param id id de la reserva a actualizar
     * @return true si la reserva se actualizada
     */
    public boolean update(Reservas reserva);

    /**
     * Funcion que borra una reserva de la base de datos
     * @param id id de la reserva
     * @return
     */
    public boolean delete(int id);

    /**
     * Funcion para listar todas las reserva de la base de datos
     * @return lista de las reserva
     */
    public List<Reservas> findAll();

    /**
     * Funcion para buscar una reserva por su id
     * @param id id de las reserva a buscar
     * @return reserva buscada
     */
    public Reservas findById(int id);
}

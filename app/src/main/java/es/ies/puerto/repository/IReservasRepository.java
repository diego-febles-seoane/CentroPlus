package es.ies.puerto.repository;

import java.util.List;


import es.ies.puerto.modelo.Reservas;

public interface IReservasRepository {
    /**
     * Metodo que crea la reserva
     * @return true/false
     */
    public boolean save(Reservas reserva);
    /**
     * Metodo que actualiza la reserva
     * @return reserva
     */
    public boolean update(Reservas reserva);
    /**
     * Metodo que busca la reserva por su id
     * @param idReserva de reserva
     * @return reserva
     */
    public Reservas findById(int idReserva);
    /**
     * Metodo que busca todo las Reservas
     * @return Lista<Reservas>
     */
    public List<Reservas> findAll();
    /**
     * Metodo que elimina la reserva mediante su id
     * @param idReserva de reserva
     * @return reserva
     */
    public boolean delete(int idReserva);
}

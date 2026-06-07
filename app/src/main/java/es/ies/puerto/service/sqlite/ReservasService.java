package es.ies.puerto.service.sqlite;

import java.util.List;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.repository.IReservasRepository;
import es.ies.puerto.repository.sqlite.ReservasRepository;
import es.ies.puerto.service.IReservasService;

public class ReservasService extends SQLiteConnectionManager implements IReservasService{

    private final IReservasRepository repository;

    public ReservasService(){
        this.repository = new ReservasRepository();                    
    }

    @Override
    public boolean save(Reservas reserva) {
        return repository.save(reserva);
    }

    @Override
    public boolean update(Reservas reserva) {
        return repository.update(reserva);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public List<Reservas> findAll() {
        return repository.findAll();
    }

    @Override
    public Reservas findById(int id) {
        return repository.findById(id);
    }

}

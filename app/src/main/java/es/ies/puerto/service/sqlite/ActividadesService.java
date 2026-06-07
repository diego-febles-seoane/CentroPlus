package es.ies.puerto.service.sqlite;
import java.util.List;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.repository.IActividadesRepository;
import es.ies.puerto.repository.sqlite.ActividadesRepository;
import es.ies.puerto.service.IActividadesService;

public class ActividadesService extends SQLiteConnectionManager implements IActividadesService{

    private final IActividadesRepository repository;

    public ActividadesService(){
        this.repository = new ActividadesRepository();                    
    }

    @Override
    public boolean save(Actividades actividad) {
        return repository.save(actividad);
    }

    @Override
    public boolean update(Actividades actividade) {
        return repository.update(actividade);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public List<Actividades> findAll() {
        return repository.findAll();
    }

    @Override
    public Actividades findById(int id) {
        return repository.findById(id);
    }

}

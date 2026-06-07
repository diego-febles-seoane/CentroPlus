package es.ies.puerto.service.sqlite;

import java.util.List;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.repository.IIncidenciasRepository;
import es.ies.puerto.repository.sqlite.IncidenciasRepository;
import es.ies.puerto.service.IIncidenciasService;

public class IncidenciasService extends SQLiteConnectionManager implements IIncidenciasService{

    private final IIncidenciasRepository repository;

    public IncidenciasService(){
        this.repository = new IncidenciasRepository();                    
    }

    @Override
    public boolean save(Incidencias incidencia) {
        return repository.save(incidencia);
    }

    @Override
    public boolean update(Incidencias incidencias) {
        return repository.update(incidencias);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public List<Incidencias> findAll() {
        return repository.findAll();
    }

    @Override
    public Incidencias findById(int id) {
        return repository.findById(id);
    }

}

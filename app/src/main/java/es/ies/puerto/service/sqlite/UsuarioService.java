package es.ies.puerto.service.sqlite;

import java.util.List;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.repository.IUsuarioRepository;
import es.ies.puerto.repository.sqlite.UsuarioRepository;
import es.ies.puerto.service.IUsuarioService;

public class UsuarioService extends SQLiteConnectionManager implements IUsuarioService{

    private final IUsuarioRepository repository;

    public UsuarioService(){
        this.repository = new UsuarioRepository();                    
    }

    @Override
    public boolean save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public boolean update(Usuario usuario) {
        return repository.update(usuario);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Usuario findById(int id) {
        return repository.findById(id);
    }

}

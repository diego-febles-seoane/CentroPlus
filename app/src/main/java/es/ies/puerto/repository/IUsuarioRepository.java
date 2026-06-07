package es.ies.puerto.repository;

import java.util.List;

import es.ies.puerto.modelo.Usuario;

public interface IUsuarioRepository {
    /**
     * Metodo que crea la usuario
     * @return true/false
     */
    public boolean save(Usuario usuario);
    /**
     * Metodo que actualiza la usuario
     * @return usuario
     */
    public boolean update(Usuario usuario);
    /**
     * Metodo que busca la usuario por su id
     * @param id de usuario
     * @return usuario
     */
    public Usuario findById(int id);
    /**
     * Metodo que busca todo las usuarios
     * @return Lista<Usuario>
     */
    public List<Usuario> findAll();
    /**
     * Metodo que elimina la usuario mediante su id
     * @param id de usuario
     * @return usuario
     */
    public boolean delete(int id);
}

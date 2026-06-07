package es.ies.puerto.service;

import java.util.List;

import es.ies.puerto.modelo.Usuario;

public interface IUsuarioService {

    /**
     * Funcion para guardar una usuario dentro de la base de datos
     * @return true si se añade
     */
    public boolean save(Usuario usuario);

    /**
     * Funcion para actualizar una usuario en la base de datos en funcion del id
     * @param id id de la usuario a actualizar
     * @return true si usuario se actualizada
     */
    public boolean update(Usuario usuario);

    /**
     * Funcion que borra una usuario de la base de datos
     * @param id id de la usuario
     * @return
     */
    public boolean delete(int id);

    /**
     * Funcion para listar todas las usuario de la base de datos
     * @return lista de las usuario
     */
    public List<Usuario> findAll();

    /**
     * Funcion para buscar una usuario por su id
     * @param id id de las usuario a buscar
     * @return usuario buscada
     */
    public Usuario findById(int id);
}

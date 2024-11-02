package com.parrilla.chalaperu.dao;

import com.parrilla.chalaperu.model.Usuario;
import java.util.List;

public interface IUsuarioDAO {

    public List<Usuario> ListarTodos();
    
    public List<Usuario> ListarPorIdRol(int id);

    public Usuario BuscarPorId(int id);

    public boolean Guardar(Usuario obj) throws Exception;

    public boolean Actualizar(Usuario obj) throws Exception;

    public boolean Eliminar(int id) throws Exception;

    public Usuario Login(String username, String password);
    
    public int ExisteUsername(String username , int id);
}

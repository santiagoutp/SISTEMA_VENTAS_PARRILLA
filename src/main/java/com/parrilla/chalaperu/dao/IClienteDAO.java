package com.parrilla.chalaperu.dao;

import com.parrilla.chalaperu.model.Cliente;
import java.util.List;

public interface IClienteDAO {

    public List<Cliente> ListarTodos();

    public Cliente BuscarPorId(int id);

    public boolean Guardar(Cliente obj) throws Exception;

    public boolean Actualizar(Cliente obj) throws Exception;

    public boolean Eliminar(int id) throws Exception;
}

package com.parrilla.chalaperu.service;

import com.parrilla.chalaperu.dao.IClienteDAO;
import com.parrilla.chalaperu.dao.impl.ClienteDAOImpl;
import com.parrilla.chalaperu.model.Cliente;

import java.util.List;

public class ClienteService {

    private final IClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAOImpl();
    }

    public List<Cliente> ListarTodos() {
        return clienteDAO.ListarTodos();
    }

    public Cliente BuscarPorId(int id) {
        return clienteDAO.BuscarPorId(id);
    }

    public boolean Guardar(Cliente obj) throws Exception {
        return clienteDAO.Guardar(obj);
    }

    public boolean Actualizar(Cliente obj) throws Exception {
        return clienteDAO.Actualizar(obj);
    }

    public boolean Eliminar(int id) throws Exception {
        return clienteDAO.Eliminar(id);
    }

}

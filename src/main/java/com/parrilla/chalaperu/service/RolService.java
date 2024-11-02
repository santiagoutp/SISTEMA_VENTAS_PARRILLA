package com.parrilla.chalaperu.service;

import com.parrilla.chalaperu.dao.IRolDAO;
import com.parrilla.chalaperu.dao.impl.RolDAOImpl;
import com.parrilla.chalaperu.model.Rol;
import java.util.List;

public class RolService {

    private final IRolDAO rolDAO;

    public RolService() {
        this.rolDAO = new RolDAOImpl();
    }

    public List<Rol> ListarTodos() {
        return rolDAO.ListarTodos();
    }

}

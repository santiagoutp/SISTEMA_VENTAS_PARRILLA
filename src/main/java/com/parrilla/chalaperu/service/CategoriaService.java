package com.parrilla.chalaperu.service;

import com.parrilla.chalaperu.dao.ICategoriaDAO;
import com.parrilla.chalaperu.dao.impl.CategoriaDAOImpl;
import com.parrilla.chalaperu.model.Categoria;
import java.util.List;

public class CategoriaService {

    private final ICategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAOImpl();
    }

    public List<Categoria> ListarTodos() {
        return categoriaDAO.ListarTodos();
    }

    public Categoria BuscarPorId(int id) {
        return categoriaDAO.BuscarPorId(id);
    }

    public boolean Guardar(Categoria obj) throws Exception {
        if (categoriaDAO.ExisteCategoria(obj.getNombreCateg(), 0) == 0) {
            return categoriaDAO.Guardar(obj);
        } else {
            throw new Exception("La categoria " + obj.getNombreCateg() + " ya se encuentra registrado");
        }
    }

    public boolean Actualizar(Categoria obj) throws Exception {
        if (categoriaDAO.ExisteCategoria(obj.getNombreCateg(), obj.getIdCateg()) == 0) {
            return categoriaDAO.Actualizar(obj);
        } else {
            throw new Exception("La categoria " + obj.getNombreCateg() + " ya se encuentra registrado");
        }
    }

    public boolean Eliminar(int id) throws Exception {
        return categoriaDAO.Eliminar(id);
    }
}

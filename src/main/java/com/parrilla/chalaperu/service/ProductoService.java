package com.parrilla.chalaperu.service;

import com.parrilla.chalaperu.dao.IProductoDAO;
import com.parrilla.chalaperu.dao.impl.ProductoDAOImpl;
import com.parrilla.chalaperu.model.Producto;
import java.util.List;

public class ProductoService {

    private final IProductoDAO productoDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAOImpl();
    }

    public List<Producto> ListarTodos() {
        return productoDAO.ListarTodos();
    }

    public List<Producto> ListarTodosActivosDisponibles() {
        return productoDAO.ListarTodosActivosDisponibles();
    }

    public Producto BuscarPorId(int id) {
        return productoDAO.BuscarPorId(id);
    }

    public boolean Guardar(Producto obj) throws Exception {
        return productoDAO.Guardar(obj);
    }

    public boolean Actualizar(Producto obj) throws Exception {
        if (obj.getImagen() == null) {
            return productoDAO.ActualizarSinImagen(obj);
        } else {
            return productoDAO.Actualizar(obj);
        }
    }

    public boolean Eliminar(int id) throws Exception {
        return productoDAO.Eliminar(id);
    }

}

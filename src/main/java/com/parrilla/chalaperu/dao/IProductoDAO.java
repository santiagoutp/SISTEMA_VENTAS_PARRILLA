package com.parrilla.chalaperu.dao;

import com.parrilla.chalaperu.model.Producto;
import java.util.List;

public interface IProductoDAO {

    public List<Producto> ListarTodos();
    
    public List<Producto> ListarTodosActivosDisponibles();

    public Producto BuscarPorId(int id);

    public boolean Guardar(Producto obj) throws Exception;

    public boolean Actualizar(Producto obj) throws Exception;

    public boolean ActualizarSinImagen(Producto obj) throws Exception;

    public boolean Eliminar(int id) throws Exception;

}

package com.parrilla.chalaperu.dao;

import com.parrilla.chalaperu.model.Categoria;
import java.util.List;

public interface ICategoriaDAO {

    public List<Categoria> ListarTodos();

    public Categoria BuscarPorId(int id);

    public boolean Guardar(Categoria obj) throws Exception;

    public boolean Actualizar(Categoria obj) throws Exception;

    public boolean Eliminar(int id) throws Exception;
    
    public int ExisteCategoria(String nombre, int id);
}

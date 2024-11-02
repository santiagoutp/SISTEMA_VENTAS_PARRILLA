package com.parrilla.chalaperu.service;

import com.parrilla.chalaperu.dao.IUsuarioDAO;
import com.parrilla.chalaperu.dao.impl.UsuarioDAOImpl;
import com.parrilla.chalaperu.model.Usuario;
import com.parrilla.chalaperu.util.ConstantesApp;
import java.util.Collection;
import java.util.List;
import com.google.common.collect.Collections2;
import com.google.common.base.Predicate;
import java.util.ArrayList;

public class UsuarioService {

    private final IUsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public List<Usuario> ListarTodos() {
        return usuarioDAO.ListarTodos();
    }

    // Se aplica prediccion para listar los activos (Google Guava)
    public List<Usuario> listarVendedoresActivos() {
        int id = ConstantesApp.ROL_VENDEDOR;
        List<Usuario> lista = usuarioDAO.ListarPorIdRol(id);

        Collection<Usuario> usuariosActivos = Collections2.filter(lista, new Predicate<Usuario>() {
            @Override
            public boolean apply(Usuario usuario) {
                return usuario.getEstado() == 1;
            }
        });

        return new ArrayList<>(usuariosActivos);
    }
    
    // Se aplica prediccion para buscar el vendedor con id (Google Guava)
    public List<Usuario> listarVendedoresActivosPorId(int idUsuario) {
        int rolVendedorId = ConstantesApp.ROL_VENDEDOR;
        List<Usuario> lista = usuarioDAO.ListarPorIdRol(rolVendedorId);

        Collection<Usuario> usuariosActivos = Collections2.filter(lista, new Predicate<Usuario>() {
            @Override
            public boolean apply(Usuario usuario) {
                return usuario.getEstado() == 1 && usuario.getIdUsuario() == idUsuario;
            }
        });

        return new ArrayList<>(usuariosActivos);
    }

    public Usuario BuscarPorId(int id) {
        return usuarioDAO.BuscarPorId(id);
    }

    public boolean Guardar(Usuario obj) throws Exception {
        if (usuarioDAO.ExisteUsername(obj.getUsername(), 0) == 0) {
            return usuarioDAO.Guardar(obj);
        } else {
            throw new Exception("El usuario " + obj.getUsername() + " ya se encuentra registrado");
        }
    }

    public boolean Actualizar(Usuario obj) throws Exception {
        if (usuarioDAO.ExisteUsername(obj.getUsername(), obj.getIdUsuario()) == 0) {
            return usuarioDAO.Actualizar(obj);
        } else {
            throw new Exception("El usuario " + obj.getUsername() + " ya se encuentra registrado");
        }
    }

    public boolean Eliminar(int id) throws Exception {
        return usuarioDAO.Eliminar(id);
    }

    public Usuario Login(String username, String password) {
        return usuarioDAO.Login(username, password);
    }
}

package com.parrilla.chalaperu.dao.impl;

import com.parrilla.chalaperu.config.ConexionDB;
import com.parrilla.chalaperu.dao.IUsuarioDAO;
import com.parrilla.chalaperu.model.Usuario;
import com.parrilla.chalaperu.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements IUsuarioDAO {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAOImpl.class);
    private final Connection connection;

    public UsuarioDAOImpl() {
        this.connection = ConexionDB.getInstance().getConnection();
    }

    @Override
    public List<Usuario> ListarTodos() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Usuario> lista = new ArrayList<>();
        String query = "SELECT * FROM tb_usuario u inner join tb_rol r on r.id_rol = u.id_rol";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario obj = new Usuario();
                obj.setIdUsuario(rs.getInt("id_usuario"));
                obj.setNombres(rs.getString("nombres"));
                obj.setApellidos(rs.getString("apellidos"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setTelefono(rs.getString("telefono"));
                obj.setEstado(rs.getInt("estado"));
                obj.setIdRol(rs.getInt("id_rol"));
                obj.setNombreRol(rs.getString("nombre_rol"));
                lista.add(obj);
            }
            logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Usuario BuscarPorId(int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        Usuario obj = null;
        String query = "SELECT * FROM tb_usuario WHERE id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            logger.info("Método: {}, Parámetros: [id_usuario: {}]", methodName, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    obj = new Usuario();
                    obj.setIdUsuario(rs.getInt("id_usuario"));
                    obj.setNombres(rs.getString("nombres"));
                    obj.setApellidos(rs.getString("apellidos"));
                    obj.setUsername(rs.getString("username"));
                    obj.setPassword(rs.getString("password"));
                    obj.setTelefono(rs.getString("telefono"));
                    obj.setEstado(rs.getInt("estado"));
                    obj.setIdRol(rs.getInt("id_rol"));
                }
                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            }
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return obj;
    }

    @Override
    public boolean Guardar(Usuario obj) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "INSERT INTO tb_usuario (nombres, apellidos, username, password, telefono, estado, id_rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, obj.getNombres());
            ps.setString(2, obj.getApellidos());
            ps.setString(3, obj.getUsername());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getTelefono());
            ps.setInt(6, obj.getEstado());
            ps.setInt(7, obj.getIdRol());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Usuario agregado: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Actualizar(Usuario obj) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "UPDATE tb_usuario SET nombres = ?, apellidos = ?, username = ?, password = ?, telefono = ?, estado = ?, id_rol = ? WHERE id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, obj.getNombres());
            ps.setString(2, obj.getApellidos());
            ps.setString(3, obj.getUsername());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getTelefono());
            ps.setInt(6, obj.getEstado());
            ps.setInt(7, obj.getIdRol());
            ps.setInt(8, obj.getIdUsuario());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Usuario actualizado: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Eliminar(int id) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "DELETE FROM tb_usuario WHERE id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            logger.info("Método: {}, Parámetros: [id_usuario: {}]", methodName, id);
            boolean result = ps.executeUpdate() > 0;

            logger.info("Método: {}, Usuario eliminado con id: {} => {}", methodName, id, result);

            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Usuario Login(String username, String password) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        Usuario obj = null;
        String query = "SELECT u.* , r.nombre_rol FROM tb_usuario u "
                + " INNER JOIN tb_rol r ON r.id_rol = u.id_rol "
                + " WHERE username = ? AND password = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            logger.info("Método: {}, Parámetros: [username: {}, password: {}]", methodName, username, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    obj = new Usuario();
                    obj.setIdUsuario(rs.getInt("id_usuario")); 
                    obj.setNombres(rs.getString("nombres"));
                    obj.setApellidos(rs.getString("apellidos"));
                    obj.setUsername(rs.getString("username"));
                    obj.setPassword(rs.getString("password"));
                    obj.setTelefono(rs.getString("telefono"));
                    obj.setEstado(rs.getInt("estado"));
                    obj.setIdRol(rs.getInt("id_rol"));
                    obj.setNombreRol(rs.getString("nombre_rol"));
                }
                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            }
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return obj;
    }

    @Override
    public int ExisteUsername(String username, int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        int result = 0;
        String query = "SELECT * FROM tb_usuario WHERE username = ? AND  (?=0 or id_usuario!=?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setInt(2, id);
            ps.setInt(3, id);

            logger.info("Método: {}, Parámetros: [username: {}, id: {}]", methodName, username, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt(1);
                }
                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(result));
            }
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Usuario> ListarPorIdRol(int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Usuario> lista = new ArrayList<>();
        String query = "SELECT * FROM tb_usuario "
                + "u inner join tb_rol r on r.id_rol = u.id_rol"
                + "  where u.id_rol = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            logger.info("Método: {}, Parámetros: id rol: {}", methodName, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario obj = new Usuario();
                    obj.setIdUsuario(rs.getInt("id_usuario"));
                    obj.setNombres(rs.getString("nombres"));
                    obj.setApellidos(rs.getString("apellidos"));
                    obj.setUsername(rs.getString("username"));
                    obj.setPassword(rs.getString("password"));
                    obj.setTelefono(rs.getString("telefono"));
                    obj.setEstado(rs.getInt("estado"));
                    obj.setIdRol(rs.getInt("id_rol"));
                    obj.setNombreRol(rs.getString("nombre_rol"));
                    lista.add(obj);
                }
            }
            logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return lista;
    }
}

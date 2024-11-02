package com.parrilla.chalaperu.dao.impl;

import com.parrilla.chalaperu.config.ConexionDB;
import com.parrilla.chalaperu.dao.ICategoriaDAO;
import com.parrilla.chalaperu.model.Categoria;
import com.parrilla.chalaperu.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements ICategoriaDAO {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaDAOImpl.class);
    private final Connection connection;

    public CategoriaDAOImpl() {
        this.connection = ConexionDB.getInstance().getConnection();
    }

    @Override
    public List<Categoria> ListarTodos() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Categoria> lista = new ArrayList<>();
        String query = "SELECT * FROM tb_categoria";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria obj = new Categoria();
                obj.setIdCateg(rs.getInt("id_categ"));
                obj.setNombreCateg(rs.getString("nombre_categ"));
                obj.setEstado(rs.getInt("estado"));
                lista.add(obj);
            }
            logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Categoria BuscarPorId(int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        Categoria obj = null;
        String query = "SELECT * FROM tb_categoria WHERE id_categ = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            logger.info("Método: {}, Parámetros: [id_categ: {}]", methodName, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    obj = new Categoria();
                    obj.setIdCateg(rs.getInt("id_categ"));
                    obj.setNombreCateg(rs.getString("nombre_categ"));
                    obj.setEstado(rs.getInt("estado"));
                }
                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            }
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return obj;
    }

    @Override
    public boolean Guardar(Categoria obj) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "INSERT INTO tb_categoria (nombre_categ, estado) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, obj.getNombreCateg());
            ps.setInt(2, obj.getEstado());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            boolean result = ps.executeUpdate() > 0;
            
            logger.info("Método: {}, Categoria agregada: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Actualizar(Categoria obj) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "UPDATE tb_categoria SET nombre_categ = ?, estado = ? WHERE id_categ = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, obj.getNombreCateg());
            ps.setInt(2, obj.getEstado());
            ps.setInt(3, obj.getIdCateg());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Categoria actualizada: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Eliminar(int id) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "DELETE FROM tb_categoria WHERE id_categ = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            logger.info("Método: {}, Parámetros: [id_categ: {}]", methodName, id);

            boolean result = ps.executeUpdate() > 0;

            logger.info("Método: {}, Categoria eliminado con id: {} => {}", methodName, id, result);

            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int ExisteCategoria(String nombre, int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        int result = 0;
        String query = "SELECT * FROM tb_categoria WHERE nombre_categ = ? AND  (?=0 or id_categ!=?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setInt(2, id);
            ps.setInt(3, id);

            logger.info("Método: {}, Parámetros: [nombre: {}, id: {}]", methodName, nombre, id);

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
}

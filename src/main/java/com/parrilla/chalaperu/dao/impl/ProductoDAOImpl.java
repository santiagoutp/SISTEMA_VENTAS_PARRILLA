package com.parrilla.chalaperu.dao.impl;

import com.parrilla.chalaperu.config.ConexionDB;
import com.parrilla.chalaperu.dao.IProductoDAO;
import com.parrilla.chalaperu.model.Producto;
import com.parrilla.chalaperu.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements IProductoDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductoDAOImpl.class);
    private final Connection connection;

    public ProductoDAOImpl() {
        this.connection = ConexionDB.getInstance().getConnection();
    }

    @Override
    public List<Producto> ListarTodos() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Producto> lista = new ArrayList<>();
        String query = "SELECT p.*, c.nombre_categ FROM tb_producto p inner join tb_categoria c on c.id_categ = p.id_categ";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto obj = new Producto();
                obj.setIdProducto(rs.getInt("id_producto"));
                obj.setIdCateg(rs.getInt("id_categ"));
                obj.setNombre(rs.getString("nombre"));
                obj.setStock(rs.getInt("stock"));
                obj.setPrecio(rs.getDouble("precio"));
                obj.setDescripcion(rs.getString("descripcion"));
                obj.setPorcentajeIgv(rs.getDouble("porcentaje_igv"));
                obj.setEstado(rs.getInt("estado"));
                obj.setImagen(rs.getString("imagen"));
                obj.setNomCateg(rs.getString("nombre_categ"));
                lista.add(obj);
            }
            logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Producto BuscarPorId(int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        Producto obj = null;
        String query = "SELECT p.*, c.nombre_categ FROM tb_producto p"
                + " inner join tb_categoria c on c.id_categ = p.id_categ "
                + " WHERE id_producto = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            logger.info("Método: {}, Parámetros: [id_producto: {}]", methodName, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    obj = new Producto();
                    obj.setIdProducto(rs.getInt("id_producto"));
                    obj.setIdCateg(rs.getInt("id_categ"));
                    obj.setNombre(rs.getString("nombre"));
                    obj.setStock(rs.getInt("stock"));
                    obj.setPrecio(rs.getDouble("precio"));
                    obj.setDescripcion(rs.getString("descripcion"));
                    obj.setPorcentajeIgv(rs.getDouble("porcentaje_igv"));
                    obj.setEstado(rs.getInt("estado"));
                    obj.setImagen(rs.getString("imagen"));
                    obj.setNomCateg(rs.getString("nombre_categ"));
                }
                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            }
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return obj;
    }

    @Override
    public boolean Guardar(Producto obj) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "INSERT INTO tb_producto (id_categ, nombre, stock, precio, descripcion, porcentaje_igv, estado, imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, obj.getIdCateg());
            ps.setString(2, obj.getNombre());
            ps.setInt(3, obj.getStock());
            ps.setDouble(4, obj.getPrecio());
            ps.setString(5, obj.getDescripcion());
            ps.setDouble(6, obj.getPorcentajeIgv());
            ps.setInt(7, obj.getEstado());
            ps.setString(8, obj.getImagen());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            boolean result = ps.executeUpdate() > 0;

            logger.info("Método: {}, Producto agregado: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Actualizar(Producto obj) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "UPDATE tb_producto SET id_categ = ?, nombre = ?, stock = ?, precio = ?, descripcion = ?, porcentaje_igv = ?, estado = ?, imagen = ? WHERE id_producto = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, obj.getIdCateg());
            ps.setString(2, obj.getNombre());
            ps.setInt(3, obj.getStock());
            ps.setDouble(4, obj.getPrecio());
            ps.setString(5, obj.getDescripcion());
            ps.setDouble(6, obj.getPorcentajeIgv());
            ps.setInt(7, obj.getEstado());
            ps.setString(8, obj.getImagen());
            ps.setInt(9, obj.getIdProducto());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Producto actualizado: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean ActualizarSinImagen(Producto obj) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "UPDATE tb_producto SET id_categ = ?, nombre = ?, stock = ?, precio = ?, descripcion = ?,"
                + " porcentaje_igv = ?, estado = ? WHERE id_producto = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, obj.getIdCateg());
            ps.setString(2, obj.getNombre());
            ps.setInt(3, obj.getStock());
            ps.setDouble(4, obj.getPrecio());
            ps.setString(5, obj.getDescripcion());
            ps.setDouble(6, obj.getPorcentajeIgv());
            ps.setInt(7, obj.getEstado());
            ps.setInt(8, obj.getIdProducto());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Producto actualizado: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Eliminar(int id) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "DELETE FROM tb_producto WHERE id_producto = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            logger.info("Método: {}, Parámetros: [id_producto: {}]", methodName, id);

            boolean result = ps.executeUpdate() > 0;

            logger.info("Método: {}, Producto eliminado con id: {} => {}", methodName, id, result);

            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Producto> ListarTodosActivosDisponibles() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Producto> lista = new ArrayList<>();
        String query = "SELECT p.*, c.nombre_categ"
                + " FROM tb_producto p inner join tb_categoria c on c.id_categ = p.id_categ"
                + " WHERE p.stock > 0 AND p.estado = 1";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto obj = new Producto();
                obj.setIdProducto(rs.getInt("id_producto"));
                obj.setIdCateg(rs.getInt("id_categ"));
                obj.setNombre(rs.getString("nombre"));
                obj.setStock(rs.getInt("stock"));
                obj.setPrecio(rs.getDouble("precio"));
                obj.setDescripcion(rs.getString("descripcion"));
                obj.setPorcentajeIgv(rs.getDouble("porcentaje_igv"));
                obj.setEstado(rs.getInt("estado"));
                obj.setImagen(rs.getString("imagen"));
                obj.setNomCateg(rs.getString("nombre_categ"));
                lista.add(obj);
            }
            logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return lista;
    }

}

package com.parrilla.chalaperu.dao.impl;

import com.parrilla.chalaperu.config.ConexionDB;
import com.parrilla.chalaperu.dao.IClienteDAO;
import com.parrilla.chalaperu.model.Cliente;
import com.parrilla.chalaperu.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements IClienteDAO {

    private static final Logger logger = LoggerFactory.getLogger(ClienteDAOImpl.class);
    private final Connection connection;

    public ClienteDAOImpl() {
        this.connection = ConexionDB.getInstance().getConnection();
    }

    @Override
    public List<Cliente> ListarTodos() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Cliente> lista = new ArrayList<>();
        String query = "SELECT * FROM tb_cliente";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombres"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setDni(rs.getString("dni"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setEstado(rs.getInt("estado"));
                lista.add(cliente);
            }
            logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Cliente BuscarPorId(int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        Cliente cliente = null;
        String query = "SELECT * FROM tb_cliente WHERE id_cliente = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            logger.info("Método: {}, Parámetros: [id_cliente: {}]", methodName, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNombre(rs.getString("nombres"));
                    cliente.setApellidos(rs.getString("apellidos"));
                    cliente.setDni(rs.getString("dni"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setEstado(rs.getInt("estado"));
                }
                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(cliente));
            }
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }
        return cliente;
    }

    @Override
    public boolean Guardar(Cliente cliente) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "INSERT INTO tb_cliente (nombres, apellidos, dni, telefono, direccion, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getEstado());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(cliente));
            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Cliente agregado: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Actualizar(Cliente cliente) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "UPDATE tb_cliente SET nombres = ?, apellidos = ?, dni = ?, telefono = ?, direccion = ?, estado = ? WHERE id_cliente = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getEstado());
            ps.setInt(7, cliente.getIdCliente());

            logger.info("Método: {}, Parámetros: {}", methodName, JsonUtil.toJsonValueAsString(cliente));
            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Cliente actualizado: {}", methodName, JsonUtil.toJsonValueAsString(result));
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean Eliminar(int id) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String query = "DELETE FROM tb_cliente WHERE id_cliente = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            logger.info("Método: {}, Parámetros: [id_cliente: {}]", methodName, id);

            boolean result = ps.executeUpdate() > 0;
            logger.info("Método: {}, Cliente eliminado con id: {} => {}", methodName, id, result);
            return result;
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }
}

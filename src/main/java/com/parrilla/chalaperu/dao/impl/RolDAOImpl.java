package com.parrilla.chalaperu.dao.impl;

import com.parrilla.chalaperu.config.ConexionDB;
import com.parrilla.chalaperu.dao.IRolDAO;
import com.parrilla.chalaperu.model.Rol;
import com.parrilla.chalaperu.util.JsonUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolDAOImpl implements IRolDAO {

    private static final Logger logger = LoggerFactory.getLogger(RolDAOImpl.class);
    private final Connection connection;

    public RolDAOImpl() {
        this.connection = ConexionDB.getInstance().getConnection();
    }

    @Override
    public List<Rol> ListarTodos() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Rol> lista = new ArrayList<>();
        String query = "SELECT * FROM tb_rol";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol obj = new Rol();
                obj.setIdRol(rs.getInt("id_rol"));
                obj.setNombreRol(rs.getString("nombre_rol"));
                lista.add(obj);
            }
            logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            e.printStackTrace();
        }

        return lista;
    }

}

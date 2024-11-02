package com.parrilla.chalaperu.dao.impl;

import com.parrilla.chalaperu.config.ConexionDB;
import com.parrilla.chalaperu.dao.IVentaDAO;
import com.parrilla.chalaperu.model.DetalleVenta;
import com.parrilla.chalaperu.model.Producto;
import com.parrilla.chalaperu.model.Venta;
import com.parrilla.chalaperu.util.JsonUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VentaDAOImpl implements IVentaDAO {

    private static final Logger logger = LoggerFactory.getLogger(VentaDAOImpl.class);
    private final Connection connection;

    public VentaDAOImpl() {
        this.connection = ConexionDB.getInstance().getConnection();
    }

    @Override
    public boolean Guardar(Venta venta) {
        boolean resultado = false;
        String queryCabecera = "INSERT INTO tb_cabecera_venta(id_cliente, valor_pagar, fecha_venta, estado, id_vendedor) VALUES (?, ?, NOW(), ?, ?)";
        String queryDetalle = "{CALL sp_insertar_detalle_venta(?, ?, ?, ?, ?, ?, ?)}";

        Connection cn = null;

        try {
            cn = ConexionDB.getInstance().getConnection();
            cn.setAutoCommit(false);

            try (PreparedStatement psCabecera = cn.prepareStatement(queryCabecera,
                    Statement.RETURN_GENERATED_KEYS)) {
                psCabecera.setInt(1, venta.getIdCliente());
                psCabecera.setDouble(2, venta.getValorPagar());
                psCabecera.setInt(3, venta.getEstado());
                psCabecera.setInt(4, venta.getIdVendedor());

                logger.info("Parámetros tb_cabecera_venta - id_cliente: {}, valor_pagar: {}, estado: {}, id_vendedor: {}",
                        venta.getIdCliente(), venta.getValorPagar(), venta.getEstado(), venta.getIdVendedor());

                int rowsInserted = psCabecera.executeUpdate();
                if (rowsInserted > 0) {
                    try (ResultSet rs = psCabecera.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idVenta = rs.getInt(1);

                            try (PreparedStatement psDetalle = cn.prepareStatement(queryDetalle)) {
                                for (DetalleVenta detalle : venta.getDetalles()) {
                                    double precioUnitario = detalle.getProducto().getPrecio();
                                    int cantidad = detalle.getCantidad();
                                    double subtotal = precioUnitario * cantidad;
                                    double porcentajeIgv = detalle.getProducto().getPorcentajeIgv();
                                    double totalPagar = subtotal + (subtotal * (porcentajeIgv / 100.0));

                                    psDetalle.setInt(1, idVenta);
                                    psDetalle.setInt(2, detalle.getProducto().getIdProducto());
                                    psDetalle.setInt(3, cantidad);
                                    psDetalle.setDouble(4, precioUnitario);
                                    psDetalle.setDouble(5, subtotal);
                                    psDetalle.setDouble(6, porcentajeIgv);
                                    psDetalle.setDouble(7, totalPagar);

                                    logger.info("Parámetros tb_detalle_venta - id_venta: {}, id_producto: {}, cantidad: {}, precio_unitario: {}, subtotal: {}, porcentaje_igv: {}, total_pagar: {}",
                                            idVenta, detalle.getProducto().getIdProducto(), cantidad, precioUnitario, subtotal, porcentajeIgv * 100, totalPagar);

                                    psDetalle.addBatch();
                                }

                                psDetalle.executeBatch();
                            }
                        }
                    }
                }

                cn.commit();
                resultado = true;
            }
        } catch (Exception e) {
            logger.error("Error al guardar venta: {}", e.getMessage(), e);
            if (cn != null) {
                try {
                    cn.rollback();
                    logger.info("Rollback realizado debido a un error en la transacción");
                } catch (SQLException rollbackException) {
                    logger.error("Error al realizar rollback: {}", rollbackException.getMessage(), rollbackException);
                }
            }
        }
        return resultado;
    }

    @Override
    public List<Venta> ConsultarVentasPorFecha(String fechaInicio, String fechaFin) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<Venta> lista = new ArrayList<>();
        String query = "SELECT v.id_venta, CONCAT(c.apellidos, ' ', c.nombres)  AS nombre_cliente,"
                + " CONCAT(u.apellidos, ' ', u.nombres) AS nombre_vendedor , v.valor_pagar AS total,\n"
                + " v.fecha_venta AS fecha,v.estado\n"
                + " FROM tb_cabecera_venta v\n"
                + " JOIN tb_cliente c ON v.id_cliente = c.id_cliente\n"
                + " JOIN tb_usuario u ON v.id_vendedor = u.id_usuario\n"
                + " WHERE  STR_TO_DATE(v.fecha_venta, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%Y-%m-%d') AND STR_TO_DATE(?, '%Y-%m-%d')\n"
                + " ORDER BY v.fecha_venta";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);

            logger.info("Método: {}, Parámetros: [Fecha Inicio: {}, Fecha Fin: {}]", methodName, fechaInicio, fechaFin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("id_venta"));
                    venta.setNombreCliente(rs.getString("nombre_cliente"));
                    venta.setNombreVendedor(rs.getString("nombre_vendedor"));
                    venta.setValorPagar(rs.getDouble("total"));
                    venta.setFechaVenta(rs.getString("fecha"));
                    venta.setEstado(rs.getInt("estado"));

                    lista.add(venta);
                }

                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(lista));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public Venta BuscarPorId(int id) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        Venta obj = null;
        String query = "SELECT v.id_venta, CONCAT(c.apellidos, ' ', c.nombres)  AS nombre_cliente,"
                + " CONCAT(u.apellidos, ' ', u.nombres) AS nombre_vendedor , v.valor_pagar AS total,\n"
                + " v.fecha_venta AS fecha,v.estado\n"
                + " FROM tb_cabecera_venta v\n"
                + " JOIN tb_cliente c ON v.id_cliente = c.id_cliente\n"
                + " JOIN tb_usuario u ON v.id_vendedor = u.id_usuario\n"
                + " WHERE  v.id_venta = ? ";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);

            logger.info("Método: {}, Parámetros: [Id Venta: {}]", methodName, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    obj = new Venta();
                    obj.setIdVenta(rs.getInt("id_venta"));
                    obj.setNombreCliente(rs.getString("nombre_cliente"));
                    obj.setNombreVendedor(rs.getString("nombre_vendedor"));
                    obj.setValorPagar(rs.getDouble("total"));
                    obj.setFechaVenta(rs.getString("fecha"));
                    obj.setEstado(rs.getInt("estado"));

                }

                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }

        return obj;
    }

    @Override
    public List<DetalleVenta> ConsultarDetallesVentaPorId(int idVenta) {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        List<DetalleVenta> detalles = new ArrayList<>();
        String query = "SELECT d.precio_unitario, d.cantidad, d.subtotal, "
                + "d.porcentaje_igv, d.total_pagar, p.nombre AS nombre_producto, "
                + "c.nombre_categ AS nombre_categoria, p.imagen "
                + "FROM tb_detalle_venta d "
                + "INNER JOIN tb_producto p ON p.id_producto = d.id_producto "
                + "INNER JOIN tb_categoria c ON c.id_categ = p.id_categ "
                + "WHERE d.id_venta = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idVenta);

            logger.info("Método: {}, Parámetros: [Id Venta: {}]", methodName, idVenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    Producto producto = new Producto();

                    producto.setNombre(rs.getString("nombre_producto"));
                    producto.setNomCateg(rs.getString("nombre_categoria"));
                    producto.setImagen(rs.getString("imagen"));
                    producto.setPorcentajeIgv(rs.getDouble("porcentaje_igv"));
                    producto.setPrecio(rs.getDouble("precio_unitario"));

                    detalle.setProducto(producto);
                    detalle.setCantidad(rs.getInt("cantidad"));

                    detalles.add(detalle);
                }

                logger.info("Método: {}, Response: {}", methodName, JsonUtil.toJsonValueAsString(detalles));
            }
        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
        }

        return detalles;
    }

    @Override
    public boolean AnularVenta(int id) throws Exception {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        boolean resultado = false;
        String query = "{CALL sp_anular_venta(?)}";

      try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);

            logger.info("Método: {}, Parámetros: [Id Venta: {}]", methodName, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                resultado = true;
                logger.info("Venta anulada correctamente - id_venta: {}", id);
            } else {
                logger.warn("No se encontró la venta con id: {}", id);
            }

        } catch (Exception e) {
            logger.error("Error en el método: {}: Error: {}", methodName, e.getMessage(), e);
            throw e;
        }
        return resultado;
    }
}

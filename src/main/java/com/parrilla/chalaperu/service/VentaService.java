package com.parrilla.chalaperu.service;

import com.parrilla.chalaperu.dao.IVentaDAO;
import com.parrilla.chalaperu.dao.impl.VentaDAOImpl;
import com.parrilla.chalaperu.model.Venta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaService {

    private final IVentaDAO ventaDAO;

    public VentaService() {
        this.ventaDAO = new VentaDAOImpl();
    }

    public boolean Guardar(Venta obj) throws Exception {
        return ventaDAO.Guardar(obj);
    }

    public List<Venta> ConsultarVentasPorFecha(String fechaInicio, String fechaFin) {
        return ventaDAO.ConsultarVentasPorFecha(fechaInicio, fechaFin);
    }

    public Venta BuscarPorId(int id) {
        Venta obj = ventaDAO.BuscarPorId(id);
        obj.setDetalles(ventaDAO.ConsultarDetallesVentaPorId(id));
        return obj;
    }

    public boolean AnularVenta(int id) throws Exception {
        return ventaDAO.AnularVenta(id);
    }
}

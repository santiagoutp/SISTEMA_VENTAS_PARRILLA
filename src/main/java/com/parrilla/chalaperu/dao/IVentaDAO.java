package com.parrilla.chalaperu.dao;

import com.parrilla.chalaperu.model.DetalleVenta;
import com.parrilla.chalaperu.model.Venta;
import java.util.List;

public interface IVentaDAO {

    public boolean Guardar(Venta obj) throws Exception;

    public List<Venta> ConsultarVentasPorFecha(String fechaInicio, String fechaFin);

    public Venta BuscarPorId(int id);

    public List<DetalleVenta> ConsultarDetallesVentaPorId(int idVenta);

    public boolean AnularVenta(int id) throws Exception;
}

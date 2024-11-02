package com.parrilla.chalaperu.util;

import com.parrilla.chalaperu.model.DetalleVenta;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CarritoUtil {

    public void AgregarCarrito(HttpServletRequest request, DetalleVenta detalle) {
        ArrayList<DetalleVenta> lista = ObtenerSesion(request);
        int posc = ExisteProducto(lista, detalle.getProducto().getIdProducto());

        if (posc == -1) {
            lista.add(detalle);
        } else {
            DetalleVenta objDet = lista.get(posc);
            objDet.AumentarCantidad(detalle.getCantidad());
        }

        GuardarSesion(request, lista);
    }

    public void RemoverItemCarrito(HttpServletRequest request, int indice) {
        ArrayList<DetalleVenta> lista = ObtenerSesion(request);
        lista.remove(indice);
        GuardarSesion(request, lista);
    }

    public void RemoverCarrito(HttpServletRequest request) {
        ArrayList<DetalleVenta> lista = ObtenerSesion(request);
        lista.removeAll(lista);
        GuardarSesion(request, lista);
    }

    public ArrayList<DetalleVenta> ObtenerSesion(HttpServletRequest request) {
        ArrayList<DetalleVenta> lista;

        if (request.getSession().getAttribute("carrito") == null) {
            lista = new ArrayList<>();
        } else {
            lista = (ArrayList<DetalleVenta>) request.getSession().getAttribute("carrito");
        }
        return lista;
    }

    public int ExisteProducto(ArrayList<DetalleVenta> lista, int idProd) {

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getProducto().getIdProducto() == idProd) {
                return i;
            }
        }
        return -1;
    }

    public void GuardarSesion(HttpServletRequest request, ArrayList<DetalleVenta> lista) {
        request.getSession().setAttribute("carrito", lista);
    }

    public double SubTotal(List<DetalleVenta> lista) {
        double total = 0;
        for (DetalleVenta item : lista) {
            total += item.Importe();
        }
        return total;
    }

    public double IGV(List<DetalleVenta> lista) {
        double subTotal = SubTotal(lista);
        return subTotal * 0.18;
    }

    public double Total(double subTotal, double igv) {
        return subTotal + igv;
    }
}

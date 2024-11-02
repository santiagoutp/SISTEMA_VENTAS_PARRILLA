package com.parrilla.chalaperu.model;

public class DetalleVenta {

    private Producto producto;
    private int cantidad;

    public double Importe() {
        return producto.getPrecioCnIGV()* cantidad;
    }

    public void AumentarCantidad(int cantidad) {
        this.cantidad += cantidad;
        if (this.cantidad > producto.getStock()) {
            this.cantidad = producto.getStock();
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

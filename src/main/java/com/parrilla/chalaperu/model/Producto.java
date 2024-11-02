package com.parrilla.chalaperu.model;

public class Producto {

    private int idProducto;
    private int idCateg;
    private String nombre;
    private int stock;
    private double precio;
    private String descripcion;
    private double porcentajeIgv;
    private int estado;
    private String imagen;
    private String nomCateg;
    private double precioCnIGV;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCateg() {
        return idCateg;
    }

    public void setIdCateg(int idCateg) {
        this.idCateg = idCateg;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPorcentajeIgv() {
        return porcentajeIgv;
    }

    public void setPorcentajeIgv(double porcentajeIgv) {
        this.porcentajeIgv = porcentajeIgv;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNomCateg() {
        return nomCateg;
    }

    public void setNomCateg(String nomCateg) {
        this.nomCateg = nomCateg;
    }

    public double getPrecioCnIGV() {
        this.precioCnIGV = precio + (precio * porcentajeIgv / 100.0);
        return precioCnIGV;
    }

    public void setPrecioCnIGV(double precioCnIGV) {
        this.precioCnIGV = precioCnIGV;
    }

}

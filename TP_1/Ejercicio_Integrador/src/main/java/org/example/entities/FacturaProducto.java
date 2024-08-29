package org.example.entities;

public class FacturaProducto {
    private int id_factura;
    private int id_producto;
    private int cantidad;

    public FacturaProducto(int id_factura, int id_producto, int cantidad) {
        this.id_factura = id_factura;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public int getId_factura() {
        return id_factura;
    }

    public int getId_producto() {
        return id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

package org.example.entities;

public class Producto {
    private int id_producto;
    private String nombre;
    private float valor;

    public Producto(int id_producto, String nombre, float valor) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Producto() {

    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id_producto=" + id_producto +
                ", nombre='" + nombre + '\'' +
                ", valor=" + valor +
                '}';
    }
}

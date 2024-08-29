package org.example;

import org.example.dao.cliente.ClienteDAOImpl;
import org.example.dao.factura.FacturaDAO;
import org.example.dao.factura.FacturaDAOImpl;
import org.example.dao.factura_producto.FacturaProductoDAO;
import org.example.dao.factura_producto.FacturaProductoDAOImpl;
import org.example.dao.producto.ProductoDAO;
import org.example.dao.producto.ProductoDAOImpl;
import org.example.entities.Cliente;
import org.example.esquemaDB.CreadorDeTablas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        String url = "jdbc:mysql://localhost:3306/bd_integrador";
        String user = "root";
        String password = "";

        CreadorDeTablas ct = new CreadorDeTablas(url, user, password);

        ClienteDAOImpl cliente = new ClienteDAOImpl(url, user, password);
        FacturaDAO factura = new FacturaDAOImpl(url, user, password);
        FacturaProductoDAO facturaProducto = new FacturaProductoDAOImpl(url, user, password);
        ProductoDAO producto = new ProductoDAOImpl(url, user, password);

        cliente.insertarDesdeArchivosCSV("C:\\Users\\Zubigaray\\Documents\\Arquitecturas\\TP_1\\Ejercicio_Integrador\\src\\main\\resources\\archivos\\clientes.csv");
        factura.insertarDesdeArchivosCSV("C:\\Users\\Zubigaray\\Documents\\Arquitecturas\\TP_1\\Ejercicio_Integrador\\src\\main\\resources\\archivos\\facturas.csv");
        producto.insertarDesdeArchivosCSV("C:\\Users\\Zubigaray\\Documents\\Arquitecturas\\TP_1\\Ejercicio_Integrador\\src\\main\\resources\\archivos\\productos.csv");
        facturaProducto.insertarDesdeArchivosCSV("C:\\Users\\Zubigaray\\Documents\\Arquitecturas\\TP_1\\Ejercicio_Integrador\\src\\main\\resources\\archivos\\facturas-productos.csv");

        System.out.println(producto.obtenerElQueMasRecaudo());
        
    }
}
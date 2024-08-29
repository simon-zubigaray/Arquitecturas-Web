package org.example.esquemaDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreadorDeTablas {

    private Connection connection;

    public CreadorDeTablas (String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        crearTablas();
    }

    public void crearTablas() throws SQLException {

        String tableCliente = "CREATE TABLE IF NOT EXISTS clientes (" +
                "idCliente INT NOT NULL AUTO_INCREMENT, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "PRIMARY KEY (idCliente))";

        String tableProducto = "CREATE TABLE IF NOT EXISTS productos (" +
                "idProducto INT NOT NULL AUTO_INCREMENT, " +
                "nombre VARCHAR(45), " +
                "valor FLOAT, " +
                "PRIMARY KEY (idProducto))";

        String tableFactura = "CREATE TABLE IF NOT EXISTS facturas (" +
                "idFactura INT NOT NULL AUTO_INCREMENT, " +
                "idCliente INT, " +
                "PRIMARY KEY (idFactura), " +
                "FOREIGN KEY (idCliente) REFERENCES clientes(idCliente))";

        String tableFacturaProducto = "CREATE TABLE IF NOT EXISTS factura_producto (" +
                "idFactura INT, " +
                "idProducto INT, " +
                "cantidad INT, " +
                "PRIMARY KEY (idFactura, idProducto), " +
                "FOREIGN KEY (idFactura) REFERENCES facturas(idFactura), " +
                "FOREIGN KEY (idProducto) REFERENCES productos(idProducto))";

        connection.prepareStatement(tableCliente).execute();
        connection.prepareStatement(tableProducto).execute();
        connection.prepareStatement(tableFactura).execute();
        connection.prepareStatement(tableFacturaProducto).execute();
        connection.commit();
    }

}

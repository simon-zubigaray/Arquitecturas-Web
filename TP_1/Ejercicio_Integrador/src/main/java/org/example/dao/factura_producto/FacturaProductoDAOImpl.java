package org.example.dao.factura_producto;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Factura;
import org.example.entities.FacturaProducto;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaProductoDAOImpl implements FacturaProductoDAO{

    private Connection connection;
    private String driver;

    @Override
    public void insertar(FacturaProducto facturaProducto) {

    }

    @Override
    public void insertarDesdeArchivosCSV(String path) throws IOException {
        String sql = "INSERT INTO facturas (idFactura, idCliente, cantidad) VALUES (?, ?, ?)";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))
        ){

            for (CSVRecord row : parser) {
                try {
                    //En el get van los nombres de las columnas de la tabla de la base de datos, en este caso factura_producto
                    stmt.setInt(1, Integer.parseInt(row.get("idFactura")));
                    stmt.setInt(2, Integer.parseInt(row.get("idCliente")));
                    stmt.setInt(3, Integer.parseInt(row.get("cantidad")));
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    connection.rollback();  // Rollback en caso de error
                }
            }

            connection.commit();  // Commit de la transacción completa después de todas las inserciones
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FacturaProducto obtenerPorId(int idFactura, int idCliente) {
        String sql = "SELECT * FROM facturas_producto WHERE idFactura = ? AND idCliente = ?";
        FacturaProducto facturaProducto = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            stmt.setInt(2, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    facturaProducto = new FacturaProducto(
                            rs.getInt("idFactura"),
                            rs.getInt("idCliente"),
                            rs.getInt("cantidad")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facturaProducto;
    }

    @Override
    public List<FacturaProducto> obtenerTodas() {
        String sql = "SELECT * FROM facturas_producto";
        List<FacturaProducto> facturasProductos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                facturasProductos.add(new FacturaProducto(
                        rs.getInt("idFactura"),
                        rs.getInt("idCliente"),
                        rs.getInt("cantidad")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facturasProductos;
    }

    @Override
    public void actualizar(int idFactura, int idCliente, int cantidad) {
        String sql = "UPDATE facturas_producto SET cantidad = ? WHERE idFactura = ? AND idCliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            int rowsUpdated = stmt.executeUpdate(); // Ejecuta la actualización
            if (rowsUpdated == 0) {
                throw new RuntimeException("No se pudo actualizar el producto con id " + idFactura);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(int idFactura, int idCliente) {
        String sql = "DELETE FROM facturas_producto WHERE idFactura = ? AND idCliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idFactura); // Asigna el valor del ID al parámetro
            stmt.setLong(2, idCliente);
            stmt.executeUpdate(); // Ejecuta la eliminación
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
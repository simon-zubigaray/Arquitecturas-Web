package org.example.dao.factura;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Factura;
import org.example.entities.Producto;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO{

    private Connection connection;
    private String driver;

    @Override
    public void insertar(Factura factura) {
        String sql = "INSERT INTO facturas (idFactura, idCliente) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, factura.getIdFactura());
            stmt.setInt(2, factura.getIdCliente());
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertarDesdeArchivosCSV(String path) throws IOException {
        String sql = "INSERT INTO facturas (idFactura, idCliente) VALUES (?, ?)";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))
        ){

            for (CSVRecord row : parser) {
                try {
                    //En el get van los nombres de las columnas de la tabla de la base de datos, en este caso facturas
                    stmt.setInt(1, Integer.parseInt(row.get("idFactura")));
                    stmt.setInt(2, Integer.parseInt(row.get("idCliente")));
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
    public Factura obtenerPorId(int id) {
        String sql = "SELECT * FROM facturas WHERE idFactura = ?";
        Factura factura = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    factura = new Factura(
                            rs.getInt("idFactura"),
                            rs.getInt("idCliente")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return factura;
    }

    @Override
    public List<Factura> obtenerTodas() {
        String sql = "SELECT * FROM productos";
        List<Factura> facturas = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                facturas.add(new Factura(
                        rs.getInt("idFactura"),
                        rs.getInt("idCliente")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facturas;
    }

    @Override
    public void actualizar(int idFactura, int nuevaIdCliente) {
        String sql = "UPDATE facturas SET idCliente = ? WHERE idFactura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nuevaIdCliente);
            stmt.setInt(2, idFactura);
            int rowsUpdated = stmt.executeUpdate(); // Ejecuta la actualización
            connection.commit();

            if (rowsUpdated == 0) {
                throw new RuntimeException("No se pudo actualizar el producto con id " + idFactura);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM facturas WHERE idFactura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Asigna el valor del ID al parámetro
            stmt.executeUpdate(); // Ejecuta la eliminación
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

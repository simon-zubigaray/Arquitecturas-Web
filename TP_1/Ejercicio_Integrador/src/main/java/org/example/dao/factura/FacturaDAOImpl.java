package org.example.dao.factura;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Factura;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO{

    private Connection connection;
    private String driver;

    @Override
    public void insertar(Factura factura) {

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
        return null;
    }

    @Override
    public List<Factura> obtenerTodas() {
        return List.of();
    }

    @Override
    public void actualizar(Factura factura) {

    }

    @Override
    public void eliminar(Long id) {

    }
}

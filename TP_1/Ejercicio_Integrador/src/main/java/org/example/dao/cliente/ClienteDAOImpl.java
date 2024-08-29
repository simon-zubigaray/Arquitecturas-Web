package org.example.dao.cliente;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Cliente;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO{

    private Connection connection;
    private String driver;

    @Override
    public void insertar(Cliente cliente) {

    }

    @Override
    public void insertarDesdeArchivosCSV(String path) throws IOException {
        String sql = "INSERT INTO clientes (idCliente, nombre, email) VALUES (?, ?, ?)";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))
        ){

            for (CSVRecord row : parser) {
                try {
                    //En el get van los nombres de las columnas de la tabla de la base de datos, en este caso clientes
                    stmt.setInt(1, Integer.parseInt(row.get("idCliente")));
                    stmt.setString(2, row.get("nombre"));
                    stmt.setFloat(3, Float.parseFloat(row.get("email")));
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
    public Cliente obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<Cliente> obtenerTodas() {
        return List.of();
    }

    @Override
    public void actualizar(Cliente cliente) {

    }

    @Override
    public void eliminar(Long id) {

    }
}

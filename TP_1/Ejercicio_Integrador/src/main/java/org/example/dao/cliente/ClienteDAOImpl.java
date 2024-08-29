package org.example.dao.cliente;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Cliente;
import org.example.entities.Producto;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO{

    private Connection connection;
    private String url;

    public ClienteDAOImpl (String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
    }

    @Override
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (idCliente, nombre, email) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getEmail());
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                    stmt.setString(3, row.get("email"));
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
        String sql = "SELECT * FROM clientes WHERE idCliente = ?";
        Cliente cliente = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getInt("idCliente"),
                            rs.getString("nombre"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    @Override
    public List<Cliente> obtenerTodas() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    @Override
    public void actualizar(int id, String nuevoNombre, String nuevoEmail) {
        String sql = "UPDATE clientes SET nombre = ?, email = ? WHERE idCliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevoEmail);
            stmt.setInt(3, id);
            int rowsUpdated = stmt.executeUpdate(); // Ejecuta la actualización
            connection.commit();

            if (rowsUpdated == 0) {
                throw new RuntimeException("No se pudo actualizar el producto con id " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE idCliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Asigna el valor del ID al parámetro
            stmt.executeUpdate(); // Ejecuta la eliminación
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

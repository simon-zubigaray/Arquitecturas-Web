package org.example.dao.producto;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Producto;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO{

    private Connection connection;
    private String driver;

    @Override
    public void insertar(Producto producto) {
        String sql = "INSERT INTO productos (idProducto, nombre, valor) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, producto.getId_producto());
            stmt.setString(2, producto.getNombre());
            stmt.setFloat(3, producto.getValor());
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertarDesdeArchivosCSV(String path) throws IOException {
        String sql = "INSERT INTO productos (idProducto, nombre, valor) VALUES (?, ?, ?)";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))
        ){

            for (CSVRecord row : parser) {
                try {
                    //En el get van los nombres de las columnas de la tabla de la base de datos, en este caso productos
                    stmt.setInt(1, Integer.parseInt(row.get("idProducto")));
                    stmt.setString(2, row.get("nombre"));
                    stmt.setFloat(3, Float.parseFloat(row.get("valor")));
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
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE idProducto = ?";
        Producto producto = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto(
                            rs.getInt("idProducto"),
                            rs.getString("nombre"),
                            rs.getFloat("valor")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public Producto obtenerElQueMasRecaudo() {
        String sql = "SELECT p.idProducto, p.nombre, p.valor " +
                     "FROM Factura_Producto as fp " +
                     "INNER JOIN productos as p ON fp.idProducto = p.idProducto " +
                     "ORDER BY (p.valor * fp.cantidad) DESC LIMIT 1;";
        Producto producto = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto(
                            rs.getInt("idProducto"),
                            rs.getString("nombre"),
                            rs.getFloat("valor")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public List<Producto> obtenerTodas() {
        String sql = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getFloat("valor")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public void actualizar(int id, String nuevoNombre, float nuevoValor) {
        String sql = "UPDATE productos SET nombre = ?, valor = ? WHERE idProducto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuevoNombre);
            stmt.setFloat(2, nuevoValor);
            stmt.setInt(3, id);
            int rowsUpdated = stmt.executeUpdate(); // Ejecuta la actualización

            if (rowsUpdated == 0) {
                throw new RuntimeException("No se pudo actualizar el producto con id " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE idProducto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id); // Asigna el valor del ID al parámetro
            stmt.executeUpdate(); // Ejecuta la eliminación
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

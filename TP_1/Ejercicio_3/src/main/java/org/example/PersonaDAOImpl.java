package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonaDAOImpl implements PersonaDAO {

    private Connection connection;
    private String driver;

    public PersonaDAOImpl(String url, String user, String password, String driver) throws SQLException {
        this.driver = driver;
        this.connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        createTables(connection);
    }

    private void createTables(Connection connection) throws SQLException {
        String driver_derby = "org.apache.derby.jdbc.EmbeddedDriver";
        String table = "";

        if(!Objects.equals(this.driver, driver_derby)){
            table = "CREATE TABLE IF NOT EXISTS personas (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(255), " +
                    "apellido VARCHAR(255), " +
                    "edad INT)";
        } else {
            table = "CREATE TABLE personas (" +
                    "id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                    "nombre VARCHAR(255), " +
                    "apellido VARCHAR(255), " +
                    "edad INT)";
        }

        connection.prepareStatement(table).execute();
        connection.commit();
    }

    @Override
    public void insertar(Persona persona) {
        String sql = "INSERT INTO personas (nombre, apellido, edad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setInt(3, persona.getEdad());
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Persona obtenerPorId(Long id) {
        String sql = "SELECT * FROM personas WHERE id = ?";
        Persona persona = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    persona = new Persona(
                            rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("edad")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }

    @Override
    public List<Persona> obtenerTodas() {
        String sql = "SELECT * FROM personas";
        List<Persona> personas = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                personas.add(new Persona(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    @Override
    public void actualizar(Persona persona) {
        String sql = "UPDATE personas SET nombre = ?, apellido = ?, edad = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setInt(3, persona.getEdad());
            stmt.setLong(4, persona.getId());
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM personas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
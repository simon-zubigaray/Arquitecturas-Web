package org.example;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDeDatos {
    public static void main(String[] args){
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        String uri = "jdbc:mysql://localhost:3306/exampleDB";

        try {
            Connection conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
            createTables(conn);
            addPersona(conn, 1, "Juan", 19);
            addPersona(conn, 2, "Ana", 34);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTables(Connection conn) throws SQLException{
        String table = "CREATE TABLE persona(" +
                "id INT," +
                "nombre VARCHAR(500)," +
                "edad INT," +
                "PRIMARY KEY(id))";

        conn.prepareStatement(table).execute();
        conn.commit();
    }

    public static void addPersona(Connection conn, int id, String nombre, int edad) throws SQLException {
        String insert = "INSERT INTO persona (id, nombre, edad) VALUES(?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1, id);
        ps.setString(2, nombre);
        ps.setInt(3, edad);
        ps.executeUpdate();
        ps.close();
        conn.commit();
    }
}

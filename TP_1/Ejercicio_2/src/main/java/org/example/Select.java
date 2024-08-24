package org.example;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Select {
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
            String select = "SELECT * FROM persona";
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println("Id: " + rs.getInt(1) + ", Nombre: " + rs.getString(2) + ", Edad: " + rs.getInt(3));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

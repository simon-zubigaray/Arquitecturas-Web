package org.example;

import org.example.esquemaDB.CreadorDeTablas;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/bd_integrador";
        String user = "root";
        String password = "";

        CreadorDeTablas ct = new CreadorDeTablas(url, user, password);
    }
}
package org.example;

import org.example.dao.cliente.ClienteDAOImpl;
import org.example.entities.Cliente;
import org.example.esquemaDB.CreadorDeTablas;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/bd_integrador";
        String user = "root";
        String password = "";

        CreadorDeTablas ct = new CreadorDeTablas(url, user, password);

        ClienteDAOImpl cliente = new ClienteDAOImpl(url, user, password);

        //Cliente cliente1 =  new Cliente(2,"Pepe", "pepe@gmail.com");
        //cliente.insertar(cliente1);
        //cliente.eliminar(2);
        cliente.actualizar(2, "nicolas", "nicolas@gmail.com");

        List<Cliente> clientes = cliente.obtenerTodas();

        for (Cliente c : clientes){
            String cli = c.toString();
            System.out.println(cli);
        }
    }
}
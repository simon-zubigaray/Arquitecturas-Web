package org.example.dao.cliente;

import org.example.entities.Cliente;

import java.io.IOException;
import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente cliente);
    void insertarDesdeArchivosCSV(String path) throws IOException;
    Cliente obtenerPorId(int id);
    List<Cliente> obtenerTodas();
    void actualizar(int id, String nuevoNombre, String nuevoEmail);
    void eliminar(int id);
}

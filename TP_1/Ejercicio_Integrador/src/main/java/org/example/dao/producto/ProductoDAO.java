package org.example.dao.producto;

import org.example.entities.Producto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProductoDAO {
    void insertar(Producto producto);
    void insertarDesdeArchivosCSV(String path) throws IOException;
    Producto obtenerPorId(int id);
    Producto obtenerElQueMasRecaudo();
    List<Producto> obtenerTodas();
    void actualizar(int id, String nuevoNombre, float nuevoValor);
    void eliminar(int id);
}

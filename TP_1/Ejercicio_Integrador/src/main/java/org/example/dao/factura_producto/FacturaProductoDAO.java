package org.example.dao.factura_producto;

import org.example.entities.FacturaProducto;

import java.io.IOException;
import java.util.List;

public interface FacturaProductoDAO {
    void insertar(FacturaProducto facturaProducto);
    void insertarDesdeArchivosCSV(String path) throws IOException;
    FacturaProducto obtenerPorId(int id);
    List<FacturaProducto> obtenerTodas();
    void actualizar(FacturaProducto facturaProducto);
    void eliminar(Long id);
}

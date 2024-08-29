package org.example.dao.factura_producto;

import org.example.entities.FacturaProducto;

import java.io.IOException;
import java.util.List;

public interface FacturaProductoDAO {
    void insertar(FacturaProducto facturaProducto);
    void insertarDesdeArchivosCSV(String path) throws IOException;
    FacturaProducto obtenerPorId(int idFactura, int idCliente);
    List<FacturaProducto> obtenerTodas();
    void actualizar(int idFactura, int idCliente, int cantidad);
    void eliminar(int idFactura, int idCliente);
}

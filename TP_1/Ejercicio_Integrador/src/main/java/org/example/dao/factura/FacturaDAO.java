package org.example.dao.factura;

import org.example.entities.Factura;

import java.io.IOException;
import java.util.List;

public interface FacturaDAO {
    void insertar(Factura factura);
    void insertarDesdeArchivosCSV(String path) throws IOException;
    Factura obtenerPorId(int id);
    List<Factura> obtenerTodas();
    void actualizar(Factura factura);
    void eliminar(Long id);
}

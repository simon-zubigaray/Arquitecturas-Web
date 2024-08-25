package org.example;

import java.util.List;

//Aca defino los metodos CRUD

public interface PersonaDAO {
    void insertar(Persona persona);
    Persona obtenerPorId(Long id);
    List<Persona> obtenerTodas();
    void actualizar(Persona persona);
    void eliminar(Long id);
}



package org.example;

import java.util.List;

public interface PersonaService {
    void agregarPersona(Persona persona);
    Persona obtenerPersonaPorId(Long id);
    List<Persona> obtenerTodasLasPersonas();
    void actualizarPersona(Persona persona);
    void eliminarPersona(Long id);
}



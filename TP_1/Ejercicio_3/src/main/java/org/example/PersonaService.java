package org.example;

import java.util.List;

public interface PersonaService {
    void agregarPersona(PersonaDTO personaDTO);
    PersonaDTO obtenerPersonaPorId(Long id);
    List<PersonaDTO> obtenerTodasLasPersonas();
    void actualizarPersona(PersonaDTO PersonaDTO);
    void eliminarPersona(Long id);
}
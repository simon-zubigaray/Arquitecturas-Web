package org.example;

import java.util.List;

public class PersonaServiceImpl implements PersonaService {

    private PersonaDAO personaDAO;

    public PersonaServiceImpl(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    @Override
    public void agregarPersona(Persona persona) {
        personaDAO.insertar(persona);
    }

    @Override
    public Persona obtenerPersonaPorId(Long id) {
        return personaDAO.obtenerPorId(id);
    }

    @Override
    public List<Persona> obtenerTodasLasPersonas() {
        return personaDAO.obtenerTodas();
    }

    @Override
    public void actualizarPersona(Persona persona) {
        personaDAO.actualizar(persona);
    }

    @Override
    public void eliminarPersona(Long id) {
        personaDAO.eliminar(id);
    }
}

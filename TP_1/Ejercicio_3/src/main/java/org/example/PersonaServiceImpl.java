package org.example;

import java.util.ArrayList;
import java.util.List;

public class PersonaServiceImpl implements PersonaService {

    private PersonaDAO personaDAO;

    public PersonaServiceImpl(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    @Override
    public void agregarPersona(PersonaDTO personaDTO) {
        Persona per = new Persona(
                personaDTO.getNombre(),
                personaDTO.getApellido(),
                personaDTO.getEdad()
        );

        personaDAO.insertar(per);
    }

    @Override
    public PersonaDTO obtenerPersonaPorId(Long id) {
        Persona persona = personaDAO.obtenerPorId(id);
        return persona != null ? new PersonaDTO(
                persona.getNombre(),
                persona.getApellido(),
                persona.getEdad()
        ) : null;
    }

    @Override
    public List<PersonaDTO> obtenerTodasLasPersonas() {
        List<Persona> personas = personaDAO.obtenerTodas();
        List<PersonaDTO> personaDTOs = new ArrayList<>();
        for (Persona persona : personas) {
            personaDTOs.add(new PersonaDTO(
                    persona.getNombre(),
                    persona.getApellido(),
                    persona.getEdad()
            ));
        }
        return personaDTOs;
    }

    @Override
    public void actualizarPersona(PersonaDTO personaDTO) {
        Persona persona = new Persona(
                personaDTO.getNombre(),
                personaDTO.getApellido(),
                personaDTO.getEdad()
        );
        personaDAO.actualizar(persona);
    }

    @Override
    public void eliminarPersona(Long id) {
        personaDAO.eliminar(id);
    }
}

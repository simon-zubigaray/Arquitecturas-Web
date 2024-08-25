package org.example;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Configuración de la conexión a la base de datos
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bd_personas"; // Cambia según tu configuración
        String user = "root"; // Cambia según tu configuración
        String password = ""; // Cambia según tu configuración

        String driver_derby = "org.apache.derby.jdbc.EmbeddedDriver";
        String url_derby = "jdbc:derby:MyDerbyDb;create=true";

        try {
            // Crear instancia de PersonaDAO
            PersonaDAO personaDAO = new PersonaDAOImpl(url_derby, "", "", driver_derby);

            // Crear instancia de PersonaService
            PersonaService personaService = new PersonaServiceImpl(personaDAO);

            /*
            // Crear una nueva persona
            PersonaDTO nuevaPersona1 = new PersonaDTO("Juan", "Pérez", 30);
            personaService.agregarPersona(nuevaPersona1);
            PersonaDTO nuevaPersona2 = new PersonaDTO("Rodrigo", "Saenz", 23);
            personaService.agregarPersona(nuevaPersona2);
            PersonaDTO nuevaPersona3 = new PersonaDTO("Esteban", "Gomez", 41);
            personaService.agregarPersona(nuevaPersona3);
            personaService.eliminarPersona(13L);
            */
            // Obtener todas las personas
            List<PersonaDTO> personas = personaService.obtenerTodasLasPersonas();
            System.out.println("Todas las personas:");
            for (PersonaDTO persona : personas) {
                System.out.println(persona.getNombre() + " " + persona.getApellido() + ", Edad: " + persona.getEdad());
            }

            // Obtener una persona por ID
            //PersonaDTO persona = personaService.obtenerPersonaPorId(1L); // Asegúrate de que el ID exista
            //if (persona != null) {
            //    System.out.println("Persona con ID 1: " + persona.getNombre() + " " + persona.getApellido() + ", Edad: " + persona.getEdad());
            //} else {
            //    System.out.println("Persona con ID 1 no encontrada.");
            //}

            // Actualizar una persona
            //PersonaDTO personaActualizada = new PersonaDTO(1L, "Juan", "Pérez", 31);
            //personaService.actualizarPersona(personaActualizada);

            // Eliminar una persona
            //personaService.eliminarPersona(1L);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
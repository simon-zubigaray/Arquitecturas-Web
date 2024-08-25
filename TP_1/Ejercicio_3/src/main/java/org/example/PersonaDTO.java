package org.example;

public class PersonaDTO {
    private String nombre;
    private String apellido;
    private Integer edad;

    public PersonaDTO(String nombre, String apellido, Integer edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getEdad() {
        return edad;
    }
}

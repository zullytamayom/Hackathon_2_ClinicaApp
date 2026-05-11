package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;
import java.util.*;

public class Paciente implements Registrable {
    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        this.id = id;
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) {
        if(cedula == null || cedula.isBlank()) throw new IllegalArgumentException("Cédula no válida.");
        this.cedula = cedula.trim();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido.");
        this.nombre = nombre.trim();
    }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) {
        if(apellido == null || apellido.isBlank()) throw new IllegalArgumentException("Apellido requerido.");
        this.apellido = apellido.trim();
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {
        if(telefono == null || !telefono.matches("^[0-9]{7,10}$"))
            throw new IllegalArgumentException("Teléfono debe tener entre 7 y 10 dígitos");
        this.telefono = telefono;
    }


    @Override
    public String getDatosRegistro() {
        return String.format("ID: %d | %s %s | CC: %s | Tel: %s",
                id, nombre, apellido, cedula, telefono);
    }

    @Override
    public boolean esValido() {
        return (cedula != null && !cedula.isBlank()) &&
                (nombre != null && !nombre.isBlank()) &&
                (apellido != null && !apellido.isBlank()) &&
                (telefono != null && telefono.matches("^[0-9]{7,10}$"));
    }


    @Override
    public String toString() {
        return String.format("%s %s - %s - %s",
                nombre, apellido, cedula, telefono);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cedula, paciente.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }
}
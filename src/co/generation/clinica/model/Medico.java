package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

import java.util.Objects;

public class Medico implements Registrable {
    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    public Medico(int id, String nombre, String apellido, Especialidad especialidad) {
        this.id = id;
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    public Medico(String nombre, String apellido, Especialidad especialidad) {
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank())throw new IllegalArgumentException("Nombre requerido");
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if(apellido == null || apellido.isBlank()) throw new IllegalArgumentException("Apellido requerido");
        this.apellido = apellido.trim();
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        if(especialidad == null) throw new IllegalArgumentException("Debes ingresar especialidad");
        this.especialidad = especialidad;
    }

    @Override
    public String getDatosRegistro() {
        return "";
    }

    @Override
    public boolean esValido() {
       return   nombre != null && !nombre.isBlank()&&
                apellido != null && !apellido.isBlank() &&
               especialidad !=null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return nombre.equalsIgnoreCase(medico.nombre) &&
                apellido.equalsIgnoreCase(medico.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase(), apellido.toLowerCase());
    }

    @Override
    public String toString() {
        return "Dr. " + nombre + " " + apellido + " - " + especialidad;
    }
}

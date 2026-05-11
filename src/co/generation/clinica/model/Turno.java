package co.generation.clinica.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Turno {

    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        this.estado = EstadoTurno.PENDIENTE;
    }

    public Turno(int id, Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.id = id;
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        this.estado = estado; // estado que viene del archivo
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if(paciente == null) throw new IllegalArgumentException("El paciente es requerido.");
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if(medico == null) throw new IllegalArgumentException("El médico es requerido.");
        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if(fechaHora == null) throw new IllegalArgumentException("La fecha y hora son requeridas");
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        if(estado == null) throw new IllegalArgumentException("El estado no puede ser nulo.");
        this.estado = estado;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turno turno = (Turno) o;
        return Objects.equals(medico, turno.medico) &&
                Objects.equals(fechaHora, turno.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medico, fechaHora);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s - %s",
                estado, paciente.getNombre(), paciente.getApellido(),
                fechaHora.toString());
    }
}

package co.generation.clinica.interfaces;

import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;

import java.time.LocalDate;
import java.util.List;

public interface Consultable {

    List listarTurnosDelDia(LocalDate fecha);
    List buscarPorMedico(Medico medico);
    List buscarPorPaciente(Paciente paciente);
}

package co.generation.clinica.datos;

import co.generation.clinica.model.*;
import co.generation.clinica.service.ClinicaService;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DatosCSV {
    private static final String DIR = "datos/";
    private static final String F_PACIENTES = DIR + "pacientes.csv";
    private static final String F_MEDICOS = DIR + "medicos.csv";
    private static final String F_TURNOS = DIR + "turnos.csv";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void cargar(ClinicaService servicio) {
        new File(DIR).mkdirs();
        cargarPacientes(servicio);
        cargarMedicos(servicio);
        cargarTurnos(servicio);
    }

    private static void cargarPacientes(ClinicaService servicio) {
        File f = new File(F_PACIENTES);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] p = linea.split(",", -1); // id,cedula,nombre,apellido,telefono
                servicio.getPacientes().add(new Paciente(
                        Integer.parseInt(p[0].trim()),
                        p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim()));
            }
        } catch (IOException e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void cargarMedicos(ClinicaService servicio) {
        File f = new File(F_MEDICOS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] p = linea.split(",", -1); // id,nombre,apellido,especialidad
                servicio.getMedicos().add(new Medico(
                        Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
                        Especialidad.valueOf(p[3].trim())));
            }
        } catch (IOException e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void cargarTurnos(ClinicaService servicio) {
        File f = new File(F_TURNOS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                // formato: id,cedulaPaciente,nombreMedico,apellidoMedico,fechaHora,estado
                String[] p = linea.split(",", -1);
                Paciente pac = servicio.buscarPorCedula(p[1].trim());
                Medico med = servicio.buscarPorNombreApellido(p[2].trim(), p[3].trim());

                if (pac == null || med == null) continue;

                servicio.getTurnos().add(new Turno(
                        Integer.parseInt(p[0].trim()), pac, med,
                        LocalDateTime.parse(p[4].trim(), FMT),
                        EstadoTurno.valueOf(p[5].trim())));
            }
        } catch (IOException e) { System.out.println("Error: " + e.getMessage()); }
    }

    // ■■ GUARDAR ■■
    public static void guardar(ClinicaService servicio) {
        new File(DIR).mkdirs();
        guardarPacientes(servicio.getPacientes());
        guardarMedicos(servicio.getMedicos());
        guardarTurnos(servicio.getTurnos());
    }

    private static void guardarPacientes(List<Paciente> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(F_PACIENTES))) {
            for (Paciente p : lista)
                pw.println(p.getId() + "," + p.getCedula() + "," +
                        p.getNombre() + "," + p.getApellido() + "," + p.getTelefono());
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    private static void guardarMedicos(List<Medico> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(F_MEDICOS))) {
            for (Medico m : lista)
                pw.println(m.getId() + "," + m.getNombre() + "," +
                        m.getApellido() + "," + m.getEspecialidad());
        } catch (IOException e) {
            System.out.println("Error al guardar médicos: " + e.getMessage());
        }
    }

    private static void guardarTurnos(List<Turno> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(F_TURNOS))) {
            for (Turno t : lista)
                pw.println(t.getId() + "," +
                        t.getPaciente().getCedula() + "," +
                        t.getMedico().getNombre() + "," +
                        t.getMedico().getApellido() + "," +
                        t.getFechaHora().format(FMT) + "," +
                        t.getEstado());
        } catch (IOException e) {
            System.out.println("Error al guardar turnos: " + e.getMessage());
        }
    }
}
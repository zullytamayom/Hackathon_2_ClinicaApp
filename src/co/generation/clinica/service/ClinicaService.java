package co.generation.clinica.service;

import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService implements Consultable {

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Turno> turnos = new ArrayList<>();

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    //GESTION DE PACIENTES:
    public void registrarPaciente(Paciente p) {
        // 1. Validar datos (formato, no nulos)
        if (!p.esValido()) {
            System.out.println("Error: Datos del paciente no válidos.");
            return;
        }

        // 2. Verificar duplicados por cédula
        if (pacientes.contains(p)) {
            //En Paciente.java se implementó equals() por cédula
            System.out.println("Error: Ya existe un paciente con esa cédula.");
            return;
        }

        // 3. Asignar ID automático (el máximo actual + 1)
        int maxId = 0;
        for (Paciente pac : pacientes) {
            if (pac.getId() > maxId) maxId = pac.getId();
        }
        p.setId(maxId + 1);

        // 4. Agregar a la lista e informar
        pacientes.add(p);
        System.out.println("Paciente registrado con éxito: " + p.toString());
    }

    public Paciente buscarPorCedula(String cedula) {
        for (Paciente p : pacientes) {
            if (p.getCedula().equals(cedula)) return p;
        }
        return null;
    }
    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }
        List<Paciente> copia = new ArrayList<>(pacientes);
        // Ordenar por apellido, luego por nombre
        copia.sort(Comparator.comparing(Paciente::getApellido).thenComparing(Paciente::getNombre));
        for (Paciente p : copia) System.out.println(p);
    }

    //GESTION DE MEDICOS:
    public void registrarMedico(Medico m) {
        if (!m.esValido()) return;

        if (medicos.contains(m)) {
            System.out.println("Error: El médico ya está registrado.");
            return;
        }

        int maxId = 0;
        for (Medico med : medicos) {
            if (med.getId() > maxId) maxId = med.getId();
        }
        m.setId(maxId + 1);
        medicos.add(m);
        System.out.println("Médico registrado con éxito.");
    }

    public Medico buscarPorNombreApellido(String nombre, String apellido) {
        for (Medico m : medicos) {
            if (m.getNombre().equalsIgnoreCase(nombre) && m.getApellido().equalsIgnoreCase(apellido)) {
                return m;
            }
        }
        return null;
    }
    public void listarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados en el sistema.");
            return;
        }

        //  Creamos una COPIA de la lista para no desordenar la lista original
        List<Medico> copiaMedicos = new ArrayList<>(medicos);

        // Ordenar usando un Comparador doble:
        // Primero comparamos la Especialidad (usa el orden en que las escribiste en el Enum)
        // Si la especialidad es la misma, comparamos por Apellido.
        copiaMedicos.sort(Comparator
                .comparing(Medico::getEspecialidad)
                .thenComparing(Medico::getApellido)
        );

        System.out.println("\n--- LISTADO DE MÉDICOS (Por especialidad y apellido) ---");
        for (Medico m : copiaMedicos) {
            System.out.println(m);
        }
    }

    // GESTION DE TURNOS:
    public void asignarTurno(Turno t) {
        // 1. Verificar que el paciente y médico existan
        if (!pacientes.contains(t.getPaciente()) || !medicos.contains(t.getMedico())) {
            System.out.println("Error: Paciente o Médico no encontrados.");
            return;
        }

        // 2. Verificar conflicto de agenda
        if (turnos.contains(t)) {
            System.out.println("Error: El médico ya tiene un turno asignado a esa hora.");
            return;
        }

        // 3. Asignar ID y guardar
        int maxId = 0;
        for (Turno tur : turnos) {
            if (tur.getId() > maxId) maxId = tur.getId();
        }
        t.setId(maxId + 1);
        turnos.add(t);
        System.out.println("Turno asignado: " + t.toString());
    }

    public void cancelarTurno(int idTurno) {
        for (Turno t : turnos) {
            if (t.getId() == idTurno) {
                if (t.getEstado() == EstadoTurno.PENDIENTE) {
                    t.setEstado(EstadoTurno.CANCELADO);
                    System.out.println("Turno cancelado con éxito.");
                } else {
                    System.out.println("No se puede cancelar un turno ya atendido o cancelado.");
                }
                return;
            }
        }
        System.out.println("Turno no encontrado.");
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevoEstado) {
        // Buscar el turno por su ID
        // Como los turnos están en una lista, tenemos que recorrerla uno por uno
        Turno turnoEncontrado = null;

        for (Turno t : turnos) {
            if (t.getId() == idTurno) {
                turnoEncontrado = t;
                break;
            }
        }

        // Si después de recorrer toda la lista, la variable sigue siendo null...
        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        // Realizar el cambio
        turnoEncontrado.setEstado(nuevoEstado);

        // 4. Confirmación
        System.out.println("El estado del turno #" + idTurno + " ha sido cambiado a: " + nuevoEstado);
    }


    @Override
    public List listarTurnosDelDia(LocalDate fecha) {
        List<Turno> filtrados = new ArrayList<>();
        for (Turno t : turnos) {
            // Comparamos solo la parte de la fecha del LocalDateTime
            if (t.getFechaHora().toLocalDate().equals(fecha)) {
                filtrados.add(t);
            }
        }
        // Ordenar por hora (menor a mayor)
        filtrados.sort(Comparator.comparing(Turno::getFechaHora));
        return filtrados;
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico) {
        // 1. Creamos una lista vacía para guardar los resultados
        List<Turno> turnosDelMedico = new ArrayList<>();

        // 2. Recorremos la lista general de turnos
        for (Turno t : turnos) {
            // 3. Comparamos el médico del turno con el médico que recibimos
            if (t.getMedico().equals(medico)) {
                turnosDelMedico.add(t); // Si coincide, lo guardamos en nuestra lista temporal
            }
        }

        // 4. Retornamos la lista,si no encontró nada, la lista estará vacía.
        return turnosDelMedico;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        List<Turno> turnosDelPaciente = new ArrayList<>();

        for (Turno t : turnos) {
            // Aquí comparamos objetos Paciente usando el .equals()
            // en la clase Paciente (el que compara por cédula)
            if (t.getPaciente().equals(paciente)) {
                turnosDelPaciente.add(t);
            }
        }

        return turnosDelPaciente;
    }

    public void generarReporteHTML() {
        File archivo = new File("reporte_clinica_completo.html");

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("<html><head><meta charset='UTF-8'><title>Reporte Maestro ClinicaApp</title>");
            writer.println("<style>body{font-family:sans-serif; margin:40px; background:#f4f4f4;} .card{background:white; padding:20px; margin-bottom:30px; border-radius:8px; box-shadow:0 2px 5px rgba(0,0,0,0.1);} table{width:100%; border-collapse:collapse;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#3498db; color:white;} h2{color:#2980b9; border-bottom:2px solid #2980b9;}</style></head><body>");

            writer.println("<h1>🏥 Reporte Maestro de la Clínica</h1>");

            // --- SECCIÓN 1: PACIENTES ---
            writer.println("<div class='card'><h2>👥 Pacientes Registrados</h2><table>");
            writer.println("<tr><th>ID</th><th>Nombre Completo</th><th>Cédula</th><th>Teléfono</th></tr>");
            for (Paciente p : pacientes) {
                writer.println("<tr><td>"+p.getId()+"</td><td>"+p.getNombre()+" "+p.getApellido()+"</td><td>"+p.getCedula()+"</td><td>"+p.getTelefono()+"</td></tr>");
            }
            writer.println("</table></div>");

            // --- SECCIÓN 2: MÉDICOS ---
            writer.println("<div class='card'><h2>👨‍⚕️ Cuerpo Médico</h2><table>");
            writer.println("<tr><th>ID</th><th>Nombre</th><th>Especialidad</th></tr>");
            for (Medico m : medicos) {
                writer.println("<tr><td>"+m.getId()+"</td><td>Dr. "+m.getNombre()+" "+m.getApellido()+"</td><td>"+m.getEspecialidad()+"</td></tr>");
            }
            writer.println("</table></div>");

            // --- SECCIÓN 3: TURNOS
            writer.println("<div class='card'><h2>📅 Agenda de Turnos</h2><table>");
            writer.println("<tr><th>ID</th><th>Paciente</th><th>Médico</th><th>Fecha</th><th>Estado</th></tr>");
            for (Turno t : turnos) {
                writer.println("<tr><td>"+t.getId()+"</td><td>"+t.getPaciente().getNombre()+"</td><td>Dr. "+t.getMedico().getNombre()+"</td><td>"+t.getFechaHora()+"</td><td>"+t.getEstado()+"</td></tr>");
            }
            writer.println("</table></div>");

            writer.println("</body></html>");
            System.out.println("✅ Reporte Maestro generado: reporte_clinica_completo.html");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

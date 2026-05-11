import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.*;
import co.generation.clinica.service.ClinicaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClinicaService servicio = new ClinicaService();

        DatosCSV.cargar(servicio);

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            System.out.println("■      CLINICAAPP — MENÚ               ■");
            System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            System.out.println("■ 1. Registrar paciente                ■");
            System.out.println("■ 2. Registrar médico                  ■");
            System.out.println("■ 3. Asignar turno                     ■");
            System.out.println("■ 4. Listar turnos del día             ■");
            System.out.println("■ 5. Cancelar turno                    ■");
            System.out.println("■ 6. Ver turnos por médico             ■");
            System.out.println("■ 7. Ver turnos por paciente           ■");
            System.out.println("■ 8. Cambiar estado de turno           ■");
            System.out.println("■ 9. Listar pacientes                  ■");
            System.out.println("■ 10. Listar médicos                   ■");
            System.out.println("■ 11. Generar Reporte HTML             ■");
            System.out.println("■ 0. Salir                             ■");
            System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); // IMPORTANTE: Limpiar el enter después de leer un número

            switch (opcion) {
                case 1:
                    System.out.print("Cédula: "); String ced = sc.nextLine();
                    System.out.print("Nombre: "); String nom = sc.nextLine();
                    System.out.print("Apellido: "); String ape = sc.nextLine();
                    System.out.print("Teléfono: "); String tel = sc.nextLine();
                    // Usamos el constructor SIN id
                    servicio.registrarPaciente(new Paciente(ced, nom, ape, tel));
                    break;

                case 2:
                    System.out.print("Nombre: "); String nomM = sc.nextLine();
                    System.out.print("Apellido: "); String apeM = sc.nextLine();
                    System.out.println("Especialidades: GENERAL, PEDIATRIA, CARDIOLOGIA, URGENCIAS");
                    System.out.print("Especialidad: ");
                    String espStr = sc.nextLine().toUpperCase();
                    servicio.registrarMedico(new Medico(nomM, apeM, Especialidad.valueOf(espStr)));
                    break;

                case 3: // ASIGNAR TURNO (Lógica página 6)
                    System.out.print("Cédula del paciente: ");
                    Paciente p = servicio.buscarPorCedula(sc.nextLine());
                    System.out.print("Nombre del médico: "); String nMed = sc.nextLine();
                    System.out.print("Apellido del médico: "); String aMed = sc.nextLine();
                    Medico m = servicio.buscarPorNombreApellido(nMed, aMed);

                    if (p != null && m != null) {
                        System.out.print("Año (YYYY): "); int anio = sc.nextInt();
                        System.out.print("Mes (1-12): "); int mes = sc.nextInt();
                        System.out.print("Día: "); int dia = sc.nextInt();
                        System.out.print("Hora (0-23): "); int hora = sc.nextInt();
                        System.out.print("Minuto: "); int min = sc.nextInt();

                        LocalDateTime fecha = LocalDateTime.of(anio, mes, dia, hora, min);
                        servicio.asignarTurno(new Turno(p, m, fecha));
                    } else {
                        System.out.println("Error: Paciente o Médico no encontrados.");
                    }
                    break;

                case 4: // Listar turnos del día
                    System.out.print("Año (YYYY): "); int a4 = sc.nextInt();
                    System.out.print("Mes (1-12): "); int m4 = sc.nextInt();
                    System.out.print("Día: "); int d4 = sc.nextInt();
                    LocalDate fechaBusqueda = LocalDate.of(a4, m4, d4);

                    List<Turno> turnosDia = servicio.listarTurnosDelDia(fechaBusqueda);
                    if(turnosDia.isEmpty()) System.out.println("No hay turnos para esa fecha.");
                    else turnosDia.forEach(System.out::println);
                    break;

                case 5: // Cancelar turno
                    System.out.print("ID del turno a cancelar: ");
                    int idCanc = sc.nextInt();
                    servicio.cancelarTurno(idCanc);
                    break;

                case 6: // Ver turnos por médico
                    System.out.print("Nombre del médico: "); String n6 = sc.nextLine();
                    System.out.print("Apellido del médico: "); String a6 = sc.nextLine();
                    Medico med6 = servicio.buscarPorNombreApellido(n6, a6);
                    if(med6 != null) {
                        servicio.buscarPorMedico(med6).forEach(System.out::println);
                    } else {
                        System.out.println("Médico no encontrado.");
                    }
                    break;

                case 7: // Ver turnos por paciente
                    System.out.print("Cédula del paciente: ");
                    Paciente pac7 = servicio.buscarPorCedula(sc.nextLine());
                    if(pac7 != null) {
                        servicio.buscarPorPaciente(pac7).forEach(System.out::println);
                    } else {
                        System.out.println("Paciente no encontrado.");
                    }
                    break;

                case 8: // Cambiar estado de turno
                    System.out.print("ID del turno: "); int id8 = sc.nextInt();
                    System.out.println("Nuevos estados: PENDIENTE, ATENDIDO, CANCELADO");
                    System.out.print("Nuevo estado: ");
                    String est8 = sc.next().toUpperCase();
                    servicio.cambiarEstadoTurno(id8, EstadoTurno.valueOf(est8));
                    break;

                case 9:
                    servicio.listarPacientes();
                    break;

                case 10:
                    servicio.listarMedicos();
                    break;
                case 11:
                    servicio.generarReporteHTML();
                    break;

                case 0:
                    // PASO 2: GUARDAR DATOS AL SALIR
                    DatosCSV.guardar(servicio);
                    System.out.println("¡Datos guardados! Cerrando sistema...");
                    break;
            }
        }
    }
}
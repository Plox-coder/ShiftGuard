package com.inaki.shiftguard.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.inaki.shiftguard.model.Turno;
import com.inaki.shiftguard.model.Vigilante;
import com.inaki.shiftguard.service.TurnoService;
import com.inaki.shiftguard.service.VigilanteService;

public class MenuTurno {

    private final Scanner scanner;
    private final TurnoService turnoService;
    private final VigilanteService vigilanteService;

    public MenuTurno(
            Scanner scanner,
            TurnoService turnoService,
            VigilanteService vigilanteService
    ) {
        this.scanner = scanner;
        this.turnoService = turnoService;
        this.vigilanteService = vigilanteService;
    }

    public void mostrarMenu() {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== GESTIÓN DE TURNOS ===");
            System.out.println("1. Crear turno");
            System.out.println("2. Mostrar turnos");
            System.out.println("3. Buscar turno por ID");
            System.out.println("4. Modificar turno");
            System.out.println("5. Eliminar turno");
            System.out.println("6. Volver");

            System.out.print("Selecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {

                case "1" -> crearTurno();

                case "2" -> mostrarTurnos();

                case "3" -> buscarTurno();

                case "4" -> modificarTurno();

                case "5" -> eliminarTurno();

                case "6" -> salir = true;

                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void crearTurno() {

        try {

            System.out.print("ID del turno: ");
            Long id = Long.valueOf(scanner.nextLine());

            if (turnoService.buscarTurnoPorId(id) != null) {
                System.out.println("Ya existe un turno con ese ID.");
                return;
            }

            System.out.print("Fecha (AAAA-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            System.out.print("Hora de inicio (HH:MM): ");
            LocalTime horaInicio = LocalTime.parse(scanner.nextLine());

            System.out.print("Hora de fin (HH:MM): ");
            LocalTime horaFin = LocalTime.parse(scanner.nextLine());

            if (!horaFin.isAfter(horaInicio)) {
                System.out.println(
                        "La hora de fin debe ser posterior a la hora de inicio."
                );
                return;
            }

            System.out.print("TIP del vigilante: ");
            String tip = scanner.nextLine();

            Vigilante vigilante =
                    vigilanteService.buscarPorTip(tip);

            if (vigilante == null) {
                System.out.println(
                        "No existe ningún vigilante con ese TIP."
                );
                return;
            }

            Turno turno = new Turno(
                    id,
                    fecha,
                    horaInicio,
                    horaFin,
                    vigilante
            );

            turnoService.agregarTurno(turno);

            System.out.println("Turno creado correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("El ID debe ser un número.");

        } catch (DateTimeParseException e) {

            System.out.println(
                    "Formato incorrecto. Usa AAAA-MM-DD para la fecha "
                            + "y HH:MM para las horas."
            );
        }
    }

    private void mostrarTurnos() {

        if (turnoService.obtenerTurnos().isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }

        System.out.println("\n=== TURNOS ===");

        for (Turno turno : turnoService.obtenerTurnos()) {

            mostrarDatosTurno(turno);
        }
    }

    private void buscarTurno() {

        try {

            System.out.print("Introduce el ID del turno: ");
            Long id = Long.valueOf(scanner.nextLine());

            Turno turno = turnoService.buscarTurnoPorId(id);

            if (turno == null) {
                System.out.println(
                        "No existe ningún turno con ese ID."
                );
                return;
            }

            System.out.println("\n=== TURNO ENCONTRADO ===");
            mostrarDatosTurno(turno);

        } catch (NumberFormatException e) {

            System.out.println("El ID debe ser un número.");
        }
    }

    private void modificarTurno() {

        try {

            System.out.print("Introduce el ID del turno: ");
            Long id = Long.valueOf(scanner.nextLine());

            Turno turno = turnoService.buscarTurnoPorId(id);

            if (turno == null) {
                System.out.println(
                        "No existe ningún turno con ese ID."
                );
                return;
            }

            System.out.println("\nDatos actuales:");
            mostrarDatosTurno(turno);

            System.out.println(
                    "\nPulsa ENTER para mantener el valor actual."
            );

            // Fecha

            System.out.print(
                    "Nueva fecha [" + turno.getFecha() + "]: "
            );

            String nuevaFecha = scanner.nextLine();

            if (!nuevaFecha.isBlank()) {
                turno.setFecha(
                        LocalDate.parse(nuevaFecha)
                );
            }

            // Hora inicio

            System.out.print(
                    "Nueva hora de inicio ["
                            + turno.getHoraInicio()
                            + "]: "
            );

            String nuevoInicio = scanner.nextLine();

            if (!nuevoInicio.isBlank()) {
                turno.setHoraInicio(
                        LocalTime.parse(nuevoInicio)
                );
            }

            // Hora fin

            System.out.print(
                    "Nueva hora de fin ["
                            + turno.getHoraFin()
                            + "]: "
            );

            String nuevoFin = scanner.nextLine();

            if (!nuevoFin.isBlank()) {
                turno.setHoraFin(LocalTime.parse(nuevoFin));
            }

            if (!turno.getHoraFin().isAfter(turno.getHoraInicio())) {

                System.out.println("Error: la hora de fin debe ser posterior " + "a la hora de inicio.");
                return;
            }

            // Vigilante

            System.out.print(
                    "Nuevo TIP [" + turno.getVigilante().getTip() + "]: "
            );

            String nuevoTip = scanner.nextLine();

            if (!nuevoTip.isBlank()) {

                Vigilante nuevoVigilante = vigilanteService.buscarPorTip(nuevoTip);

                if (nuevoVigilante == null) {

                    System.out.println(
                            "No existe ningún vigilante con ese TIP."
                    );

                    return;
                }

                turno.setVigilante(nuevoVigilante);
            }

            System.out.println("Turno modificado correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("El ID debe ser un número.");

        } catch (DateTimeParseException e) {

            System.out.println(
                    "Formato incorrecto de fecha u hora."
            );
        }
    }

    private void eliminarTurno() {

        try {

            System.out.print(
                    "Introduce el ID del turno a eliminar: "
            );

            Long id = Long.valueOf(scanner.nextLine());

            boolean eliminado =
                    turnoService.eliminarTurnoPorId(id);

            if (eliminado) {
                System.out.println(
                        "Turno eliminado correctamente."
                );
            } else {
                System.out.println(
                        "No existe ningún turno con ese ID."
                );
            }

        } catch (NumberFormatException e) {

            System.out.println("El ID debe ser un número.");
        }
    }

    private void mostrarDatosTurno(Turno turno) {

        System.out.println("-------------------------");
        System.out.println("ID: " + turno.getId());
        System.out.println("Fecha: " + turno.getFecha());

        System.out.println(
                "Horario: "
                        + turno.getHoraInicio()
                        + " - "
                        + turno.getHoraFin()
        );

        System.out.println(
                "Vigilante: "
                        + turno.getVigilante().getNombre()
        );

        System.out.println(
                "TIP: "
                        + turno.getVigilante().getTip()
        );
    }
}
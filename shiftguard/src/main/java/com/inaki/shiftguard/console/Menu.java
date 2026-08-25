package com.inaki.shiftguard.console;

import java.util.List;
import java.util.Scanner;

import com.inaki.shiftguard.model.Vigilante;
import com.inaki.shiftguard.service.VigilanteService;

public class Menu {

    private final VigilanteService vigilanteService;
    private final Scanner scanner;

    public Menu(VigilanteService vigilanteService) {
        this.vigilanteService = vigilanteService;
        this.scanner = new Scanner(System.in);
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void iniciar() {
        boolean ejecutando = true;

        while (ejecutando) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> crearVigilante();
                case 2 -> mostrarVigilantes();
                case 3 -> buscarVigilante();
                case 4 -> cambiarEstadoVigilante();
                case 5 -> eliminarVigilante();
                case 6 -> {
                    ejecutando = false;
                    System.out.println("Cerrando ShiftGuard...");
                }
                default -> System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=== SHIFTGUARD ===");
        System.out.println("1. Crear vigilante");
        System.out.println("2. Mostrar vigilantes");
        System.out.println("3. Buscar vigilante por TIP");
        System.out.println("4. Cambiar estado de un vigilante");
        System.out.println("5. Eliminar vigilante");
        System.out.println("6. Salir");
    }

    private int leerOpcion() {
        while (true) {
            System.out.print("Selecciona una opción: ");
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un número.");
            }
        }
    }

    private void crearVigilante() {
        System.out.println();
        System.out.println("=== CREAR VIGILANTE ===");

        String tip = pedirTip();

        if (vigilanteService.buscarPorTip(tip) != null) {
            System.out.println("Ya existe un vigilante con ese TIP.");
            esperarEnter();
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("¿Está activo? (s/n): ");
        String respuesta = scanner.nextLine().trim();

        boolean activo = respuesta.equalsIgnoreCase("s");

        vigilanteService.crearVigilante(nombre, tip, activo);

        System.out.println("Vigilante creado correctamente.");
        esperarEnter();
    }

    private void mostrarVigilantes() {
        System.out.println();
        System.out.println("=== VIGILANTES ===");

        List<Vigilante> vigilantes = vigilanteService.obtenerTodos();

        if (vigilantes.isEmpty()) {
            System.out.println("No hay vigilantes registrados.");
            esperarEnter();
            return;
        }

        for (Vigilante vigilante : vigilantes) {
            mostrarDatosVigilante(vigilante);
            System.out.println("--------------------");
        }

        esperarEnter();
    }

    private void buscarVigilante() {
        System.out.println();
        System.out.println("=== BUSCAR VIGILANTE ===");

        String tip = pedirTip();
        Vigilante vigilante = vigilanteService.buscarPorTip(tip);

        if (vigilante == null) {
            System.out.println("Vigilante no encontrado.");
            esperarEnter();
            return;
        }

        System.out.println("Vigilante encontrado:");
        mostrarDatosVigilante(vigilante);
        esperarEnter();
    }

    private void cambiarEstadoVigilante() {
        System.out.println();
        System.out.println("=== CAMBIAR ESTADO ===");

        String tip = pedirTip();
        Vigilante vigilante = vigilanteService.buscarPorTip(tip);

        if (vigilante == null) {
            System.out.println("Vigilante no encontrado.");
            esperarEnter();
            return;
        }

        vigilanteService.cambiarEstado(tip);

        String nuevoEstado =
                vigilante.isActivo() ? "activo" : "inactivo";

        System.out.println(
                "El vigilante ahora está " + nuevoEstado + "."
        );

        esperarEnter();
    }

    private void eliminarVigilante() {
        System.out.println();
        System.out.println("=== ELIMINAR VIGILANTE ===");

        String tip = pedirTip();
        Vigilante vigilante = vigilanteService.buscarPorTip(tip);

        if (vigilante == null) {
            System.out.println("Vigilante no encontrado.");
            esperarEnter();
            return;
        }

        System.out.println("Vigilante encontrado:");
        mostrarDatosVigilante(vigilante);

        System.out.print("¿Confirmas que deseas eliminarlo? (s/n): ");
        String respuesta = scanner.nextLine().trim();

        if (respuesta.equalsIgnoreCase("s")) {
            vigilanteService.eliminarPorTip(tip);
            System.out.println("Vigilante eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }

        esperarEnter();
    }

    private String pedirTip() {
        while (true) {
            System.out.print("Introduce el TIP: ");
            String tip = scanner.nextLine().trim();

            if (tip.isBlank()) {
                System.out.println("El TIP no puede estar vacío.");
            } else if (!tip.matches("\\d+")) {
                System.out.println(
                        "El TIP solo puede contener números."
                );
            } else {
                return tip;
            }
        }
    }

    private void mostrarDatosVigilante(Vigilante vigilante) {
        System.out.println("Nombre: " + vigilante.getNombre());
        System.out.println("TIP: " + vigilante.getTip());
        System.out.println(
                "Activo: " +
                (vigilante.isActivo() ? "Sí" : "No")
        );
    }

    private void esperarEnter() {
        System.out.println();
        System.out.print("Pulsa Enter para volver al menú...");
        scanner.nextLine();
    }
}
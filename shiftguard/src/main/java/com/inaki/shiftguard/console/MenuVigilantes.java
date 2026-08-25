package com.inaki.shiftguard.console;

import java.util.List;

import com.inaki.shiftguard.model.Vigilante;
import com.inaki.shiftguard.service.VigilanteService;

public class MenuVigilantes {

    private final VigilanteService vigilanteService;
    private final Consola consola;

    public MenuVigilantes(
            VigilanteService vigilanteService,
            Consola consola) {

        this.vigilanteService = vigilanteService;
        this.consola = consola;
    }

    public void iniciar() {
        boolean volver = false;

        while (!volver) {
            mostrarMenu();
            int opcion = consola.leerOpcion();

            switch (opcion) {
                case 1 ->
                    crearVigilante();

                case 2 ->
                    mostrarVigilantes();

                case 3 ->
                    buscarVigilante();

                case 4 ->
                    cambiarEstadoVigilante();

                case 5 ->
                    eliminarVigilante();

                case 6 ->
                    volver = true;

                default ->
                    System.out.println(
                            "Opción no válida."
                    );
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=== VIGILANTES ===");
        System.out.println("1. Crear vigilante");
        System.out.println("2. Mostrar vigilantes");
        System.out.println("3. Buscar vigilante por TIP");
        System.out.println("4. Cambiar estado");
        System.out.println("5. Eliminar vigilante");
        System.out.println("6. Volver");
    }

    private void crearVigilante() {
        System.out.println();
        System.out.println("=== CREAR VIGILANTE ===");

        String tip = consola.pedirTip();

        if (vigilanteService.buscarPorTip(tip) != null) {
            System.out.println(
                    "Ya existe un vigilante con ese TIP."
            );

            consola.esperarEnter();
            return;
        }

        String nombre = consola.pedirTextoNoVacio(
                "Nombre: "
        );

        boolean activo = consola.pedirSiNo(
                "¿Está activo? (s/n): "
        );

        boolean creado =
                vigilanteService.crearVigilante(
                        nombre,
                        tip,
                        activo
                );

        if (creado) {
            System.out.println(
                    "Vigilante creado correctamente."
            );
        } else {
            System.out.println(
                    "No se pudo crear el vigilante."
            );
        }

        consola.esperarEnter();
    }

    private void mostrarVigilantes() {
        System.out.println();
        System.out.println("=== VIGILANTES ===");

        List<Vigilante> vigilantes =
                vigilanteService.obtenerTodos();

        if (vigilantes.isEmpty()) {
            System.out.println(
                    "No hay vigilantes registrados."
            );

            consola.esperarEnter();
            return;
        }

        for (Vigilante vigilante : vigilantes) {
            mostrarDatosVigilante(vigilante);
            System.out.println("--------------------");
        }

        consola.esperarEnter();
    }

    private void buscarVigilante() {
        System.out.println();
        System.out.println("=== BUSCAR VIGILANTE ===");

        String tip = consola.pedirTip();

        Vigilante vigilante =
                vigilanteService.buscarPorTip(tip);

        if (vigilante == null) {
            System.out.println(
                    "Vigilante no encontrado."
            );

            consola.esperarEnter();
            return;
        }

        System.out.println("Vigilante encontrado:");
        mostrarDatosVigilante(vigilante);

        consola.esperarEnter();
    }

    private void cambiarEstadoVigilante() {
        System.out.println();
        System.out.println(
                "=== CAMBIAR ESTADO DE VIGILANTE ==="
        );

        String tip = consola.pedirTip();

        Vigilante vigilante =
                vigilanteService.buscarPorTip(tip);

        if (vigilante == null) {
            System.out.println(
                    "Vigilante no encontrado."
            );

            consola.esperarEnter();
            return;
        }

        vigilanteService.cambiarEstado(tip);

        String nuevoEstado =
                vigilante.isActivo()
                        ? "activo"
                        : "inactivo";

        System.out.println(
                "El vigilante ahora está " +
                nuevoEstado + "."
        );

        consola.esperarEnter();
    }

    private void eliminarVigilante() {
        System.out.println();
        System.out.println("=== ELIMINAR VIGILANTE ===");

        String tip = consola.pedirTip();

        Vigilante vigilante =
                vigilanteService.buscarPorTip(tip);

        if (vigilante == null) {
            System.out.println(
                    "Vigilante no encontrado."
            );

            consola.esperarEnter();
            return;
        }

        System.out.println("Vigilante encontrado:");
        mostrarDatosVigilante(vigilante);

        boolean confirmar = consola.pedirSiNo(
                "¿Confirmas que deseas eliminarlo? (s/n): "
        );

        if (confirmar) {
            vigilanteService.eliminarPorTip(tip);

            System.out.println(
                    "Vigilante eliminado correctamente."
            );
        } else {
            System.out.println(
                    "Operación cancelada."
            );
        }

        consola.esperarEnter();
    }

    private void mostrarDatosVigilante(
            Vigilante vigilante) {

        System.out.println(
                "Nombre: " + vigilante.getNombre()
        );

        System.out.println(
                "TIP: " + vigilante.getTip()
        );

        System.out.println(
                "Activo: " +
                (vigilante.isActivo() ? "Sí" : "No")
        );
    }
}
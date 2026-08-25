package com.inaki.shiftguard.console;

public class MenuPrincipal {

    private final MenuVigilantes menuVigilantes;
    private final MenuCentrosTrabajo menuCentrosTrabajo;
    private final MenuTurnos menuTurnos;
    private final Consola consola;

    public MenuPrincipal(
            MenuVigilantes menuVigilantes,
            MenuCentrosTrabajo menuCentrosTrabajo,
            MenuTurnos menuTurnos,
            Consola consola) {

        this.menuVigilantes = menuVigilantes;
        this.menuCentrosTrabajo = menuCentrosTrabajo;
        this.menuTurnos = menuTurnos;
        this.consola = consola;
    }

    public void iniciar() {
        boolean ejecutando = true;

        while (ejecutando) {
            mostrarMenu();
            int opcion = consola.leerOpcion();

            switch (opcion) {
                case 1 ->
                    menuVigilantes.iniciar();

                case 2 ->
                    menuCentrosTrabajo.iniciar();

                case 3 ->
                    menuTurnos.iniciar();

                case 4 -> {
                    ejecutando = false;
                    System.out.println(
                            "Cerrando ShiftGuard..."
                    );
                }

                default ->
                    System.out.println(
                            "Opción no válida."
                    );
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=== SHIFTGUARD ===");
        System.out.println("1. Gestión de vigilantes");
        System.out.println("2. Gestión de centros de trabajo");
        System.out.println("3. Gestión de turnos");
        System.out.println("4. Salir");
    }
}
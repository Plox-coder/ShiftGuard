package com.inaki.shiftguard.console;

import java.util.Scanner;

public class Consola {

    private final Scanner scanner;

    public Consola() {
        this.scanner = new Scanner(System.in);
    }

    public int leerOpcion() {
        while (true) {
            System.out.print("Selecciona una opción: ");
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debes introducir un número."
                );
            }
        }
    }

    public String pedirTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isBlank()) {
                return texto;
            }

            System.out.println(
                    "Este campo no puede estar vacío."
            );
        }
    }

    public String pedirTip() {
        while (true) {
            System.out.print("Introduce el TIP: ");
            String tip = scanner.nextLine().trim();

            if (tip.isBlank()) {
                System.out.println(
                        "El TIP no puede estar vacío."
                );
            } else if (!tip.matches("\\d+")) {
                System.out.println(
                        "El TIP solo puede contener números."
                );
            } else {
                return tip;
            }
        }
    }

    public boolean pedirSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim();

            if (respuesta.equalsIgnoreCase("s")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println(
                    "Debes responder con s o n."
            );
        }
    }

    public void esperarEnter() {
        System.out.println();
        System.out.print(
                "Pulsa Enter para continuar..."
        );
        scanner.nextLine();
    }

    public void cerrar() {
        scanner.close();
    }
}
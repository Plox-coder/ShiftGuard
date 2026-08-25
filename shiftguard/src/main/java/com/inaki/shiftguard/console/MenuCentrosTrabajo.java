package com.inaki.shiftguard.console;

import java.util.List;

import com.inaki.shiftguard.model.CentroTrabajo;
import com.inaki.shiftguard.service.CentroTrabajoService;

public class MenuCentrosTrabajo {

    private final CentroTrabajoService centroTrabajoService;
    private final Consola consola;

    public MenuCentrosTrabajo(
            CentroTrabajoService centroTrabajoService,
            Consola consola) {

        this.centroTrabajoService = centroTrabajoService;
        this.consola = consola;
    }

    public void iniciar() {
        boolean volver = false;

        while (!volver) {
            mostrarMenu();
            int opcion = consola.leerOpcion();

            switch (opcion) {
                case 1 ->
                    crearCentroTrabajo();

                case 2 ->
                    mostrarCentrosTrabajo();

                case 3 ->
                    buscarCentroTrabajo();

                case 4 ->
                    cambiarEstadoCentroTrabajo();

                case 5 ->
                    eliminarCentroTrabajo();

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
        System.out.println("=== CENTROS DE TRABAJO ===");
        System.out.println("1. Crear centro");
        System.out.println("2. Mostrar centros");
        System.out.println("3. Buscar centro");
        System.out.println("4. Cambiar estado");
        System.out.println("5. Eliminar centro");
        System.out.println("6. Volver");
    }

    private void crearCentroTrabajo() {
        System.out.println();
        System.out.println(
                "=== CREAR CENTRO DE TRABAJO ==="
        );

        String codigo = consola.pedirTextoNoVacio(
                "Código: "
        );

        if (centroTrabajoService.buscarPorCodigo(codigo) != null) {
            System.out.println(
                    "Ya existe un centro con ese código."
            );

            consola.esperarEnter();
            return;
        }

        String nombre = consola.pedirTextoNoVacio(
                "Nombre: "
        );

        String direccion = consola.pedirTextoNoVacio(
                "Dirección: "
        );

        boolean activo = consola.pedirSiNo(
                "¿Está activo? (s/n): "
        );

        boolean creado =
                centroTrabajoService.crearCentroTrabajo(
                        codigo,
                        nombre,
                        direccion,
                        activo
                );

        if (creado) {
            System.out.println(
                    "Centro creado correctamente."
            );
        } else {
            System.out.println(
                    "No se pudo crear el centro."
            );
        }

        consola.esperarEnter();
    }

    private void mostrarCentrosTrabajo() {
        System.out.println();
        System.out.println(
                "=== CENTROS DE TRABAJO ==="
        );

        List<CentroTrabajo> centros =
                centroTrabajoService.obtenerTodos();

        if (centros.isEmpty()) {
            System.out.println(
                    "No hay centros registrados."
            );

            consola.esperarEnter();
            return;
        }

        for (CentroTrabajo centro : centros) {
            mostrarDatosCentroTrabajo(centro);
            System.out.println("--------------------");
        }

        consola.esperarEnter();
    }

    private void buscarCentroTrabajo() {
        System.out.println();
        System.out.println("=== BUSCAR CENTRO ===");

        String texto = consola.pedirTextoNoVacio(
                "Introduce código, nombre o dirección: "
        );

        List<CentroTrabajo> resultados =
                centroTrabajoService.buscarPorTexto(texto);

        if (resultados.isEmpty()) {
            System.out.println(
                    "No se encontraron centros."
            );

            consola.esperarEnter();
            return;
        }

        System.out.println(
                "Centros encontrados: " +
                resultados.size()
        );

        for (CentroTrabajo centro : resultados) {
            mostrarDatosCentroTrabajo(centro);
            System.out.println("--------------------");
        }

        consola.esperarEnter();
    }

    private void cambiarEstadoCentroTrabajo() {
        System.out.println();
        System.out.println(
                "=== CAMBIAR ESTADO DEL CENTRO ==="
        );

        String codigo = consola.pedirTextoNoVacio(
                "Código del centro: "
        );

        CentroTrabajo centro =
                centroTrabajoService.buscarPorCodigo(codigo);

        if (centro == null) {
            System.out.println(
                    "Centro no encontrado."
            );

            consola.esperarEnter();
            return;
        }

        centroTrabajoService.cambiarEstado(codigo);

        String nuevoEstado =
                centro.isActivo()
                        ? "activo"
                        : "inactivo";

        System.out.println(
                "El centro ahora está " +
                nuevoEstado + "."
        );

        consola.esperarEnter();
    }

    private void eliminarCentroTrabajo() {
        System.out.println();
        System.out.println(
                "=== ELIMINAR CENTRO DE TRABAJO ==="
        );

        String codigo = consola.pedirTextoNoVacio(
                "Código del centro: "
        );

        CentroTrabajo centro =
                centroTrabajoService.buscarPorCodigo(codigo);

        if (centro == null) {
            System.out.println(
                    "Centro no encontrado."
            );

            consola.esperarEnter();
            return;
        }

        System.out.println("Centro encontrado:");
        mostrarDatosCentroTrabajo(centro);

        boolean confirmar = consola.pedirSiNo(
                "¿Confirmas que deseas eliminarlo? (s/n): "
        );

        if (confirmar) {
            centroTrabajoService.eliminarPorCodigo(codigo);

            System.out.println(
                    "Centro eliminado correctamente."
            );
        } else {
            System.out.println(
                    "Operación cancelada."
            );
        }

        consola.esperarEnter();
    }

    private void mostrarDatosCentroTrabajo(
            CentroTrabajo centro) {

        System.out.println(
                "Código: " + centro.getCodigo()
        );

        System.out.println(
                "Nombre: " + centro.getNombre()
        );

        System.out.println(
                "Dirección: " + centro.getDireccion()
        );

        System.out.println(
                "Activo: " +
                (centro.isActivo() ? "Sí" : "No")
        );
    }
}
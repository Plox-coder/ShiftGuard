package com.inaki.shiftguard.console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.inaki.shiftguard.model.Turno;
import com.inaki.shiftguard.service.TurnoService;
import com.inaki.shiftguard.service.TurnoService.ResultadoOperacionTurno;

public class MenuTurnos {

    private static final DateTimeFormatter FORMATO_FECHA_HORA =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
            );

    private final TurnoService turnoService;
    private final Consola consola;

    public MenuTurnos(
            TurnoService turnoService,
            Consola consola) {

        this.turnoService = turnoService;
        this.consola = consola;
    }

    public void iniciar() {
        boolean volver = false;

        while (!volver) {
            mostrarMenu();
            int opcion = consola.leerOpcion();

            switch (opcion) {
                case 1 ->
                    crearTurno();

                case 2 ->
                    mostrarTodosLosTurnos();

                case 3 ->
                    buscarTurnoPorId();

                case 4 ->
                    buscarTurnosPorVigilante();

                case 5 ->
                    buscarTurnosPorCentro();

                case 6 ->
                    modificarTurno();

                case 7 ->
                    eliminarTurno();

                case 8 ->
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
        System.out.println("=== TURNOS ===");
        System.out.println("1. Crear turno");
        System.out.println("2. Mostrar todos los turnos");
        System.out.println("3. Buscar turno por ID");
        System.out.println("4. Buscar turnos por vigilante");
        System.out.println("5. Buscar turnos por centro");
        System.out.println("6. Modificar turno");
        System.out.println("7. Eliminar turno");
        System.out.println("8. Volver");
    }

    private void crearTurno() {
        System.out.println();
        System.out.println("=== CREAR TURNO ===");

        String tipVigilante = consola.pedirTip();

        String codigoCentro = consola.pedirTextoNoVacio(
                "Código del centro: "
        );

        LocalDateTime inicio = consola.pedirFechaHora(
                "Inicio (dd/MM/yyyy HH:mm): "
        );

        LocalDateTime fin = consola.pedirFechaHora(
                "Final (dd/MM/yyyy HH:mm): "
        );

        ResultadoOperacionTurno resultado =
                turnoService.crearTurno(
                        tipVigilante,
                        codigoCentro,
                        inicio,
                        fin
                );

        mostrarResultadoOperacion(resultado);
        consola.esperarEnter();
    }

    private void mostrarTodosLosTurnos() {
        System.out.println();
        System.out.println("=== TODOS LOS TURNOS ===");

        List<Turno> turnos =
                turnoService.obtenerTodos();

        mostrarListaTurnos(
                turnos,
                "No hay turnos registrados."
        );

        consola.esperarEnter();
    }

    private void buscarTurnoPorId() {
        System.out.println();
        System.out.println("=== BUSCAR TURNO POR ID ===");

        Long id = consola.pedirLongPositivo(
                "ID del turno: "
        );

        Turno turno = turnoService.buscarPorId(id);

        if (turno == null) {
            System.out.println("Turno no encontrado.");
            consola.esperarEnter();
            return;
        }

        mostrarDatosTurno(turno);
        consola.esperarEnter();
    }

    private void buscarTurnosPorVigilante() {
        System.out.println();
        System.out.println(
                "=== TURNOS DE UN VIGILANTE ==="
        );

        String tip = consola.pedirTip();

        List<Turno> turnos =
                turnoService.buscarPorVigilante(tip);

        mostrarListaTurnos(
                turnos,
                "El vigilante no tiene turnos asignados."
        );

        consola.esperarEnter();
    }

    private void buscarTurnosPorCentro() {
        System.out.println();
        System.out.println(
                "=== TURNOS DE UN CENTRO ==="
        );

        String codigoCentro =
                consola.pedirTextoNoVacio(
                        "Código del centro: "
                );

        List<Turno> turnos =
                turnoService.buscarPorCentro(
                        codigoCentro
                );

        mostrarListaTurnos(
                turnos,
                "El centro no tiene turnos asignados."
        );

        consola.esperarEnter();
    }

    private void modificarTurno() {
        System.out.println();
        System.out.println("=== MODIFICAR TURNO ===");

        Long id = consola.pedirLongPositivo(
                "ID del turno: "
        );

        Turno turno = turnoService.buscarPorId(id);

        if (turno == null) {
            System.out.println("Turno no encontrado.");
            consola.esperarEnter();
            return;
        }

        System.out.println("Datos actuales:");
        mostrarDatosTurno(turno);

        System.out.println();
        System.out.println("Introduce los nuevos datos:");

        String nuevoTip = consola.pedirTip();

        String nuevoCodigoCentro =
                consola.pedirTextoNoVacio(
                        "Código del centro: "
                );

        LocalDateTime nuevoInicio =
                consola.pedirFechaHora(
                        "Inicio (dd/MM/yyyy HH:mm): "
                );

        LocalDateTime nuevoFin =
                consola.pedirFechaHora(
                        "Final (dd/MM/yyyy HH:mm): "
                );

        ResultadoOperacionTurno resultado =
                turnoService.modificarTurno(
                        id,
                        nuevoTip,
                        nuevoCodigoCentro,
                        nuevoInicio,
                        nuevoFin
                );

        mostrarResultadoOperacion(resultado);
        consola.esperarEnter();
    }

    private void eliminarTurno() {
        System.out.println();
        System.out.println("=== ELIMINAR TURNO ===");

        Long id = consola.pedirLongPositivo(
                "ID del turno: "
        );

        Turno turno = turnoService.buscarPorId(id);

        if (turno == null) {
            System.out.println("Turno no encontrado.");
            consola.esperarEnter();
            return;
        }

        System.out.println("Turno encontrado:");
        mostrarDatosTurno(turno);

        boolean confirmar = consola.pedirSiNo(
                "¿Confirmas que deseas eliminarlo? (s/n): "
        );

        if (confirmar) {
            turnoService.eliminarPorId(id);

            System.out.println(
                    "Turno eliminado correctamente."
            );
        } else {
            System.out.println(
                    "Operación cancelada."
            );
        }

        consola.esperarEnter();
    }

    private void mostrarListaTurnos(
            List<Turno> turnos,
            String mensajeListaVacia) {

        if (turnos.isEmpty()) {
            System.out.println(mensajeListaVacia);
            return;
        }

        for (Turno turno : turnos) {
            mostrarDatosTurno(turno);
            System.out.println("--------------------");
        }
    }

    private void mostrarDatosTurno(Turno turno) {
        long minutos =
                turno.getDuracion().toMinutes();

        long horas = minutos / 60;
        long minutosRestantes = minutos % 60;

        System.out.println(
                "ID: " + turno.getId()
        );

        System.out.println(
                "Vigilante: "
                + turno.getVigilante().getNombre()
                + " - TIP: "
                + turno.getVigilante().getTip()
        );

        System.out.println(
                "Centro: "
                + turno.getCentroTrabajo().getNombre()
                + " - Código: "
                + turno.getCentroTrabajo().getCodigo()
        );

        System.out.println(
                "Inicio: "
                + turno.getInicio().format(
                        FORMATO_FECHA_HORA
                )
        );

        System.out.println(
                "Final: "
                + turno.getFin().format(
                        FORMATO_FECHA_HORA
                )
        );

        System.out.println(
                "Duración: "
                + horas
                + " h "
                + minutosRestantes
                + " min"
        );
    }

    private void mostrarResultadoOperacion(
            ResultadoOperacionTurno resultado) {

        switch (resultado) {
            case CREADO ->
                System.out.println(
                        "Turno creado correctamente."
                );

            case MODIFICADO ->
                System.out.println(
                        "Turno modificado correctamente."
                );

            case TURNO_NO_ENCONTRADO ->
                System.out.println(
                        "Turno no encontrado."
                );

            case VIGILANTE_NO_ENCONTRADO ->
                System.out.println(
                        "Vigilante no encontrado."
                );

            case CENTRO_NO_ENCONTRADO ->
                System.out.println(
                        "Centro de trabajo no encontrado."
                );

            case VIGILANTE_INACTIVO ->
                System.out.println(
                        "No se puede asignar el turno: "
                        + "el vigilante está inactivo."
                );

            case CENTRO_INACTIVO ->
                System.out.println(
                        "No se puede asignar el turno: "
                        + "el centro está inactivo."
                );

            case FECHAS_INVALIDAS ->
                System.out.println(
                        "Las fechas no son válidas. "
                        + "El final debe ser posterior al inicio."
                );

            case SOLAPAMIENTO ->
                System.out.println(
                        "El vigilante ya tiene otro turno "
                        + "en ese horario."
                );
        }
    }
}
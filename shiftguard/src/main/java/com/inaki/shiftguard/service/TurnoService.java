package com.inaki.shiftguard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.inaki.shiftguard.model.CentroTrabajo;
import com.inaki.shiftguard.model.Turno;
import com.inaki.shiftguard.model.Vigilante;

public class TurnoService {

    public enum ResultadoOperacionTurno {
        CREADO,
        MODIFICADO,
        TURNO_NO_ENCONTRADO,
        VIGILANTE_NO_ENCONTRADO,
        CENTRO_NO_ENCONTRADO,
        VIGILANTE_INACTIVO,
        CENTRO_INACTIVO,
        FECHAS_INVALIDAS,
        SOLAPAMIENTO
    }

    private final List<Turno> turnos;

    private final VigilanteService vigilanteService;
    private final CentroTrabajoService centroTrabajoService;

    private Long siguienteId;

    public TurnoService(
            VigilanteService vigilanteService,
            CentroTrabajoService centroTrabajoService) {

        this.vigilanteService = Objects.requireNonNull(
                vigilanteService
        );

        this.centroTrabajoService = Objects.requireNonNull(
                centroTrabajoService
        );

        this.turnos = new ArrayList<>();
        this.siguienteId = 1L;
    }

    public ResultadoOperacionTurno crearTurno(
            String tipVigilante,
            String codigoCentro,
            LocalDateTime inicio,
            LocalDateTime fin) {

        if (!fechasValidas(inicio, fin)) {
            return ResultadoOperacionTurno.FECHAS_INVALIDAS;
        }

        Vigilante vigilante =
                vigilanteService.buscarPorTip(tipVigilante);

        if (vigilante == null) {
            return ResultadoOperacionTurno
                    .VIGILANTE_NO_ENCONTRADO;
        }

        CentroTrabajo centroTrabajo =
                centroTrabajoService.buscarPorCodigo(
                        codigoCentro
                );

        if (centroTrabajo == null) {
            return ResultadoOperacionTurno
                    .CENTRO_NO_ENCONTRADO;
        }

        if (!vigilante.isActivo()) {
            return ResultadoOperacionTurno
                    .VIGILANTE_INACTIVO;
        }

        if (!centroTrabajo.isActivo()) {
            return ResultadoOperacionTurno
                    .CENTRO_INACTIVO;
        }

        if (tieneSolapamiento(
                vigilante,
                inicio,
                fin,
                null)) {

            return ResultadoOperacionTurno.SOLAPAMIENTO;
        }

        Turno nuevoTurno = new Turno(
                siguienteId,
                inicio,
                fin,
                vigilante,
                centroTrabajo
        );

        turnos.add(nuevoTurno);
        siguienteId++;

        return ResultadoOperacionTurno.CREADO;
    }

    public ResultadoOperacionTurno modificarTurno(
            Long id,
            String nuevoTipVigilante,
            String nuevoCodigoCentro,
            LocalDateTime nuevoInicio,
            LocalDateTime nuevoFin) {

        Turno turno = buscarPorId(id);

        if (turno == null) {
            return ResultadoOperacionTurno
                    .TURNO_NO_ENCONTRADO;
        }

        if (!fechasValidas(nuevoInicio, nuevoFin)) {
            return ResultadoOperacionTurno
                    .FECHAS_INVALIDAS;
        }

        Vigilante nuevoVigilante =
                vigilanteService.buscarPorTip(
                        nuevoTipVigilante
                );

        if (nuevoVigilante == null) {
            return ResultadoOperacionTurno
                    .VIGILANTE_NO_ENCONTRADO;
        }

        CentroTrabajo nuevoCentro =
                centroTrabajoService.buscarPorCodigo(
                        nuevoCodigoCentro
                );

        if (nuevoCentro == null) {
            return ResultadoOperacionTurno
                    .CENTRO_NO_ENCONTRADO;
        }

        if (!nuevoVigilante.isActivo()) {
            return ResultadoOperacionTurno
                    .VIGILANTE_INACTIVO;
        }

        if (!nuevoCentro.isActivo()) {
            return ResultadoOperacionTurno
                    .CENTRO_INACTIVO;
        }

        if (tieneSolapamiento(
                nuevoVigilante,
                nuevoInicio,
                nuevoFin,
                id)) {

            return ResultadoOperacionTurno.SOLAPAMIENTO;
        }

        turno.reprogramar(
                nuevoInicio,
                nuevoFin
        );

        turno.reasignarVigilante(
                nuevoVigilante
        );

        turno.cambiarCentroTrabajo(
                nuevoCentro
        );

        return ResultadoOperacionTurno.MODIFICADO;
    }

    public List<Turno> obtenerTodos() {
        return List.copyOf(turnos);
    }

    public Turno buscarPorId(Long id) {

        if (id == null) {
            return null;
        }

        for (Turno turno : turnos) {
            if (turno.getId().equals(id)) {
                return turno;
            }
        }

        return null;
    }

    public List<Turno> buscarPorVigilante(
            String tipVigilante) {

        if (tipVigilante == null
                || tipVigilante.isBlank()) {

            return List.of();
        }

        List<Turno> resultados =
                new ArrayList<>();

        for (Turno turno : turnos) {

            String tipDelTurno =
                    turno.getVigilante().getTip();

            if (tipDelTurno.equalsIgnoreCase(
                    tipVigilante.trim())) {

                resultados.add(turno);
            }
        }

        return List.copyOf(resultados);
    }

    public List<Turno> buscarPorCentro(
            String codigoCentro) {

        if (codigoCentro == null
                || codigoCentro.isBlank()) {

            return List.of();
        }

        List<Turno> resultados =
                new ArrayList<>();

        for (Turno turno : turnos) {

            String codigoDelTurno =
                    turno.getCentroTrabajo().getCodigo();

            if (codigoDelTurno.equalsIgnoreCase(
                    codigoCentro.trim())) {

                resultados.add(turno);
            }
        }

        return List.copyOf(resultados);
    }

    public boolean eliminarPorId(Long id) {

        Turno turno = buscarPorId(id);

        if (turno == null) {
            return false;
        }

        turnos.remove(turno);
        return true;
    }

    public boolean tieneTurnosElVigilante(
            String tipVigilante) {

        return !buscarPorVigilante(
                tipVigilante
        ).isEmpty();
    }

    public boolean tieneTurnosElCentro(
            String codigoCentro) {

        return !buscarPorCentro(
                codigoCentro
        ).isEmpty();
    }

    private boolean fechasValidas(
            LocalDateTime inicio,
            LocalDateTime fin) {

        return inicio != null
                && fin != null
                && fin.isAfter(inicio);
    }

    private boolean tieneSolapamiento(
            Vigilante vigilante,
            LocalDateTime nuevoInicio,
            LocalDateTime nuevoFin,
            Long idTurnoIgnorado) {

        for (Turno turno : turnos) {

            if (idTurnoIgnorado != null
                    && turno.getId()
                            .equals(idTurnoIgnorado)) {

                continue;
            }

            boolean mismoVigilante =
                    turno.getVigilante()
                            .getTip()
                            .equalsIgnoreCase(
                                    vigilante.getTip()
                            );

            boolean horariosSolapados =
                    nuevoInicio.isBefore(
                            turno.getFin()
                    )
                    && nuevoFin.isAfter(
                            turno.getInicio()
                    );

            if (mismoVigilante
                    && horariosSolapados) {

                return true;
            }
        }

        return false;
    }
}
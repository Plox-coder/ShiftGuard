package com.inaki.shiftguard.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.inaki.shiftguard.model.Turno;
import com.inaki.shiftguard.model.Vigilante;

public class TurnoService {

    private final List<Turno> turnos;

    public TurnoService() {
        this.turnos = new ArrayList<>();
    }

    public boolean agregarTurno(Turno turno) {

        if (turno == null) {
            return false;
        }

        if (turno.getId() == null) {
            return false;
        }

        if (buscarTurnoPorId(turno.getId()) != null) {
            return false;
        }

        if (!horarioValido(
                turno.getHoraInicio(),
                turno.getHoraFin()
        )) {
            return false;
        }

        turnos.add(turno);
        return true;
    }

    public List<Turno> obtenerTurnos() {
        return List.copyOf(turnos);
    }

    public Turno buscarTurnoPorId(Long id) {

        if (id == null) {
            return null;
        }

        for (Turno turno : turnos) {

            if (Objects.equals(turno.getId(), id)) {
                return turno;
            }
        }

        return null;
    }

    public boolean modificarTurno(
            Long id,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            Vigilante vigilante
    ) {

        Turno turno = buscarTurnoPorId(id);

        if (turno == null) {
            return false;
        }

        if (fecha == null ||
                horaInicio == null ||
                horaFin == null ||
                vigilante == null) {

            return false;
        }

        if (!horarioValido(horaInicio, horaFin)) {
            return false;
        }

        turno.setFecha(fecha);
        turno.setHoraInicio(horaInicio);
        turno.setHoraFin(horaFin);
        turno.setVigilante(vigilante);

        return true;
    }

    public boolean eliminarTurnoPorId(Long id) {

        Turno turno = buscarTurnoPorId(id);

        if (turno == null) {
            return false;
        }

        turnos.remove(turno);
        return true;
    }

    public boolean existeTurno(Long id) {
        return buscarTurnoPorId(id) != null;
    }

    private boolean horarioValido(
            LocalTime horaInicio,
            LocalTime horaFin
    ) {

        if (horaInicio == null || horaFin == null) {
            return false;
        }

        return horaFin.isAfter(horaInicio);
    }
}
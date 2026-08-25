package com.inaki.shiftguard.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Turno {

    private final Long id;

    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Vigilante vigilante;
    private CentroTrabajo centroTrabajo;

    public Turno(
            Long id,
            LocalDateTime inicio,
            LocalDateTime fin,
            Vigilante vigilante,
            CentroTrabajo centroTrabajo) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El identificador debe ser positivo."
            );
        }

        this.id = id;

        reprogramar(inicio, fin);
        reasignarVigilante(vigilante);
        cambiarCentroTrabajo(centroTrabajo);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public CentroTrabajo getCentroTrabajo() {
        return centroTrabajo;
    }

    public Duration getDuracion() {
        return Duration.between(inicio, fin);
    }

    public void reprogramar(
            LocalDateTime nuevoInicio,
            LocalDateTime nuevoFin) {

        Objects.requireNonNull(
                nuevoInicio,
                "El inicio es obligatorio."
        );

        Objects.requireNonNull(
                nuevoFin,
                "El final es obligatorio."
        );

        if (!nuevoFin.isAfter(nuevoInicio)) {
            throw new IllegalArgumentException(
                    "El final debe ser posterior al inicio."
            );
        }

        this.inicio = nuevoInicio;
        this.fin = nuevoFin;
    }

    public void reasignarVigilante(
            Vigilante nuevoVigilante) {

        this.vigilante = Objects.requireNonNull(
                nuevoVigilante,
                "El vigilante es obligatorio."
        );
    }

    public void cambiarCentroTrabajo(
            CentroTrabajo nuevoCentroTrabajo) {

        this.centroTrabajo = Objects.requireNonNull(
                nuevoCentroTrabajo,
                "El centro de trabajo es obligatorio."
        );
    }
}
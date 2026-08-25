package com.inaki.shiftguard.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Turno {

    private final long id;
    private final Vigilante vigilante;
    private final CentroTrabajo centroTrabajo;
    private final LocalDateTime inicio;
    private final LocalDateTime fin;

    public Turno(long id, Vigilante vigilante, CentroTrabajo centroTrabajo, LocalDateTime inicio, LocalDateTime fin) {

        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }

        this.id = id;

        this.vigilante = Objects.requireNonNull(vigilante, "El vigilante es obligatorio.");

        this.centroTrabajo = Objects.requireNonNull(centroTrabajo, "El centro de trabajo es obligatorio.");

        this.inicio = Objects.requireNonNull(inicio, "El inicio es obligatorio.");

        this.fin = Objects.requireNonNull(fin,"El final es obligatorio.");

        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("El final debe ser posterior al inicio.");
        }
    }

    public long getId() {
        return id;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public CentroTrabajo getCentroTrabajo() {
        return centroTrabajo;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public Duration getDuracion() {
        return Duration.between(inicio, fin);
    }
}
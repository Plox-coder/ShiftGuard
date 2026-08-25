package com.inaki.shiftguard.service;

import java.util.ArrayList;
import java.util.List;

import com.inaki.shiftguard.model.Vigilante;

public class VigilanteService {

    private final List<Vigilante> vigilantes;

    public VigilanteService() {
        this.vigilantes = new ArrayList<>();
    }

    public boolean crearVigilante(String nombre, String tip, boolean activo) {
        if (!esTipValido(tip)) {
            return false;
        }

        if (buscarPorTip(tip) != null) {
            return false;
        }

        Vigilante nuevoVigilante = new Vigilante(nombre, tip, activo);

        vigilantes.add(nuevoVigilante);

        return true;
    }

    public List<Vigilante> obtenerTodos() {
        return List.copyOf(vigilantes);
    }

    public Vigilante buscarPorTip(String tipBuscado) {

        for (Vigilante vigilante : vigilantes) {
            if (vigilante.getTip().equalsIgnoreCase(tipBuscado)) {
                return vigilante;
            }
        }

        return null;
    }

    public boolean cambiarEstado(String tip) {

        Vigilante vigilante = buscarPorTip(tip);

        if (vigilante == null) {
            return false;
        }

        vigilante.setActivo(!vigilante.isActivo());

        return true;
    }

    public boolean eliminarPorTip(String tip) {

        Vigilante vigilante = buscarPorTip(tip);

        if (vigilante == null) {
            return false;
        }

        vigilantes.remove(vigilante);

        return true;
    }
    private boolean esTipValido(String tip) {
        return tip != null && !tip.isBlank() && tip.matches("\\d+");
    }
}
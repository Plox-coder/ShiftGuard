package com.inaki.shiftguard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.inaki.shiftguard.model.CentroTrabajo;

public class CentroTrabajoService {

    private final List<CentroTrabajo> centrosTrabajo;

    public CentroTrabajoService() {
        this.centrosTrabajo = new ArrayList<>();
    }

    public boolean crearCentroTrabajo(String codigo, String nombre, String direccion, boolean activo) {

        if (!datosValidos(codigo, nombre, direccion)) {
            return false;
        }

        if (buscarPorCodigo(codigo) != null) {
            return false;
        }

        CentroTrabajo nuevoCentro =
                new CentroTrabajo(
                        codigo.trim(),
                        nombre.trim(),
                        direccion.trim(),
                        activo
                );

        centrosTrabajo.add(nuevoCentro);

        return true;
    }

    public List<CentroTrabajo> obtenerTodos() {
        return List.copyOf(centrosTrabajo);
    }

    public CentroTrabajo buscarPorCodigo(
            String codigoBuscado) {

        if (codigoBuscado == null) {
            return null;
        }

        for (CentroTrabajo centro : centrosTrabajo) {
            if (centro.getCodigo().equalsIgnoreCase(
                    codigoBuscado.trim())) {

                return centro;
            }
        }

        return null;
    }

    public boolean cambiarEstado(String codigo) {

        CentroTrabajo centro =
                buscarPorCodigo(codigo);

        if (centro == null) {
            return false;
        }

        centro.setActivo(!centro.isActivo());

        return true;
    }

    public boolean eliminarPorCodigo(String codigo) {

        CentroTrabajo centro =
                buscarPorCodigo(codigo);

        if (centro == null) {
            return false;
        }

        centrosTrabajo.remove(centro);

        return true;
    }

    private boolean datosValidos(String codigo, String nombre, String direccion) {

        return codigo != null && !codigo.isBlank() && nombre != null && !nombre.isBlank() && direccion != null && !direccion.isBlank();
    }
    public List<CentroTrabajo> buscarPorTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return List.of();
        }

        String textoNormalizado = texto.trim().toLowerCase(Locale.ROOT);

        List<CentroTrabajo> resultados = new ArrayList<>();

        for (CentroTrabajo centro : centrosTrabajo) {

            String codigo = centro.getCodigo()
                    .toLowerCase(Locale.ROOT);

            String nombre = centro.getNombre()
                    .toLowerCase(Locale.ROOT);

            String direccion = centro.getDireccion()
                    .toLowerCase(Locale.ROOT);

            if (codigo.contains(textoNormalizado)
                    || nombre.contains(textoNormalizado)
                    || direccion.contains(textoNormalizado)) {

                resultados.add(centro);
            }
        }

        return List.copyOf(resultados);
    }
}

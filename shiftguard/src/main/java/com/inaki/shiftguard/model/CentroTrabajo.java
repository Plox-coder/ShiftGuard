package com.inaki.shiftguard.model;

public class CentroTrabajo {

    private final String codigo;
    private final String nombre;
    private final String direccion;
    private boolean activo;

    public CentroTrabajo(String codigo, String nombre, String direccion, boolean activo) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.activo = activo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
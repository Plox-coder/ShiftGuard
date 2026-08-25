package com.inaki.shiftguard.model;

public class Vigilante {

    private final String nombre;
    private final String tip;
    private boolean activo;

    public Vigilante(String nombre, String tip, boolean activo){
        this.nombre = nombre;
        this.tip = tip;
        this.activo = activo;
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getTip() {
        return this.tip;
    }
    public boolean isActivo(){
        return this.activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    
    }
}
package com.idra.gestionpeluqueria.model.enums;

public enum EstadoTurno {
    PENDIENTE("Pendiente"),
    CONFIRMADO("Confirmado"),
    COMPLETADO("Completado"),
    CANCELADO("Cancelado"),
    AUSENTE("Ausente");
    
    private final String descripcion;
    
    private EstadoTurno(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public String toString() {
        return descripcion;
    }
}
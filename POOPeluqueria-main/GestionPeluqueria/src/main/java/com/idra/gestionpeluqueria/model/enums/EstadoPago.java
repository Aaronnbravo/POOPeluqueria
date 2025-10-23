package com.idra.gestionpeluqueria.model.enums;

public enum EstadoPago {
    PAGADO("Pagado"),
    PENDIENTE("Pendiente de pago"),
    ABONADO_PARCIAL("Abonado parcialmente");
    
    private final String descripcion;
    
    private EstadoPago(String descripcion) {
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
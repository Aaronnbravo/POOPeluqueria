package com.idra.gestionpeluqueria.model.enums;

public enum FormaPago {
    EFECTIVO("Efectivo"),
    DEBITO("Tarjeta de Débito"),
    CREDITO("Tarjeta de Crédito"),
    TRANSFERENCIA("Transferencia Bancaria");
    
    private final String descripcion;
    
    private FormaPago(String descripcion) {
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
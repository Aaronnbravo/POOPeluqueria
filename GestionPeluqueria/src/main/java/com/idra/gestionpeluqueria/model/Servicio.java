package com.idra.gestionpeluqueria.model;

import com.idra.gestionpeluqueria.model.enums.TipoServicio;

public class Servicio {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;
    private TipoServicio tipoServicio;
    private boolean activo;
    
    // Constructores
    public Servicio() {}
    
    public Servicio(String nombre, String descripcion, double precio, int duracionMinutos, TipoServicio tipoServicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
        this.tipoServicio = tipoServicio;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    
    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    @Override
    public String toString() {
        return nombre + " - $" + precio + " (" + duracionMinutos + " min)";
    }
}
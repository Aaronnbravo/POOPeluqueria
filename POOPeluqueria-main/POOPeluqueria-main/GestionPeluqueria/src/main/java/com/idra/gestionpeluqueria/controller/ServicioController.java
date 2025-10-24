package com.idra.gestionpeluqueria.controller;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.service.ServicioService;
import com.idra.gestionpeluqueria.service.impl.ServicioServiceImpl;
import com.idra.gestionpeluqueria.dao.impl.ServicioDAOImpl;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.util.List;

public class ServicioController {
    
    private ServicioService servicioService;
    
    public ServicioController() {
        this.servicioService = new ServicioServiceImpl(new ServicioDAOImpl());
    }
    
    public void crearServicio(Servicio servicio) throws ServiceException {
        servicioService.crearServicio(servicio);
    }
    
    public Servicio buscarServicioPorId(int id) throws ServiceException {
        return servicioService.buscarServicioPorId(id);
    }
    
    public List<Servicio> obtenerTodosServicios() throws ServiceException {
        return servicioService.buscarTodosServicios();
    }
    
    public List<Servicio> obtenerServiciosActivos() throws ServiceException {
        return servicioService.buscarServiciosActivos();
    }
    
    public List<Servicio> buscarServiciosPorTipo(String tipoServicio) throws ServiceException {
        return servicioService.buscarServiciosPorTipo(tipoServicio);
    }
    
    public void actualizarServicio(Servicio servicio) throws ServiceException {
        servicioService.actualizarServicio(servicio);
    }
    
    public void eliminarServicio(int id) throws ServiceException {
        servicioService.eliminarServicio(id);
    }
    
    public boolean validarServicio(Servicio servicio) throws ServiceException {
        return servicioService.validarServicio(servicio);
    }
}
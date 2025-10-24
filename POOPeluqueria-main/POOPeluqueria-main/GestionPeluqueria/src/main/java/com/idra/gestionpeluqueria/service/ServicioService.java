package com.idra.gestionpeluqueria.service;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.util.List;

public interface ServicioService {
    void crearServicio(Servicio servicio) throws ServiceException;
    Servicio buscarServicioPorId(int id) throws ServiceException;
    List<Servicio> buscarTodosServicios() throws ServiceException;
    List<Servicio> buscarServiciosActivos() throws ServiceException;
    List<Servicio> buscarServiciosPorTipo(String tipoServicio) throws ServiceException;
    void actualizarServicio(Servicio servicio) throws ServiceException;
    void eliminarServicio(int id) throws ServiceException;
    boolean validarServicio(Servicio servicio) throws ServiceException;
}
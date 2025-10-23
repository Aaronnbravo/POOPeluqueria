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
    
    /**
     * Crea un nuevo servicio en el sistema
     * @param servicio El servicio a crear
     * @throws ServiceException Si ocurre un error al crear el servicio
     */
    public void crearServicio(Servicio servicio) throws ServiceException {
        servicioService.crearServicio(servicio);
    }
    
    /**
     * Busca un servicio por su ID
     * @param id El ID del servicio a buscar
     * @return El servicio encontrado o null si no existe
     * @throws ServiceException Si ocurre un error al buscar el servicio
     */
    public Servicio buscarServicioPorId(int id) throws ServiceException {
        return servicioService.buscarServicioPorId(id);
    }
    
    /**
     * Obtiene todos los servicios del sistema
     * @return Lista de todos los servicios
     * @throws ServiceException Si ocurre un error al obtener los servicios
     */
    public List<Servicio> obtenerTodosServicios() throws ServiceException {
        return servicioService.buscarTodosServicios();
    }
    
    /**
     * Obtiene solo los servicios activos del sistema
     * @return Lista de servicios activos
     * @throws ServiceException Si ocurre un error al obtener los servicios activos
     */
    public List<Servicio> obtenerServiciosActivos() throws ServiceException {
        return servicioService.buscarServiciosActivos();
    }
    
    /**
     * Busca servicios por tipo
     * @param tipoServicio El tipo de servicio a buscar
     * @return Lista de servicios que coinciden con el tipo
     * @throws ServiceException Si ocurre un error al buscar los servicios
     */
    public List<Servicio> buscarServiciosPorTipo(String tipoServicio) throws ServiceException {
        return servicioService.buscarServiciosPorTipo(tipoServicio);
    }
    
    /**
     * Actualiza la información de un servicio existente
     * @param servicio El servicio con la información actualizada
     * @throws ServiceException Si ocurre un error al actualizar el servicio
     */
    public void actualizarServicio(Servicio servicio) throws ServiceException {
        servicioService.actualizarServicio(servicio);
    }
    
    /**
     * Elimina un servicio del sistema
     * @param id El ID del servicio a eliminar
     * @throws ServiceException Si ocurre un error al eliminar el servicio
     */
    public void eliminarServicio(int id) throws ServiceException {
        servicioService.eliminarServicio(id);
    }
    
    /**
     * Valida si los datos del servicio son correctos
     * @param servicio El servicio a validar
     * @return true si los datos son válidos, false en caso contrario
     * @throws ServiceException Si ocurre un error durante la validación
     */
    public boolean validarServicio(Servicio servicio) throws ServiceException {
        return servicioService.validarServicio(servicio);
    }
}
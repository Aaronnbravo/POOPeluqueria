package com.idra.gestionpeluqueria.dao;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.exception.DAOException;
import java.util.List;

public interface ServicioDAO {
    void crear(Servicio servicio) throws DAOException;
    Servicio buscarPorId(int id) throws DAOException;
    List<Servicio> buscarTodos() throws DAOException;
    List<Servicio> buscarActivos() throws DAOException;
    List<Servicio> buscarPorTipo(String tipoServicio) throws DAOException;
    void actualizar(Servicio servicio) throws DAOException;
    void eliminar(int id) throws DAOException;
}
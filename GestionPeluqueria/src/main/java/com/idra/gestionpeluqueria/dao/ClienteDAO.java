package com.idra.gestionpeluqueria.dao;

import com.idra.gestionpeluqueria.model.Cliente;
import com.idra.gestionpeluqueria.exception.DAOException;
import java.util.List;

public interface ClienteDAO {
    void crear(Cliente cliente) throws DAOException;
    Cliente buscarPorId(int id) throws DAOException;
    List<Cliente> buscarTodos() throws DAOException;
    List<Cliente> buscarPorNombre(String nombre) throws DAOException;
    void actualizar(Cliente cliente) throws DAOException;
    void eliminar(int id) throws DAOException;
    boolean existeTelefono(String telefono) throws DAOException;
}
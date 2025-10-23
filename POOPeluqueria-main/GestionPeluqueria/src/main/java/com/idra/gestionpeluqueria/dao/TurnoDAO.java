package com.idra.gestionpeluqueria.dao;

import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.exception.DAOException;
import java.time.LocalDate;
import java.util.List;

public interface TurnoDAO {
    void crear(Turno turno) throws DAOException;
    Turno buscarPorId(int id) throws DAOException;
    List<Turno> buscarTodos() throws DAOException;
    List<Turno> buscarPorFecha(LocalDate fecha) throws DAOException;
    List<Turno> buscarPorCliente(int clienteId) throws DAOException;
    List<Turno> buscarPorEstado(String estado) throws DAOException;
    void actualizar(Turno turno) throws DAOException;
    void eliminar(int id) throws DAOException;
    boolean existeTurnoEnFechaHora(int servicioId, java.time.LocalDateTime fechaHora) throws DAOException;
}
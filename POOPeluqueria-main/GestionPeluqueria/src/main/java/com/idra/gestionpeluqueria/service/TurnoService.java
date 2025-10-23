package com.idra.gestionpeluqueria.service;

import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.time.LocalDate;
import java.util.List;

public interface TurnoService {
    void crearTurno(Turno turno) throws ServiceException;
    Turno buscarTurnoPorId(int id) throws ServiceException;
    List<Turno> buscarTodosTurnos() throws ServiceException;
    List<Turno> buscarTurnosPorFecha(LocalDate fecha) throws ServiceException;
    List<Turno> buscarTurnosPorCliente(int clienteId) throws ServiceException;
    List<Turno> buscarTurnosPorEstado(String estado) throws ServiceException;
    void actualizarTurno(Turno turno) throws ServiceException;
    void cancelarTurno(int id) throws ServiceException;
    void completarTurno(int id) throws ServiceException;
    boolean validarDisponibilidad(Turno turno) throws ServiceException;
    double calcularTotalPagadoHoy() throws ServiceException;
}
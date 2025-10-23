package com.idra.gestionpeluqueria.view.dialogs;

import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.model.Cliente;
import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.enums.EstadoTurno;
import com.idra.gestionpeluqueria.model.enums.EstadoPago;
import com.idra.gestionpeluqueria.model.enums.FormaPago;
import com.idra.gestionpeluqueria.controller.TurnoController;
import com.idra.gestionpeluqueria.controller.ClienteController;
import com.idra.gestionpeluqueria.controller.ServicioController;
import com.idra.gestionpeluqueria.exception.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TurnoDialog extends JDialog {
    private JComboBox<Cliente> comboCliente;
    private JComboBox<Servicio> comboServicio;
    private JTextField txtFecha, txtHora;
    private JTextArea txtNotas;
    private JComboBox<EstadoTurno> comboEstado;
    private JComboBox<EstadoPago> comboEstadoPago;
    private JComboBox<FormaPago> comboFormaPago;
    private JTextField txtMontoPagado;
    private JButton btnGuardar, btnCancelar, btnBuscarCliente;
    private TurnoController turnoController;
    private ClienteController clienteController;
    private ServicioController servicioController;
    private Turno turnoEditar;
    private boolean guardadoExitoso;

    public TurnoDialog(JFrame parent, String titulo, Turno turnoEditar) {
        super(parent, titulo, true);
        this.turnoEditar = turnoEditar;
        this.turnoController = new TurnoController();
        this.clienteController = new ClienteController();
        this.servicioController = new ServicioController();
        this.guardadoExitoso = false;
        
        initializeUI();
        cargarCombos();
        if (turnoEditar != null) {
            cargarDatosTurno();
        } else {
            // Valores por defecto para nuevo turno
            comboEstado.setSelectedItem(EstadoTurno.CONFIRMADO);
            comboEstadoPago.setSelectedItem(EstadoPago.PENDIENTE);
            txtMontoPagado.setText("0.00");
        }
    }

    private void initializeUI() {
        setSize(500, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Turno"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Cliente
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        JPanel clientePanel = new JPanel(new BorderLayout(5, 0));
        comboCliente = new JComboBox<>();
        clientePanel.add(comboCliente, BorderLayout.CENTER);
        btnBuscarCliente = new JButton("🔍");
        btnBuscarCliente.addActionListener(e -> buscarCliente());
        clientePanel.add(btnBuscarCliente, BorderLayout.EAST);
        formPanel.add(clientePanel, gbc);
        
        // Servicio
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Servicio:"), gbc);
        gbc.gridx = 1;
        comboServicio = new JComboBox<>();
        formPanel.add(comboServicio, gbc);
        
        // Fecha
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtFecha = new JTextField();
        txtFecha.setText(LocalDateTime.now().toLocalDate().toString());
        formPanel.add(txtFecha, gbc);
        
        // Hora
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Hora (HH:MM):"), gbc);
        gbc.gridx = 1;
        txtHora = new JTextField();
        txtHora.setText("10:00");
        formPanel.add(txtHora, gbc);
        
        // Estado
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        comboEstado = new JComboBox<>(EstadoTurno.values());
        formPanel.add(comboEstado, gbc);
        
        // Estado Pago
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Estado Pago:"), gbc);
        gbc.gridx = 1;
        comboEstadoPago = new JComboBox<>(EstadoPago.values());
        formPanel.add(comboEstadoPago, gbc);
        
        // Forma Pago
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Forma Pago:"), gbc);
        gbc.gridx = 1;
        comboFormaPago = new JComboBox<>(FormaPago.values());
        formPanel.add(comboFormaPago, gbc);
        
        // Monto Pagado
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Monto Pagado:"), gbc);
        gbc.gridx = 1;
        txtMontoPagado = new JTextField();
        formPanel.add(txtMontoPagado, gbc);
        
        // Notas
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(new JLabel("Notas:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        txtNotas = new JTextArea(3, 20);
        txtNotas.setLineWrap(true);
        JScrollPane scrollNotas = new JScrollPane(txtNotas);
        formPanel.add(scrollNotas, gbc);
        
        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnGuardar = new JButton("💾 Guardar");
        btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        
        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        
        btnGuardar.addActionListener(e -> guardarTurno());
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnGuardar);
        
        return buttonPanel;
    }

    private void cargarCombos() {
        try {
            // Cargar clientes
            List<Cliente> clientes = clienteController.obtenerTodosClientes();
            comboCliente.removeAllItems();
            for (Cliente cliente : clientes) {
                comboCliente.addItem(cliente);
            }
            
            // Cargar servicios activos
            List<Servicio> servicios = servicioController.obtenerServiciosActivos();
            comboServicio.removeAllItems();
            for (Servicio servicio : servicios) {
                comboServicio.addItem(servicio);
            }
            
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosTurno() {
        if (turnoEditar != null) {
            // Seleccionar cliente
            for (int i = 0; i < comboCliente.getItemCount(); i++) {
                if (comboCliente.getItemAt(i).getId() == turnoEditar.getCliente().getId()) {
                    comboCliente.setSelectedIndex(i);
                    break;
                }
            }
            
            // Seleccionar servicio
            for (int i = 0; i < comboServicio.getItemCount(); i++) {
                if (comboServicio.getItemAt(i).getId() == turnoEditar.getServicio().getId()) {
                    comboServicio.setSelectedIndex(i);
                    break;
                }
            }
            
            txtFecha.setText(turnoEditar.getFechaHora().toLocalDate().toString());
            txtHora.setText(turnoEditar.getFechaHora().toLocalTime().toString().substring(0, 5));
            txtNotas.setText(turnoEditar.getNotas() != null ? turnoEditar.getNotas() : "");
            comboEstado.setSelectedItem(turnoEditar.getEstado());
            comboEstadoPago.setSelectedItem(turnoEditar.getEstadoPago());
            comboFormaPago.setSelectedItem(turnoEditar.getFormaPago());
            txtMontoPagado.setText(String.valueOf(turnoEditar.getMontoPagado()));
        }
    }

    private void buscarCliente() {
        String nombre = JOptionPane.showInputDialog(this, "Buscar cliente por nombre:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            try {
                List<Cliente> clientes = clienteController.buscarClientesPorNombre(nombre.trim());
                if (clientes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron clientes", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Mostrar diálogo de selección
                    Cliente[] clienteArray = clientes.toArray(new Cliente[0]);
                    Cliente seleccionado = (Cliente) JOptionPane.showInputDialog(
                        this,
                        "Seleccione un cliente:",
                        "Seleccionar Cliente",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        clienteArray,
                        clienteArray[0]
                    );
                    if (seleccionado != null) {
                        comboCliente.setSelectedItem(seleccionado);
                    }
                }
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al buscar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarTurno() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            LocalDateTime fechaHora = LocalDateTime.parse(
                txtFecha.getText().trim() + "T" + txtHora.getText().trim() + ":00"
            );
            
            if (turnoEditar == null) {
                Turno nuevoTurno = new Turno(
                    (Cliente) comboCliente.getSelectedItem(),
                    (Servicio) comboServicio.getSelectedItem(),
                    fechaHora
                );
                nuevoTurno.setNotas(txtNotas.getText().trim());
                nuevoTurno.setEstado((EstadoTurno) comboEstado.getSelectedItem());
                nuevoTurno.setEstadoPago((EstadoPago) comboEstadoPago.getSelectedItem());
                nuevoTurno.setFormaPago((FormaPago) comboFormaPago.getSelectedItem());
                nuevoTurno.setMontoPagado(Double.parseDouble(txtMontoPagado.getText().trim()));
                
                // Validar disponibilidad
                if (!turnoController.validarDisponibilidad(nuevoTurno)) {
                    JOptionPane.showMessageDialog(this, 
                        "No hay disponibilidad para ese horario y servicio", 
                        "Error de Disponibilidad", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                turnoController.crearTurno(nuevoTurno);
                JOptionPane.showMessageDialog(this, "Turno creado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                turnoEditar.setCliente((Cliente) comboCliente.getSelectedItem());
                turnoEditar.setServicio((Servicio) comboServicio.getSelectedItem());
                turnoEditar.setFechaHora(fechaHora);
                turnoEditar.setNotas(txtNotas.getText().trim());
                turnoEditar.setEstado((EstadoTurno) comboEstado.getSelectedItem());
                turnoEditar.setEstadoPago((EstadoPago) comboEstadoPago.getSelectedItem());
                turnoEditar.setFormaPago((FormaPago) comboFormaPago.getSelectedItem());
                turnoEditar.setMontoPagado(Double.parseDouble(txtMontoPagado.getText().trim()));
                
                turnoController.actualizarTurno(turnoEditar);
                JOptionPane.showMessageDialog(this, "Turno actualizado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
            guardadoExitoso = true;
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (comboCliente.getSelectedItem() == null) {
            mostrarError("Debe seleccionar un cliente");
            return false;
        }
        
        if (comboServicio.getSelectedItem() == null) {
            mostrarError("Debe seleccionar un servicio");
            return false;
        }
        
        if (!txtFecha.getText().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            mostrarError("Formato de fecha inválido. Use YYYY-MM-DD");
            txtFecha.requestFocus();
            return false;
        }
        
        if (!txtHora.getText().trim().matches("\\d{2}:\\d{2}")) {
            mostrarError("Formato de hora inválido. Use HH:MM");
            txtHora.requestFocus();
            return false;
        }
        
        try {
            double monto = Double.parseDouble(txtMontoPagado.getText().trim());
            if (monto < 0) {
                mostrarError("El monto pagado no puede ser negativo");
                txtMontoPagado.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El monto pagado debe ser un número válido");
            txtMontoPagado.requestFocus();
            return false;
        }
        
        return true;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.WARNING_MESSAGE);
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
}
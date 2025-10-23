package com.idra.gestionpeluqueria.view.panels;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.enums.TipoServicio;
import com.idra.gestionpeluqueria.view.dialogs.ServicioDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ServicioPanel extends JPanel {
    private JTable tablaServicios;
    private DefaultTableModel tableModel;
    private JButton btnAgregar, btnEditar, btnEliminar, btnActivarDesactivar, btnBuscar;
    private JTextField txtBuscar;
    private JComboBox<String> comboFiltroTipo;

    public ServicioPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createHeaderPanel();
        createTablePanel();
        createToolbar();
        cargarDatosEjemplo();
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Gestión de Servicios");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));

        JLabel subtitleLabel = new JLabel("Administre los servicios y precios de la peluquería");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 100, 100));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 240, 240));
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void createToolbar() {
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbarPanel.setBackground(new Color(240, 240, 240));
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnAgregar = createToolbarButton("➕ Agregar Servicio", new Color(39, 174, 96));
        btnEditar = createToolbarButton("✏️ Editar", new Color(41, 128, 185));
        btnEliminar = createToolbarButton("🗑️ Eliminar", new Color(231, 76, 60));
        btnActivarDesactivar = createToolbarButton("⚡ Estado", new Color(243, 156, 18));

        btnAgregar.addActionListener(e -> abrirDialogoServicio(null));
        btnEditar.addActionListener(e -> editarServicioSeleccionado());
        btnEliminar.addActionListener(e -> eliminarServicioSeleccionado());
        btnActivarDesactivar.addActionListener(e -> cambiarEstadoServicio());

        // Filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterPanel.setBackground(new Color(240, 240, 240));

        JLabel lblFiltro = new JLabel("Filtrar por tipo:");
        lblFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        comboFiltroTipo = new JComboBox<>(new String[]{"Todos", "CORTE", "TINTURA", "PEINADO", "ALISADO", "MECHAS", "BARBA", "CEJAS", "TRATAMIENTO"});
        comboFiltroTipo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboFiltroTipo.addActionListener(e -> filtrarPorTipo());

        // Búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(new Color(240, 240, 240));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        txtBuscar = new JTextField(15);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        btnBuscar = createToolbarButton("🔍 Buscar", new Color(155, 89, 182));
        btnBuscar.addActionListener(e -> buscarServicios());

        searchPanel.add(lblBuscar, BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);
        searchPanel.add(btnBuscar, BorderLayout.EAST);

        filterPanel.add(lblFiltro);
        filterPanel.add(comboFiltroTipo);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(searchPanel);

        toolbarPanel.add(btnAgregar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnEditar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnEliminar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnActivarDesactivar);
        toolbarPanel.add(Box.createHorizontalStrut(30));
        toolbarPanel.add(filterPanel);

        add(toolbarPanel, BorderLayout.SOUTH);
    }

    private JButton createToolbarButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void createTablePanel() {
        String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Duración", "Tipo", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaServicios = new JTable(tableModel);
        tablaServicios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaServicios.setRowHeight(35);
        tablaServicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaServicios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaServicios.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaServicios.getTableHeader().setForeground(Color.WHITE);

        // Renderer simplificado para estado
        tablaServicios.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String estado = value.toString();
                    if (estado.equals("Activo")) {
                        c.setBackground(new Color(220, 255, 220));
                        c.setForeground(new Color(0, 128, 0));
                    } else {
                        c.setBackground(new Color(255, 220, 220));
                        c.setForeground(new Color(128, 0, 0));
                    }
                    if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                        c.setForeground(table.getSelectionForeground());
                    }
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaServicios);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatosEjemplo() {
        tableModel.setRowCount(0);
        Object[][] datosEjemplo = {
            {1, "Corte Caballero", "Corte de cabello para hombres", 25.00, 30, "CORTE", "Activo"},
            {2, "Corte Dama", "Corte de cabello para mujeres", 35.00, 45, "CORTE", "Activo"},
            {3, "Corte Niño", "Corte de cabello para niños", 20.00, 25, "CORTE", "Activo"},
            {4, "Tintura Completa", "Tintura de cabello completo", 80.00, 120, "TINTURA", "Activo"},
            {5, "Mechas", "Aplicación de mechas", 120.00, 150, "MECHAS", "Activo"}
        };
        for (Object[] fila : datosEjemplo) {
            tableModel.addRow(fila);
        }
    }

  private void abrirDialogoServicio(Object[] datosServicio) {
    Servicio servicio = null;
    String titulo = "Agregar Servicio";
    
    if (datosServicio != null) {
        servicio = new Servicio();
        servicio.setId((Integer) datosServicio[0]);
        servicio.setNombre(datosServicio[1].toString());
        servicio.setDescripcion(datosServicio[2].toString());
        servicio.setPrecio((Double) datosServicio[3]);
        servicio.setDuracionMinutos((Integer) datosServicio[4]);
        servicio.setTipoServicio(TipoServicio.valueOf(datosServicio[5].toString()));
        servicio.setActivo(datosServicio[6].toString().equals("Activo"));
        titulo = "Editar Servicio";
    }
    
    Window parentWindow = SwingUtilities.getWindowAncestor(this);
    JFrame parentFrame = null;
    if (parentWindow instanceof JFrame) {
        parentFrame = (JFrame) parentWindow;
    }
    
    ServicioDialog dialog = new ServicioDialog(parentFrame, titulo, servicio);
    dialog.setVisible(true);
    
    if (dialog.isGuardadoExitoso()) {
        actualizarTabla();
    }
}

    private void editarServicioSeleccionado() {
        int fila = tablaServicios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        abrirDialogoServicio(null);
    }

    private void eliminarServicioSeleccionado() {
        int fila = tablaServicios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.removeRow(fila);
    }

    private void cambiarEstadoServicio() {
        int fila = tablaServicios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String estado = tableModel.getValueAt(fila, 6).toString();
        tableModel.setValueAt(estado.equals("Activo") ? "Inactivo" : "Activo", fila, 6);
    }

    private void filtrarPorTipo() {
        // Implementación simple de filtro
        cargarDatosEjemplo();
    }

    private void buscarServicios() {
        // Implementación simple de búsqueda
        cargarDatosEjemplo();
    }

    public void actualizarTabla() {
        cargarDatosEjemplo();
    }
}
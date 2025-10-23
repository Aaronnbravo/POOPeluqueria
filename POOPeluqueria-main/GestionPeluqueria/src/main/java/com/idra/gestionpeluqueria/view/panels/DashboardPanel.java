package com.idra.gestionpeluqueria.view.panels;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardPanel extends JPanel {
    private JLabel lblTotalClientes, lblTotalServicios, lblTurnosHoy, lblIngresosHoy;
    private JLabel lblFechaActual;
    private JPanel statsPanel, quickActionsPanel, recentTurnosPanel;

    public DashboardPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con fecha y título
        createHeaderPanel();

        // Panel de estadísticas
        createStatsPanel();

        // Panel de acciones rápidas
        createQuickActionsPanel();

        // Panel de turnos recientes
        createRecentTurnosPanel();

        // Layout principal
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new BorderLayout(0, 20));
        topPanel.add(statsPanel, BorderLayout.CENTER);
        topPanel.add(quickActionsPanel, BorderLayout.EAST);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(recentTurnosPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Dashboard Principal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));

        lblFechaActual = new JLabel();
        lblFechaActual.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFechaActual.setForeground(new Color(100, 100, 100));
        actualizarFecha();

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(lblFechaActual, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        statsPanel.setBackground(new Color(240, 240, 240));

        // Tarjeta 1: Total Clientes
        JPanel cardClientes = createStatCard("👥 Total Clientes", "0", new Color(41, 128, 185));
        lblTotalClientes = (JLabel) ((JPanel) cardClientes.getComponent(1)).getComponent(0);

        // Tarjeta 2: Total Servicios
        JPanel cardServicios = createStatCard("✂️ Servicios Activos", "0", new Color(39, 174, 96));
        lblTotalServicios = (JLabel) ((JPanel) cardServicios.getComponent(1)).getComponent(0);

        // Tarjeta 3: Turnos Hoy
        JPanel cardTurnos = createStatCard("📅 Turnos Hoy", "0", new Color(243, 156, 18));
        lblTurnosHoy = (JLabel) ((JPanel) cardTurnos.getComponent(1)).getComponent(0);

        // Tarjeta 4: Ingresos Hoy
        JPanel cardIngresos = createStatCard("💰 Ingresos Hoy", "$0.00", new Color(231, 76, 60));
        lblIngresosHoy = (JLabel) ((JPanel) cardIngresos.getComponent(1)).getComponent(0);

        statsPanel.add(cardClientes);
        statsPanel.add(cardServicios);
        statsPanel.add(cardTurnos);
        statsPanel.add(cardIngresos);
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setBackground(Color.WHITE);
        valuePanel.add(valueLabel, BorderLayout.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);

        return card;
    }

    private void createQuickActionsPanel() {
        quickActionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        quickActionsPanel.setBackground(new Color(240, 240, 240));
        quickActionsPanel.setPreferredSize(new Dimension(250, 0));

        JButton btnNuevoCliente = createActionButton("➕ Nuevo Cliente", new Color(41, 128, 185));
        JButton btnNuevoServicio = createActionButton("🎨 Nuevo Servicio", new Color(39, 174, 96));
        JButton btnNuevoTurno = createActionButton("📋 Nuevo Turno", new Color(243, 156, 18));
        JButton btnReportes = createActionButton("📊 Ver Reportes", new Color(155, 89, 182));

        quickActionsPanel.add(btnNuevoCliente);
        quickActionsPanel.add(btnNuevoServicio);
        quickActionsPanel.add(btnNuevoTurno);
        quickActionsPanel.add(btnReportes);

        // Event listeners
        btnNuevoCliente.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad: Nuevo Cliente"));
        btnNuevoServicio.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad: Nuevo Servicio"));
        btnNuevoTurno.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad: Nuevo Turno"));
        btnReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad: Reportes"));
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
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

    private void createRecentTurnosPanel() {
        recentTurnosPanel = new JPanel(new BorderLayout());
        recentTurnosPanel.setBackground(Color.WHITE);
        recentTurnosPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("📋 Turnos de Hoy"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Tabla de turnos (por ahora vacía)
        String[] columnNames = {"Hora", "Cliente", "Servicio", "Estado", "Pago"};
        Object[][] data = {
            {"--:--", "No hay turnos", "---", "---", "---"}
        };

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setEnabled(false); // Solo lectura por ahora

        JScrollPane scrollPane = new JScrollPane(table);
        recentTurnosPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public void actualizarDatos() {
        actualizarFecha();
        // Aquí iría la lógica para actualizar las estadísticas con datos reales
        lblTotalClientes.setText("125");
        lblTotalServicios.setText("15");
        lblTurnosHoy.setText("8");
        lblIngresosHoy.setText("$320.00");
    }

    private void actualizarFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy");
        String fechaFormateada = LocalDate.now().format(formatter);
        lblFechaActual.setText(fechaFormateada);
    }
}
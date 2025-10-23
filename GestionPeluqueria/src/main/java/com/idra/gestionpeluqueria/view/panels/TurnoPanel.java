package com.idra.gestionpeluqueria.view.panels;

import com.idra.gestionpeluqueria.model.Cliente;
import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.view.dialogs.TurnoDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class TurnoPanel extends JPanel {
    private JTable tablaTurnos;
    private DefaultTableModel tableModel;
    private JButton btnNuevoTurno, btnEditar, btnCancelar, btnCompletar, btnBuscar;
    private JTextField txtBuscar;
    private JComboBox<String> comboFiltroEstado;

    public TurnoPanel() {
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

        JLabel titleLabel = new JLabel("Gestión de Turnos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));

        JLabel subtitleLabel = new JLabel("Administre los turnos y citas de la peluquería");
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

        btnNuevoTurno = createToolbarButton("➕ Nuevo Turno", new Color(39, 174, 96));
        btnEditar = createToolbarButton("✏️ Editar", new Color(41, 128, 185));
        btnCancelar = createToolbarButton("❌ Cancelar", new Color(231, 76, 60));
        btnCompletar = createToolbarButton("✅ Completar", new Color(46, 204, 113));

        btnNuevoTurno.addActionListener(e -> abrirDialogoTurno(null));
        btnEditar.addActionListener(e -> editarTurnoSeleccionado());
        btnCancelar.addActionListener(e -> cancelarTurnoSeleccionado());
        btnCompletar.addActionListener(e -> completarTurnoSeleccionado());

        // Filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterPanel.setBackground(new Color(240, 240, 240));

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        comboFiltroEstado = new JComboBox<>(new String[]{"Todos", "CONFIRMADO", "COMPLETADO", "CANCELADO", "AUSENTE"});
        comboFiltroEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboFiltroEstado.addActionListener(e -> filtrarPorEstado());

        // Búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(new Color(240, 240, 240));

        JLabel lblBuscar = new JLabel("Buscar cliente:");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        txtBuscar = new JTextField(15);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        btnBuscar = createToolbarButton("🔍 Buscar", new Color(155, 89, 182));
        btnBuscar.addActionListener(e -> buscarTurnos());

        searchPanel.add(lblBuscar, BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);
        searchPanel.add(btnBuscar, BorderLayout.EAST);

        filterPanel.add(lblEstado);
        filterPanel.add(comboFiltroEstado);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(searchPanel);

        toolbarPanel.add(btnNuevoTurno);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnEditar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnCancelar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnCompletar);
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
        String[] columnNames = {"ID", "Fecha/Hora", "Cliente", "Servicio", "Precio", "Estado", "Pago"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTurnos = new JTable(tableModel);
        tablaTurnos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaTurnos.setRowHeight(35);
        tablaTurnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTurnos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaTurnos.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaTurnos.getTableHeader().setForeground(Color.WHITE);

        // Renderer simplificado para estado
        tablaTurnos.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String estado = value.toString();
                    switch (estado) {
                        case "CONFIRMADO":
                            c.setBackground(new Color(173, 216, 230));
                            c.setForeground(new Color(0, 0, 139));
                            break;
                        case "COMPLETADO":
                            c.setBackground(new Color(144, 238, 144));
                            c.setForeground(new Color(0, 100, 0));
                            break;
                        case "CANCELADO":
                            c.setBackground(new Color(255, 182, 193));
                            c.setForeground(new Color(139, 0, 0));
                            break;
                        default:
                            c.setBackground(table.getBackground());
                            c.setForeground(table.getForeground());
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

        JScrollPane scrollPane = new JScrollPane(tablaTurnos);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatosEjemplo() {
        tableModel.setRowCount(0);
        Object[][] datosEjemplo = {
            {1, "2024-01-25 10:00", "María González", "Corte Dama", 35.00, "CONFIRMADO", "PENDIENTE"},
            {2, "2024-01-25 11:00", "Carlos López", "Corte Caballero", 25.00, "COMPLETADO", "PAGADO"},
            {3, "2024-01-25 14:00", "Ana Martínez", "Mechas", 120.00, "CONFIRMADO", "PENDIENTE"}
        };
        for (Object[] fila : datosEjemplo) {
            tableModel.addRow(fila);
        }
    }

   private void abrirDialogoTurno(Object[] datosTurno) {
    Turno turno = null;
    String titulo = "Nuevo Turno";
    
    if (datosTurno != null) {
        // Crear objetos necesarios para el turno
        Cliente cliente = new Cliente();
        cliente.setId((Integer) datosTurno[0]); // Asumiendo que el ID está en la primera columna
        
        Servicio servicio = new Servicio();
        servicio.setId((Integer) datosTurno[1]); // Asumiendo que el ID del servicio está en alguna columna
        
        turno = new Turno(cliente, servicio, LocalDateTime.now());
        turno.setId((Integer) datosTurno[0]);
        // ... cargar otros datos si es necesario
        titulo = "Editar Turno";
    }
    
    Window parentWindow = SwingUtilities.getWindowAncestor(this);
    JFrame parentFrame = null;
    if (parentWindow instanceof JFrame) {
        parentFrame = (JFrame) parentWindow;
    }
    
    TurnoDialog dialog = new TurnoDialog(parentFrame, titulo, turno);
    dialog.setVisible(true);
    
    if (dialog.isGuardadoExitoso()) {
        actualizarTabla();
    }
}
    private void editarTurnoSeleccionado() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        abrirDialogoTurno(null);
    }

    private void cancelarTurnoSeleccionado() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.setValueAt("CANCELADO", fila, 5);
    }

    private void completarTurnoSeleccionado() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.setValueAt("COMPLETADO", fila, 5);
        tableModel.setValueAt("PAGADO", fila, 6);
    }

    private void filtrarPorEstado() {
        cargarDatosEjemplo();
    }

    private void buscarTurnos() {
        cargarDatosEjemplo();
    }

    public void actualizarTabla() {
        cargarDatosEjemplo();
    }
}
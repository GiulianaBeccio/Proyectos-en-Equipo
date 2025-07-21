package Ventanas;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import TablaRAF.Turno;
import gestor.GestorPacientes;
import gestor.GestorTurnos;
import gestor.Horario;
import gestor.Paciente;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Toolkit;

public class ModifTurno extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNombPac;
    private JTextField txtDNI;
    private Choice chServicio;
    private Choice chObraSocial;
    private HorarioTablaM modeloHorario;
    private JTable tablaHorario;
    private static int dniPaciente;
    private static int idTurno;

    public ModifTurno(GestorPacientes gestor, GestorTurnos gestorT, int dniPaciente, int idTurno) throws Exception {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milir\\OneDrive\\Escritorio\\Universidad\\Java\\Parcial2\\src\\893857.png"));
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 610, 430);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // ********* interfaz *********
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(10, 362, 86, 23);
        contentPane.add(btnVolver);
        
        JLabel lblNombre = new JLabel("Nombre Paciente");
        lblNombre.setBounds(19, 14, 110, 14);
        contentPane.add(lblNombre);

        txtNombPac = new JTextField();
        txtNombPac.setEditable(false);
        txtNombPac.setBounds(148, 11, 150, 20);
        contentPane.add(txtNombPac);
        txtNombPac.setColumns(10);

        JLabel lblDni = new JLabel("DNI Paciente");
        lblDni.setBounds(19, 44, 110, 14);
        contentPane.add(lblDni);

        txtDNI = new JTextField(String.valueOf(dniPaciente));
        txtDNI.setEditable(false);
        txtDNI.setBounds(148, 41, 150, 20);
        contentPane.add(txtDNI);
        txtDNI.setColumns(10);

       
        JLabel lblServicio = new JLabel("Servicio");
        lblServicio.setBounds(318, 14, 90, 14);
        contentPane.add(lblServicio);

        chServicio = new Choice();
        chServicio.setBounds(419, 14, 120, 20);
        chServicio.add("Consulta");
        chServicio.add("Extraccion");
        chServicio.add("Limpieza");
        
        contentPane.add(chServicio);

       
        JLabel lblObraSocial = new JLabel("Obra Social");
        lblObraSocial.setBounds(318, 41, 90, 14);
        contentPane.add(lblObraSocial);

        chObraSocial = new Choice();
        chObraSocial.setBounds(419, 39, 120, 20);
        chObraSocial.add("OSDE");
        chObraSocial.add("Swiss Medical");
        chObraSocial.add("PAMI");
        chObraSocial.add("Sin Obra Social");
        chObraSocial.setEnabled(false);
        contentPane.add(chObraSocial);
        EstilosComponentes.estiloChoice(chServicio);
      
        JLabel lblDiaYHora = new JLabel("Día y Hora de servicio");
        lblDiaYHora.setBounds(19, 70, 140, 20);
        contentPane.add(lblDiaYHora);

        
        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(19, 100, 540, 241);
        contentPane.add(scroll);

       
        Horario[][] horarios = new Horario[8][5];
        int idBase = 11;
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 5; col++) {
                horarios[fila][col] = new Horario(idBase + (col * 8) + fila, true);
            }
        }

        try {
            for (Turno t : gestorT.obtenerTodos()) {
                if (t.getActivo() == 'V') {
                    int id = t.getIdTurno();
                    for (int fila = 0; fila < 8; fila++) {
                        for (int col = 0; col < 5; col++) {
                            if (horarios[fila][col].getIdTurno() == id) {
                                horarios[fila][col].setDisp(false);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        modeloHorario = new HorarioTablaM(horarios);
        tablaHorario = new JTable(modeloHorario);
    
        tablaHorario.setShowGrid(true); 
        tablaHorario.setGridColor(Color.GRAY); 

        for (int i = 0; i < tablaHorario.getColumnCount(); i++) {
            tablaHorario.getColumnModel().getColumn(i).setCellRenderer(new ColorTablaHorario(modeloHorario));
        }

        tablaHorario.setRowHeight(25);
        scroll.setViewportView(tablaHorario);

 
        Paciente paciente = gestor.buscarPacientePorDNI(dniPaciente);
        if (paciente != null) {
            txtNombPac.setText(paciente.getNombre());

            String obra = paciente.getObraSocial().trim().toLowerCase();
            for (int i = 0; i < chObraSocial.getItemCount(); i++) {
                if (chObraSocial.getItem(i).toLowerCase().equals(obra)) {
                    chObraSocial.select(i);
                    break;
                }
            }
        }


        Turno turnoSeleccionado = gestorT.buscarTurnoPorId(idTurno);
        if (turnoSeleccionado != null) {
            String servicio = turnoSeleccionado.getServicio();
            boolean existe = false;
            for (int i = 0; i < chServicio.getItemCount(); i++) {
                if (chServicio.getItem(i).equalsIgnoreCase(servicio)) {
                    chServicio.select(i);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                chServicio.add(servicio);
                chServicio.select(servicio);
            }

     
            int idHorario = turnoSeleccionado.getIdTurno();
            outer:
            for (int fila = 0; fila < horarios.length; fila++) {
                for (int col = 0; col < horarios[0].length; col++) {
                    if (horarios[fila][col].getIdTurno() == idHorario) {
                        modeloHorario.seleccionarTurno(fila, col);
                        break outer; //outer es para salir de los 2 bucles
                    }
                }
            }
        }


        tablaHorario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaHorario.rowAtPoint(evt.getPoint());
                int col = tablaHorario.columnAtPoint(evt.getPoint());

            
                if (fila >= 0 && col > 0 && col < tablaHorario.getColumnCount()) {
                    Horario h = modeloHorario.getHorario(fila, col - 1);
                    if (h.isDisp()) {
                        modeloHorario.seleccionarTurno(fila, col - 1);
                    } else {
                        modeloHorario.seleccionarTurno(-1, -1);
                    }
                    tablaHorario.repaint();
                }
            }
        });

    
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(106, 362, 110, 23);
        contentPane.add(btnGuardar);
        
        // ******* acciones ***********
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = modeloHorario.getFilaSeleccionada();
                int colSeleccionada = modeloHorario.getColumnaSeleccionada();

                if (filaSeleccionada == -1 || colSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un horario disponible para modificar el turno.");
                    return;
                }

                Horario horarioSeleccionado = (Horario) tablaHorario.getValueAt(filaSeleccionada, colSeleccionada + 1);
                int nuevoIdTurno = horarioSeleccionado.getIdTurno();

                String servicioSeleccionado = "";
                if (chServicio.getSelectedIndex() >= 0) {
                    servicioSeleccionado = chServicio.getItem(chServicio.getSelectedIndex());
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un servicio.");
                    return;
                }

                try {
                    Turno turnoOriginal = gestorT.buscarTurnoPorId(idTurno);
                    if (turnoOriginal != null) {
                        turnoOriginal.setActivo('X');
                        gestorT.actualizarTurno(turnoOriginal);
                        gestorT.agregarTurno(turnoOriginal.getDniPaciente(), servicioSeleccionado, turnoOriginal.getCosto(), nuevoIdTurno);
                        horarioSeleccionado.setDisp(false);
                        modeloHorario.fireTableCellUpdated(filaSeleccionada, colSeleccionada + 1);
                        JOptionPane.showMessageDialog(null, "Turno modificado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el turno a modificar.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar el turno.");
                }

                MenuSecretaria menu = new MenuSecretaria(gestor, gestorT);
				menu.setLocationRelativeTo(null); 
				menu.setVisible(true);
                dispose();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	MenuSecretaria menu = new MenuSecretaria(gestor, gestorT);
				menu.setLocationRelativeTo(null); 
				menu.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
    	try {
    		UIManager.put("Table.showGrid", true);
			UIManager.put("Table.gridColor", Color.GRAY);
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
			UIManager.put("TextArea.background", new Color(0,0,0,0)); 
			FlatDarkLaf.setup();
	    } catch (Exception ex) {
	        System.err.println("Fallo al cargar FlatLaf");
	    }

        EventQueue.invokeLater(() -> {
            try {
                GestorPacientes gestor = new GestorPacientes("pacientes.dat");
                GestorTurnos gestorT = new GestorTurnos("turnos.dat");
                ModifTurno frame = new ModifTurno(gestor, gestorT, dniPaciente, idTurno);
            	frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}


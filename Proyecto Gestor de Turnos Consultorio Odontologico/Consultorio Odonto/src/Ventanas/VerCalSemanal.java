package Ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import TablaRAF.Turno;
import gestor.GestorPacientes;
import gestor.GestorTurnos;
import gestor.Horario;

import javax.swing.JButton;
import java.awt.Toolkit;

public class VerCalSemanal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static GestorPacientes gestor;
	private static GestorTurnos gestorT;

	public VerCalSemanal(GestorPacientes gestor, GestorTurnos gestorT) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milir\\OneDrive\\Escritorio\\Universidad\\Java\\Parcial2\\src\\893857.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// ********* interfaz  **********
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(206, 314, 79, 23);
		contentPane.add(btnVolver);
		
		Horario[][] horarios = new Horario[8][5];
		int turno = 11;
			for (int col = 0; col < 5; col++) {
				    for (int fila = 0; fila < 8; fila++) {
				        horarios[fila][col] = new Horario(turno++, true);
				    }
				}

				try {
				    ArrayList<Turno> turnos = gestorT.obtenerTodos();
				    for (Turno t : turnos) {
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
		HorarioTablaM modelo = new HorarioTablaM(horarios);
		JTable TablaHorario = new JTable(modelo);
		
		TablaHorario.setShowGrid(true);
		TablaHorario.setGridColor(Color.GRAY); 

		for (int i = 0; i < TablaHorario.getColumnCount(); i++) {
			TablaHorario.getColumnModel().getColumn(i).setCellRenderer(new ColorTablaHorario(modelo));
		}

		TablaHorario.setRowHeight(25); // Altura de cada fila
		JScrollPane scroll = new JScrollPane(TablaHorario);
		scroll.setBounds(36, 51, 429, 239); // Ajustar según diseño
		contentPane.add(scroll);
			
		JLabel lblDiaYHora = new JLabel("Dia y Hora de servicio");
		lblDiaYHora.setBounds(26, 11, 192, 29);
		contentPane.add(lblDiaYHora);
		
			// ******** acciones ******
		
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Bienvenida bienvenida = new Bienvenida();
					bienvenida.setLocationRelativeTo(null);
					bienvenida.setVisible(true);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // muestra la otra ventana
		        dispose();
				
			}
			
		});
		
		TablaHorario.addMouseListener(new java.awt.event.MouseAdapter() {
			 String nombrePaciente;
             String servicio ;
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int fila = TablaHorario.rowAtPoint(evt.getPoint());
		        int col = TablaHorario.columnAtPoint(evt.getPoint());

		        if (fila >= 0 && col > 0 && col < TablaHorario.getColumnCount()) {
		            Horario h = (Horario) TablaHorario.getValueAt(fila, col);

		            if (!h.isDisp()) {  
		                String[] dias = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
		                String[] horas = { "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30" };

		                int filaHorario = fila;
		                int colHorario = col - 1;

		                String dia = (colHorario >= 0 && colHorario < dias.length) ? dias[colHorario] : "Día desconocido";
		                String hora = (filaHorario >= 0 && filaHorario < horas.length) ? horas[filaHorario] : "Hora desconocida";

		                try {
		                    Turno turno = gestorT.buscarTurnoPorId(h.getIdTurno());
		                    if (turno != null) {
		                        double costo = 0;
		                        var paciente = gestor.buscarPacientePorDNI(turno.getDniPaciente());
		                        if (paciente != null) {
		                            nombrePaciente = paciente.getNombre();
		                            servicio= turno.getServicio();
		                        }
		                        costo = turno.getCosto();

		                        String mensaje = "Turno ocupado\n" +
		                                         "Día: " + dia + "\n" +
		                                         "Hora: " + hora + "\n" +
		                                         "Servicio: " + servicio + "\n" +
		                                         "Paciente: " + nombrePaciente + "\n" +
		                                         "Costo: $" + costo;

		                        JOptionPane.showMessageDialog(null, mensaje, "Información del Turno", JOptionPane.INFORMATION_MESSAGE);
		                    } else {
		                        JOptionPane.showMessageDialog(null, "No se encontró información del turno.", "Error", JOptionPane.ERROR_MESSAGE);
		                    }
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                    JOptionPane.showMessageDialog(null, "Error al obtener información del turno.", "Error", JOptionPane.ERROR_MESSAGE);
		                }

		            }
		        }
		    }
		});


}
	public static void main(String[] args) {
		try {
			UIManager.put("Table.showGrid", true);
			UIManager.put("Table.gridColor", Color.GRAY);
			FlatDarkLaf.setup(); 
	    } catch (Exception ex) {
	        System.err.println("Fallo al cargar FlatLaf");
	    }

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerCalSemanal frame = new VerCalSemanal(gestor, gestorT);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	}

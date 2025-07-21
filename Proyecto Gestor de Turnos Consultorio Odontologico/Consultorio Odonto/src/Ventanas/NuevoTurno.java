package Ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import TablaRAF.Turno;
import gestor.GestorPacientes;
import gestor.GestorTurnos;
import gestor.Horario;
import gestor.Paciente;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

import java.awt.Choice;
import java.awt.Color;

import javax.swing.JTable;
import java.awt.Toolkit;

public class NuevoTurno extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombPac;
	private JTextField txtDNIPac;
	private static  GestorTurnos gestorT;
	private static int dniPaciente;

	
	public NuevoTurno(GestorPacientes gestor, int dni, GestorTurnos gestorT) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milir\\OneDrive\\Escritorio\\Universidad\\Java\\Parcial2\\src\\893857.png"));
	

		dniPaciente =  dni;
	
		 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// ********** interfaz **********
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 276, 96, 23);
		contentPane.add(btnVolver);
		
		JLabel lblNewLabel = new JLabel("Nombre Paciente");
		lblNewLabel.setBounds(29, 30, 110, 14);
		contentPane.add(lblNewLabel);

		txtNombPac = new JTextField();
		txtNombPac.setEditable(false);
		txtNombPac.setBounds(158, 27, 132, 20);
		contentPane.add(txtNombPac);
		txtNombPac.setColumns(10);
		
		txtDNIPac = new JTextField(String.valueOf(dni));
		txtDNIPac.setColumns(10);
		txtDNIPac.setEditable(false);
		txtDNIPac.setBounds(158, 57, 132, 20);
		
		contentPane.add(txtDNIPac);
		
		JLabel lblDniPaciente = new JLabel("DNI Paciente");
		lblDniPaciente.setBounds(29, 60, 110, 14);
		contentPane.add(lblDniPaciente);
		
		JLabel lblServicio = new JLabel("Servicio");
		lblServicio.setBounds(300, 32, 110, 14);
		contentPane.add(lblServicio);
		
		Choice chServicio = new Choice();
		chServicio.setBounds(429, 30, 96, 16);
		chServicio.add("");
		chServicio.add("Consulta");
		chServicio.add("Extraccion");
		chServicio.add("Limpieza");
		contentPane.add(chServicio);
		
		JLabel lblObraSocial = new JLabel("Obra Social");
		lblObraSocial.setBounds(300, 57, 110, 14);
		contentPane.add(lblObraSocial);
		
		Choice chObraSocial = new Choice();
		
		chObraSocial.setBounds(429, 55, 96, 16);
		chObraSocial.add("");
		chObraSocial.add("OSDE");
		chObraSocial.add("Swiss Medical");
		chObraSocial.add("PAMI");
		chObraSocial.add("Sin Obra Social");
		chObraSocial.setEnabled(false);
		
		contentPane.add(chObraSocial);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(10, 312, 96, 23);
		contentPane.add(btnGuardar);
		//estilos
		EstilosComponentes.estiloChoice(chServicio);
		EstilosComponentes.estiloLabel(lblNewLabel);
		EstilosComponentes.estiloLabel(lblObraSocial);
		EstilosComponentes.estiloLabel(lblServicio);
		EstilosComponentes.estiloLabel(lblDniPaciente);
		EstilosComponentes.estiloCampoTexto(txtDNIPac);
		EstilosComponentes.estiloCampoTexto(txtNombPac);
		
		
		
		// ******* acciones ******
	
		
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuSecretaria menu = new MenuSecretaria(gestor, gestorT);
				menu.setLocationRelativeTo(null); 
				menu.setVisible(true);
		        dispose();
				
			}
			
		});
		
		txtNombPac.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (Character.isDigit(c)) {
		            e.consume(); 
		        }
		    }
		});
	

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
	

		for (int i = 0; i < TablaHorario.getColumnCount(); i++) {
		    TablaHorario.getColumnModel().getColumn(i).setCellRenderer(new ColorTablaHorario(modelo));
		}
		TablaHorario.setRowHeight(25);
		JScrollPane scroll = new JScrollPane(TablaHorario);
		scroll.setBounds(133, 98, 427, 237); 
		contentPane.add(scroll);
		
		
		
		JLabel lblDiaYHora = new JLabel("Dia y Hora de servicio");
		lblDiaYHora.setBounds(10, 105, 113, 29);
		contentPane.add(lblDiaYHora);
		
		// ********* acciones *******
		
		TablaHorario.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int fila = TablaHorario.rowAtPoint(evt.getPoint());
		        int col = TablaHorario.columnAtPoint(evt.getPoint());
		        if (fila >= 0 && col > 0 && col < TablaHorario.getColumnCount()) {
		            Horario h = (Horario) TablaHorario.getValueAt(fila, col);
		            if (h.isDisp()) {
		                modelo.seleccionarTurno(fila, col - 1);
		            }
		        }
		    }
		});


		Paciente pacienteEncontrado = gestor.buscarPacientePorDNI(dni);
	
		if (pacienteEncontrado != null) {
			//System.out.println("encontro");
			//System.out.println(" nombre: " + pacienteEncontrado.getNombre());
		    //System.out.println(" obra social: " + pacienteEncontrado.getObraSocial());

		    txtNombPac.setText(pacienteEncontrado.getNombre());
		    String obra = pacienteEncontrado.getObraSocial().trim().toLowerCase();
		    for (int i = 0; i < chObraSocial.getItemCount(); i++) {
		        if (chObraSocial.getItem(i).toLowerCase().equals(obra)) {
		            chObraSocial.select(i);
		            break;
		        }
		    }
		}

		btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtNombPac.getText().isEmpty() || txtDNIPac.getText().isEmpty() || chServicio.getSelectedItem().isEmpty() || chObraSocial.getSelectedItem().isEmpty()   ) {
					JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.","¡Atencion!", JOptionPane.WARNING_MESSAGE);
					return;
				}else {
					 long dni=Long.parseLong(txtDNIPac.getText().trim());
					
				      String obraSocial = chObraSocial.getSelectedItem().toString().trim();
				      String servicio = chServicio.getSelectedItem().toString().trim();
				   
				      int descuento = 0;
				   
				      if (obraSocial.equals("PAMI")) {
				          descuento = 40;
				      } else if (obraSocial.equals("OSDE")) {
				          descuento = 30;
				      } else if (obraSocial.equals("Swiss Medical")) {
				          descuento = 10;
				      }

				      double costoServicio = 0;
				      if (servicio.equals("Consulta")) {
				          costoServicio = 12000;
				      } else if (servicio.equals("Extraccion")) {
				          costoServicio = 40000;
				      } else if (servicio.equals("Limpieza")) {
				          costoServicio = 20000;
				      }

				      double calcDesc = (descuento * costoServicio) / 100.00;
				      double costo = costoServicio - calcDesc;

				   
				      int filaSeleccionada = modelo.getFilaSeleccionada();
				      int colSeleccionada = modelo.getColumnaSeleccionada();

				      if (filaSeleccionada == -1 || colSeleccionada == -1) {
				          JOptionPane.showMessageDialog(null, "Debe seleccionar un horario.","¡Atencion!", JOptionPane.WARNING_MESSAGE);
				          return;
				      }

				      Horario horarioSeleccionado = (Horario) TablaHorario.getValueAt(filaSeleccionada, colSeleccionada + 1);

				      int idTurno = horarioSeleccionado.getIdTurno();

				      try {
				    	JOptionPane.showMessageDialog(null, "Turno Agendado Con Exito.","¡	Genial!", JOptionPane.INFORMATION_MESSAGE);
						gestorT.agregarTurno(dni, servicio, costo, idTurno);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				      horarioSeleccionado.setDisp(false);
					modelo.fireTableCellUpdated(filaSeleccionada, colSeleccionada);
			           
			        } 
					
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
			FlatDarkLaf.setup();
	    } catch (Exception ex) {
	        System.err.println("Fallo al cargar FlatLaf");
	    }

	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                GestorPacientes gestor = new GestorPacientes("pacientes.dat"); 
	                NuevoTurno frame = new NuevoTurno(gestor, dniPaciente, gestorT);
	            	frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

}

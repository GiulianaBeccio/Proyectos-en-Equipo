package Ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import TablaRAF.Turno;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import Ventanas.EstilosComponentes;
import gestor.GestorPacientes;
import gestor.GestorTurnos;
import gestor.Horario;

import javax.swing.JButton;

public class MenuSecretaria extends JFrame {
	
	private static GestorTurnos gestorT;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public MenuSecretaria(GestorPacientes gestor, GestorTurnos gestorT) {
		
		
		// ****** interfaz *****
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milir\\OneDrive\\Escritorio\\Universidad\\Java\\Parcial2\\src\\893857.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrNuevoTurno = new JTextArea();
		txtrNuevoTurno.setBounds(115, 62, 237, 22);
		txtrNuevoTurno.setText("Nuevo Turno");
		txtrNuevoTurno.setOpaque(false);
		txtrNuevoTurno.setFocusable(false);
		contentPane.add(txtrNuevoTurno);
		
		
		JTextArea txtrqueAccionDesea = new JTextArea();
		txtrqueAccionDesea.setBounds(115, 11, 237, 29);
		txtrqueAccionDesea.setText("¿Que accion desea realizar?");
		
		
		contentPane.add(txtrqueAccionDesea);
		EstilosComponentes.estiloTextAreaEditable(txtrqueAccionDesea);
		txtrqueAccionDesea.setFocusable(false);
		txtrqueAccionDesea.setEditable(false);
		txtrqueAccionDesea.setOpaque(false);;
		
		JTextArea txtrCancelarTurno = new JTextArea();
		txtrCancelarTurno.setText("Cancelar Turno");
		txtrCancelarTurno.setOpaque(false);
		txtrCancelarTurno.setBounds(115, 119, 237, 22);
		txtrCancelarTurno.setFocusable(false);
		contentPane.add(txtrCancelarTurno);
		
		JTextArea txtrModificarTurno = new JTextArea();
		txtrModificarTurno.setText("Modificar Turno");
		txtrModificarTurno.setOpaque(false);
		txtrModificarTurno.setBounds(115, 176, 237, 22);
		txtrModificarTurno.setFocusable(false);
		contentPane.add(txtrModificarTurno);
		
		JTextArea txtrVerCalendarioSemanal = new JTextArea();
		txtrVerCalendarioSemanal.setText("Ver Calendario Semanal");
		txtrVerCalendarioSemanal.setOpaque(false);
		txtrVerCalendarioSemanal.setBounds(115, 233, 237, 22);
		txtrVerCalendarioSemanal.setFocusable(false);
		contentPane.add(txtrVerCalendarioSemanal);
		
	
	
		EstilosComponentes.estiloTextoInteractivo(txtrNuevoTurno);
		EstilosComponentes.estiloTextoInteractivo(txtrCancelarTurno);
		EstilosComponentes.estiloTextoInteractivo(txtrModificarTurno);
		EstilosComponentes.estiloTextoInteractivo(txtrVerCalendarioSemanal);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setBounds(177, 277, 89, 23);
		contentPane.add(btnAtras);
		EstilosComponentes.estiloBotonPlano(btnAtras);
		
		Horario[][] horarios = new Horario[8][5];
		int turno = 11;
		for (int col = 0; col < 5; col++) {
		    for (int fila = 0; fila < 8; fila++) {
		        horarios[fila][col] = new Horario(turno++, true);
		    }
		}

		try {
		    ArrayList<Turno> todosTurnos = gestorT.obtenerTodos();
		    for (Turno t : todosTurnos) {
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
		//imprimirMatrizHorarios(horarios);

		// ******  acciones  *********
		btnAtras.addActionListener(new ActionListener() {

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
		
		txtrVerCalendarioSemanal.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				VerCalSemanal verCalSemanal = new VerCalSemanal(gestor,gestorT);
				verCalSemanal.setLocationRelativeTo(null);
				verCalSemanal.setVisible(true);
				
		        dispose(); 
			}
		});
		txtrModificarTurno.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int dniConsultaModif = -1;
		        boolean dniValido = false;

		        while (!dniValido) {
		            String input = JOptionPane.showInputDialog(null, "Ingrese el DNI del paciente:", "¡Atencion!", JOptionPane.WARNING_MESSAGE);
		            if (input == null) {
		            	JOptionPane.showMessageDialog(null, "Operación cancelada.", "Información", JOptionPane.INFORMATION_MESSAGE);

		                break;
		            }

		            try {
		                dniConsultaModif = Integer.parseInt(input);
		                dniValido = true;

		                Integer idTurno = gestorT.seleccionarTurnoPorDni(null, dniConsultaModif, gestor, gestorT, horarios);

		                if (idTurno != null) {
		                	ModifTurno modifTurno = new ModifTurno(gestor, gestorT, dniConsultaModif, idTurno);
		                	modifTurno.setLocationRelativeTo(null);
		                	modifTurno.setVisible(true);
		                    dispose();
		                }


		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "DNI inválido o error interno.", "Error", JOptionPane.ERROR_MESSAGE);
		                ex.printStackTrace();
		            }
		        }
		    }
		});

		txtrCancelarTurno.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int dniConsultaCancelar = -1;
		        boolean dniValido = false;

		        while (!dniValido) {
		            String input = JOptionPane.showInputDialog(null, "Ingrese el DNI del paciente:","Información", JOptionPane.INFORMATION_MESSAGE);
		            if (input == null) {
		                JOptionPane.showMessageDialog(null, "Operación cancelada.", "Error", JOptionPane.ERROR_MESSAGE);
		                break;
		            }

		            try {
		                dniConsultaCancelar = Integer.parseInt(input);
		                dniValido = true;
		                Integer idTurno = gestorT.seleccionarTurnoPorDni(null, dniConsultaCancelar, gestor, gestorT, horarios);
		                if (idTurno != null) { 
		                    Turno turno = gestorT.buscarTurnoPorId(idTurno);
		                    if (turno != null) {  
		                        StringBuilder mensaje = new StringBuilder();
		                        mensaje.append("¿Está seguro que desea cancelar el siguiente turno?\n\n");
		                        mensaje.append("ID Turno: ").append(turno.getIdTurno()).append("\n");
		                        mensaje.append("Paciente DNI: ").append(turno.getDniPaciente()).append("\n");
		                        mensaje.append("Servicio: ").append(turno.getServicio()).append("\n");
		                        mensaje.append("Costo: $").append(turno.getCosto()).append("\n");

		                        int confirmar = JOptionPane.showConfirmDialog(null, mensaje.toString(), "Confirmar cancelación", JOptionPane.YES_NO_OPTION);

		                        if (confirmar == JOptionPane.YES_OPTION) {
		                        	 turno.setActivo('F');
		                             try {
		                                 gestorT.actualizarTurno(turno);
		                                 JOptionPane.showMessageDialog(null, "Turno cancelado correctamente.","Información", JOptionPane.INFORMATION_MESSAGE);
		                             } catch (Exception ex) {
		                                 ex.printStackTrace();
		                                 JOptionPane.showMessageDialog(null, "Error al cancelar el turno.","Información", JOptionPane.INFORMATION_MESSAGE);
		                             }
		                        } else {
		                           //cancelo
		                        }
		                    } else {
		                        
		                    }
		                } else {
		                    JOptionPane.showMessageDialog(null, "No se encontraron turnos activos para ese DNI.","Información", JOptionPane.INFORMATION_MESSAGE);
		                }
		            } catch (NumberFormatException e1) {
		                JOptionPane.showMessageDialog(null, "DNI inválido. Por favor, ingrese solo números.","Error", JOptionPane.ERROR_MESSAGE);
		            } catch (Exception e1) {
		                e1.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error inesperado: " + e1.getMessage());
		            }
		        }
		    }
		});

	

		txtrNuevoTurno.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				int dniConsultaNuevo = -1;
				boolean dniValido = false;

				while (!dniValido) {
					String input = JOptionPane.showInputDialog(null, "Ingrese el DNI del paciente:","Información", JOptionPane.INFORMATION_MESSAGE);
					if (input == null) {
						JOptionPane.showMessageDialog(null, "Operación cancelada.","Información", JOptionPane.INFORMATION_MESSAGE);
						break;
					}

					try {
						dniConsultaNuevo = Integer.parseInt(input);
						dniValido = true;

						if (!gestor.existeDNI(dniConsultaNuevo)) {
							JOptionPane.showMessageDialog(null, "Paciente no registrado. Por favor regístrelo primero.","Información", JOptionPane.INFORMATION_MESSAGE);
							NuevoPaciente nuevoPaciente = new NuevoPaciente(gestor, gestorT, dniConsultaNuevo);
		                	nuevoPaciente.setLocationRelativeTo(null);
		                	nuevoPaciente.setVisible(true);
							
							dispose();
						} else {
							NuevoTurno nuevoTurno = new NuevoTurno(gestor, dniConsultaNuevo, gestorT);
		                	nuevoTurno.setLocationRelativeTo(null);
		                	nuevoTurno.setVisible(true);
							dispose();
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "DNI inválido. Por favor, ingrese solo números.","Error", JOptionPane.ERROR_MESSAGE);
					}
				}
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

	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                GestorPacientes gestor = new GestorPacientes("pacientes.dat");
	                MenuSecretaria frame = new MenuSecretaria(gestor,gestorT);
	            	frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	



	}

	/*public void imprimirMatrizHorarios(Horario[][] horarios) {
	    String[] dias = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
	    String[] horas = {
	        "08:00", "08:30", "09:00", "09:30",
	        "10:00", "10:30", "11:00", "11:30"
	    };
	    
	    System.out.println("Matriz de Horarios:");
	    System.out.print("Hora/Día\t");
	    for (String dia : dias) {
	        System.out.print(dia + "\t");
	    }
	    System.out.println();

	    for (int fila = 0; fila < horarios.length; fila++) {
	        System.out.print(horas[fila] + "\t");
	        for (int col = 0; col < horarios[fila].length; col++) {
	            Horario h = horarios[fila][col];
	            System.out.print(h.getIdTurno() + (h.isDisp() ? "(disp)" : "(ocup)") + "\t");
	        }
	        System.out.println();
	    }
	}*/



	

	


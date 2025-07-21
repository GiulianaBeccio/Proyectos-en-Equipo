package Ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import gestor.GestorPacientes;
import gestor.GestorTurnos;
import gestor.Paciente;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Toolkit;

public class NuevoPaciente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombPac;
	private JTextField txtDniPac;
	private JTextField txtTelefono;
	private static int dniPaciente;

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
					GestorTurnos gestorT = new GestorTurnos("turnos.dat");
					NuevoPaciente frame = new NuevoPaciente(gestor, gestorT, dniPaciente);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public NuevoPaciente(GestorPacientes gestor, GestorTurnos gestorT, int dni) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milir\\OneDrive\\Escritorio\\Universidad\\Java\\Parcial2\\src\\893857.png"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// ***** interfaz ******
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(217, 208, 110, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(90, 208, 110, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblNewLabel = new JLabel("Nombre Paciente");
		lblNewLabel.setBounds(90, 63, 110, 14);
		contentPane.add(lblNewLabel);
		
		txtNombPac = new JTextField();
		txtNombPac.setColumns(10);
		txtNombPac.setBounds(219, 60, 96, 20);
		contentPane.add(txtNombPac);
		
		JLabel lblDniPaciente = new JLabel("DNI Paciente");
		lblDniPaciente.setBounds(90, 90, 110, 14);
		contentPane.add(lblDniPaciente);
		
		txtDniPac = new JTextField();
		txtDniPac.setColumns(10);
		txtDniPac.setBounds(219, 87, 96, 20);
		txtDniPac.setText(String.valueOf(dni));
		txtDniPac.setEditable(false);
		contentPane.add(txtDniPac);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(90, 117, 110, 14);
		contentPane.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(219, 114, 96, 20);
		contentPane.add(txtTelefono);
		
		JLabel lblObraSocial = new JLabel("Obra Social");
		lblObraSocial.setBounds(90, 144, 110, 14);
		contentPane.add(lblObraSocial);
		
		Choice chObraSocial = new Choice();
		chObraSocial.add("");
		chObraSocial.add("OSDE");
		chObraSocial.add("Swiss Medical");
		chObraSocial.add("PAMI");
		chObraSocial.add("Sin Obra Social");
		

		chObraSocial.setBounds(219, 142, 96, 16);
		contentPane.add(chObraSocial);
		// Estilos aplicados
		EstilosComponentes.estiloCampoTexto(txtNombPac);
		EstilosComponentes.estiloCampoTexto(txtDniPac);
		EstilosComponentes.estiloCampoTexto(txtTelefono);

		EstilosComponentes.estiloLabel(lblNewLabel);
		EstilosComponentes.estiloLabel(lblDniPaciente);
		EstilosComponentes.estiloLabel(lblTelefono);
		EstilosComponentes.estiloLabel(lblObraSocial);

		EstilosComponentes.estiloBotonPlano(btnAceptar);
		EstilosComponentes.estiloBotonPlano(btnCancelar);
		
		EstilosComponentes.estiloChoice(chObraSocial);

		
		// ****** Acciones *******
		
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuSecretaria menu = new MenuSecretaria(gestor, gestorT);
				menu.setLocationRelativeTo(null); 
				menu.setVisible(true);
				dispose();
				
			}
			
		});
		
		btnAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
			    if (txtNombPac.getText().isEmpty() || txtDniPac.getText().isEmpty() ||txtTelefono.getText().isEmpty() || chObraSocial.getSelectedItem().toString().isEmpty()) {
			        
			        JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.", "¡Atencion!", JOptionPane.WARNING_MESSAGE);
			        return;
			    }

			    try {
			        
			        
			        String nombre = txtNombPac.getText().trim();
			        String telefonoTexto = txtTelefono.getText().trim();
			        Long.parseLong(telefonoTexto);
			        String obraSocial = chObraSocial.getSelectedItem().toString().trim();

			        Paciente nuevo = new Paciente(dni, nombre, telefonoTexto, obraSocial);
			        gestor.registrarNuevoPaciente(nuevo);
			        
			        
			        JOptionPane.showMessageDialog(null, "Paciente registrado con éxito.", "¡Genial!", JOptionPane.INFORMATION_MESSAGE);
			       // gestor.imprimirPacientesArchivo();
			        txtNombPac.setText("");
			        
			        txtTelefono.setText("");
			        MenuSecretaria menu = new MenuSecretaria(gestor, gestorT);
					menu.setLocationRelativeTo(null); 
					menu.setVisible(true);
			        dispose();

			    } catch (NumberFormatException ex) {
			        JOptionPane.showMessageDialog(null, "El Teléfono deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
			    }
			}

			
		});
	}

	
}

package Ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.SystemColor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import com.formdev.flatlaf.FlatDarkLaf;
import Ventanas.EstilosComponentes;
import gestor.GestorPacientes;
import gestor.GestorTurnos;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Bienvenida extends JFrame {
	
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
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
					Bienvenida frame = new Bienvenida();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 
		
	}
	// 	txtrMenuPrincipal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
	
	public Bienvenida() throws Exception  {
		setResizable(false);
	
		GestorPacientes gestor = new GestorPacientes("pacientes.dat");
		GestorTurnos gestorT = new GestorTurnos("turnos.dat");

		//*** interfaz **
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milir\\OneDrive\\Escritorio\\Universidad\\Java\\Parcial2\\src\\893857.png"));
		setTitle("Consultorio");
		setBackground(SystemColor.menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrMenuPrincipal = new JTextArea();
		txtrMenuPrincipal.setBounds(165, 11, 112, 22);
		
		txtrMenuPrincipal.setBackground(new Color(0, 0, 0, 0));
		txtrMenuPrincipal.setOpaque(false);
		txtrMenuPrincipal.setBorder(null); 
		//txtrMenuPrincipal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtrMenuPrincipal.setText("¡Bienvenido!");
		txtrMenuPrincipal.setFocusable(false);
		contentPane.add(txtrMenuPrincipal);
		
		JButton btnGuardar = new JButton("Guardar ");
		btnGuardar.setBounds(152, 106, 112, 22);
		contentPane.add(btnGuardar);
		
//ImageIcon iconoOriginal = new ImageIcon("C:\\Users\\milir\\OneDrive\\Escritorio\\Universidad\\Java\\Parcial2\\src\\dientemini.png");
//		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(102, 108, Image.SCALE_SMOOTH);
		
		JButton btnSoySec = new JButton("Soy Secretaria");
		btnSoySec.setBounds(231, 55, 152, 23);
		contentPane.add(btnSoySec);
		
		JButton btnSoyMed = new JButton("Soy Medico");
		btnSoyMed.setBounds(33, 55, 152, 23);
		contentPane.add(btnSoyMed);
		
		
		//**** Acciones ******
		btnSoySec.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuSecretaria menu = new MenuSecretaria(gestor, gestorT);
				menu.setLocationRelativeTo(null); 
				menu.setVisible(true);
			    dispose(); 
			}
			
		});
		btnSoyMed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VerCalSemanal cal = new VerCalSemanal(gestor, gestorT);
				cal.setLocationRelativeTo(null);
				cal.setVisible(true);
			    dispose(); 
			}
			
		});
		btnGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gestor.cerrar();
				System.exit(0);
				
			}
			
		});
		
		
		
		
		
		
		
		
		
		
		


	}
}

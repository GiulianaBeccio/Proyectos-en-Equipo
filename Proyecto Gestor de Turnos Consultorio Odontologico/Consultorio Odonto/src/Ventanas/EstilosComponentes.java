package Ventanas;

import java.awt.*;
import javax.swing.*;

public class EstilosComponentes {

    //Botón
    public static void estiloBotonPlano(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(100, 100, 100));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(130, 130, 130));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 100, 100));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(80, 80, 80));
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(130, 130, 130));
            }
        });
    }

    // Texto tipo botón
    public static void estiloTextoInteractivo(JTextArea txt) {
	    txt.setForeground(Color.WHITE);
	    txt.setFont(new Font("Segoe UI", Font.BOLD, 16));
	    txt.setOpaque(false);
	    txt.setEditable(false);
	    txt.setHighlighter(null);
	    txt.setFocusable(false);
	    txt.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

	    txt.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseEntered(java.awt.event.MouseEvent evt) {
	            txt.setForeground(new Color(123, 147, 173)); 
	        }

	        public void mouseExited(java.awt.event.MouseEvent evt) {
	            txt.setForeground(new Color(215, 222, 229 )); 
	        }
	    });
	}

    //  Label 
    public static void estiloLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
    }

    

    //  Panel con fondo y padding
    public static void estiloPanelFondo(JPanel panel) {
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    //  Área de texto con padding y color
    public static void estiloTextAreaEditable(JTextArea area) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(Color.WHITE);
        area.setBackground(new Color(60, 60, 60));
        area.setCaretColor(Color.WHITE);
        area.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
    }
    // jtext field
    public static void estiloCampoTexto(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setForeground(Color.WHITE);
        campo.setBackground(new Color(60, 60, 60));
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
            
        ));
        campo.setOpaque(true);
    }

    // combobox
    @SuppressWarnings("rawtypes")
    public static void estiloComboBox(JComboBox combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(new Color(60, 60, 60));
        combo.setForeground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        combo.setOpaque(true);
    }
    // choice
    public static void estiloChoice(Choice choice) {
     
        choice.setBackground(new Color(60, 60, 60));
        choice.setForeground(Color.WHITE);

        
        choice.setFont(new Font("Segoe UI", Font.PLAIN, 14));

      
    }

}

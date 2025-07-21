package Ventanas;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

import gestor.Horario;

import java.awt.*;

public class ColorTablaHorario extends JLabel implements TableCellRenderer {
    private static final long serialVersionUID = 1L;
    private HorarioTablaM modelo;

   
    private static final Color COLOR_DISPONIBLE = new Color(46, 125, 50);      
    private static final Color COLOR_OCUPADO = new Color(198, 40, 40);         
    private static final Color COLOR_SELECCIONADO = new Color(76, 106, 135);   
    private static final Color COLOR_ENCABEZADO = new Color(66, 66, 66);      
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(189, 189, 189);         

    public ColorTablaHorario(HorarioTablaM modelo) {
        this.modelo = modelo;
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor,
            boolean seleccionado, boolean focus, int fila, int columna) {

        setBorder(new LineBorder(COLOR_BORDE, 1));

        if (valor instanceof Horario) {
            Horario h = (Horario) valor;
            setText("");

            if (columna > 0 && modelo.esSeleccionado(fila, columna - 1)) {
                setBackground(COLOR_SELECCIONADO);
            } else {
                setBackground(h.isDisp() ? COLOR_DISPONIBLE : COLOR_OCUPADO);
            }

            setForeground(COLOR_TEXTO);
        } else {
            setText(valor != null ? valor.toString() : "");
            setBackground(COLOR_ENCABEZADO);
            setForeground(COLOR_TEXTO);
        }

        return this;
    }
}




package Ventanas;

import javax.swing.table.AbstractTableModel;

import gestor.Horario;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HorarioTablaM extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private Horario[][] horarios;
    private String[] dias = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;

    public HorarioTablaM(Horario[][] horarios) {
        this.horarios = horarios;
    }

    @Override
    public int getRowCount() {
        return horarios.length;
    }

    @Override
    public int getColumnCount() {
        return dias.length; 
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        if (columna == 0) {
            Calendar calendario = Calendar.getInstance();
            calendario.set(Calendar.HOUR_OF_DAY, 8);
            calendario.set(Calendar.MINUTE, 0);
            calendario.add(Calendar.MINUTE, 30 * fila);
            SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm a");
            return formatoHora.format(calendario.getTime());
        } else {
            return horarios[fila][columna - 1]; 
        }
    }

    @Override
    public String getColumnName(int columna) {
        return dias[columna];
    }

    @Override
    public Class<?> getColumnClass(int InColumna) { //in de indice
        if (InColumna == 0)
            return String.class;
        return Horario.class;
    }

    public void toggleDisponibilidad(int fila, int col) {
        if (col > 0 && col <= horarios[0].length) {
            Horario h = horarios[fila][col - 1];
            h.setDisp(!h.isDisp());
            fireTableCellUpdated(fila, col);
        }
    }
    

    public void seleccionarTurno(int fila, int columna) {
        this.filaSeleccionada = fila;
        this.columnaSeleccionada = columna;
        fireTableDataChanged(); 
    }

    public int getFilaSeleccionada() {
        return filaSeleccionada;
    }

    public int getColumnaSeleccionada() {
        return columnaSeleccionada;
    }
    public Horario getHorario(int fila, int col) {
        return horarios[fila][col];
    }

   
    public boolean esSeleccionado(int fila, int columna) {
        return fila == filaSeleccionada && columna == columnaSeleccionada;
    }
}



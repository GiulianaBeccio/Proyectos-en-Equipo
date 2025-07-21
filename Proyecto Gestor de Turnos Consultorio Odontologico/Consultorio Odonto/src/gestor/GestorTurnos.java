package gestor;


import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import TablaRAF.*;
public class GestorTurnos {
    private TablaRAF archivoTurnos; 
    private int siguienteIdTurno = 1;

    public GestorTurnos(String archivoPath) throws Exception  {
        archivoTurnos = new TablaRAF(archivoPath);
        cargarUltimoId();
    }
    public TablaRAF getArchivoTurnos() {
        return archivoTurnos;
    }
  
    private void cargarUltimoId() throws Exception {
        Turno temp = new Turno(0, "", 0.0, 0);
        long bytesTotales = archivoTurnos.raf.length();
        int cant = (int)(bytesTotales / temp.largo());

        if (cant > 0) {
            Turno ultimo = new Turno(0, "", 0.0, 0);
            archivoTurnos.get(cant, ultimo); 
            setSiguienteIdTurno(ultimo.getIdTurno() + 1);
        }
    }
    public Turno buscarTurnoPorId(int idBuscado) throws Exception {
        Turno temp = new Turno(0, "", 0.0, 0);
        long tam = archivoTurnos.raf.length();
        int cant = (int)(tam / temp.largo());

        for (int i = 1; i <= cant; i++) {
            Turno t = new Turno(0, "", 0.0, 0);
            archivoTurnos.get(i, t);
            if (t.getActivo() == 'V' && t.getIdTurno() == idBuscado) {
                return t;
            }
        }
        return null;
    }


    public void agregarTurno(long dniPaciente, String servicio, double costo, int idTurno) throws Exception {
        Turno t = new Turno(idTurno, servicio, costo, (int) dniPaciente);
        t.setActivo('V');
        archivoTurnos.agregar(t);
       // System.out.println("Turno agregado: " + t);
    }

    public void actualizarTurno(Turno turno) throws Exception {
        long tam = archivoTurnos.raf.length();
        int cant = (int)(tam / turno.largo());

        for (int i = 1; i <= cant; i++) {
            Turno t = new Turno();
            archivoTurnos.get(i, t);
            if (t.getActivo() == 'V' && t.getIdTurno() == turno.getIdTurno()) {
                archivoTurnos.actualizar(i, turno); 
                return;
            }
        }
        throw new Exception("Turno no encontrado para actualizar.");
    }


    public ArrayList<Turno> obtenerTodos() throws Exception {
        ArrayList<Turno> turnos = new ArrayList<>();
        Turno temp = new Turno();
        int cant = (int)(archivoTurnos.raf.length() / temp.largo());

        for (int i = 1; i <= cant; i++) {
            try {
                Turno t = new Turno();
                archivoTurnos.get(i, t);
                if (t.getActivo() == 'V') {
                    turnos.add(t);
                }
            } catch (Exception e) {
              
                break;
            }
        }

        return turnos;
    }



    public Integer seleccionarTurnoPorDni(JFrame parent, int dniPaciente, GestorPacientes gestorPacientes, GestorTurnos gestorTurnos, Horario[][] horarios) throws Exception {
        Turno turnoAux = new Turno(0, "", 0.0, 0);
        long tamanioArchivo = gestorTurnos.getArchivoTurnos().raf.length();
        int cantRegistros = (int) (tamanioArchivo / turnoAux.largo());
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        ArrayList<Integer> ids = new ArrayList<>();
        Paciente paciente = gestorPacientes.buscarPacientePorDNI(dniPaciente);
        String nombre = (paciente != null) ? paciente.getNombre() : "Desconocido";
        String[] dias = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
        String[] horas = {
            "08:00", "08:30", "09:00", "09:30",
            "10:00", "10:30", "11:00", "11:30"
        };

        for (int i = 1; i <= cantRegistros; i++) {
            Turno t = new Turno(0, "", 0.0, 0);
            gestorTurnos.getArchivoTurnos().get(i, t);

            if (t.getActivo() == 'V' && t.getDniPaciente() == dniPaciente) {
                int idTurno = t.getIdTurno();
                int idBase = 11;
                int idRelativo = idTurno - idBase;

                int fila = idRelativo % 8;     // hora
                int col = idRelativo / 8;      // día

                String dia = (col >= 0 && col < dias.length) ? dias[col] : "Día inválido";
                String hora = (fila >= 0 && fila < horas.length) ? horas[fila] : "Hora inválida";
                String diaHora = "Día: " + dia + ", Hora: " + hora;
                String descripcion = diaHora + " | Servicio: " + t.getServicio() + " | Paciente: " + nombre;
                modeloLista.addElement(descripcion);
                ids.add(idTurno);
            }
        }


        if (modeloLista.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "No hay turnos activos para este paciente.");
            return null;
        }

        JList<String> lista = new JList<>(modeloLista);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 150));

        int opcion = JOptionPane.showConfirmDialog(parent, scrollPane, "Seleccione un turno para modificar",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION && lista.getSelectedIndex() != -1) {
            return ids.get(lista.getSelectedIndex());
        }

        return null;
    }


    public boolean modificarTurno(int idTurno, String nuevoServicio, double nuevoCosto) throws Exception {
        Turno auxTurno = new Turno(0, "", 0.0, 0);
        int cant = (int)(archivoTurnos.raf.length() / auxTurno.largo());

        for (int i = 1; i <= cant; i++) {
            Turno t = new Turno(0, "", 0.0, 0);
            archivoTurnos.get(i, t);
            if (t.getActivo() == 'V' && t.getIdTurno() == idTurno) {
                t.setServicio(nuevoServicio);
                t.setCosto(nuevoCosto);
                archivoTurnos.actualizar(i, t);
               // System.out.println("Turno modificado: " + t);
                return true;
            }
        }
        return false;
    }
	public int getSiguienteIdTurno() {
		return siguienteIdTurno;
	}
	public void setSiguienteIdTurno(int siguienteIdTurno) {
		this.siguienteIdTurno = siguienteIdTurno;
	}

}

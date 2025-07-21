package TablaRAF;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Turno implements Registro {
    private int idTurno;
    private String servicio;
    private double costo;
    private int dniPaciente;
    private char activo = 'V'; // V=vigente, F=eliminado

    public Turno() {}

    public Turno(int idTurno, String servicio, double costo, int dniPaciente) {
        this.idTurno = idTurno;
        this.servicio = servicio;
        this.costo = costo;
        this.dniPaciente = dniPaciente;
        this.activo = 'V';
    }
    public Turno(Turno otro) {
        this.idTurno = otro.idTurno;
        this.servicio = otro.servicio;
        this.costo = otro.costo;
        this.dniPaciente = otro.dniPaciente;
        this.activo = otro.activo;
    }

    @Override
    public void grabar(RandomAccessFile raf) throws IOException {
        raf.writeChar(activo);           
        raf.writeInt(idTurno);           
        escribirStringFijo(raf, servicio, 60); 
        raf.writeDouble(costo);         
        raf.writeInt(dniPaciente);      
    }

    @Override
    public void leer(RandomAccessFile raf) throws IOException {
        activo = raf.readChar();
        idTurno = raf.readInt();
        servicio = leerStringFijo(raf, 60);
        costo = raf.readDouble();
        dniPaciente = raf.readInt();
    }

    private void escribirStringFijo(RandomAccessFile raf, String s, int largo) throws IOException {
        StringBuffer sb = new StringBuffer(s);
        if (sb.length() > largo) {
            sb.setLength(largo);
        } else {
            while (sb.length() < largo) {
                sb.append(' ');
            }
        }
        raf.writeChars(sb.toString());
    }

    private String leerStringFijo(RandomAccessFile raf, int largo) throws IOException {
        char[] chars = new char[largo];
        for (int i = 0; i < largo; i++) {
            chars[i] = raf.readChar();
        }
        return new String(chars).trim();
    }

    @Override
    public long largo() {
       
        return 2 + 4 + (60 * 2) + 8 + 4; //  138
    }

    @Override
    public long getClave() {
        return idTurno;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public int getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(int dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public char getActivo() {
        return activo;
    }

    @Override
    public void setActivo(char c) {
        this.activo = c;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Servicio: %s, Costo: %.2f, DNI Paciente: %d, Activo: %c",
                idTurno, servicio, costo, dniPaciente, activo);
    }


}


package Arbol;


public class PacienteArbol implements Comparable<PacienteArbol> {
    private int dni;
    private int pos; 

    public PacienteArbol(int dni, int pos) {
        this.dni = dni;
        this.pos = pos;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int compareTo(PacienteArbol otro) {
        return Integer.compare(this.dni, otro.dni); // se compara por DNI
    }

    @Override
    public String toString() {
        return "DNI: " + dni + " - Pos: " + pos;
    }
}

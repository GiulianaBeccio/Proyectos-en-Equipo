package Arbol;


public class DatoArbol {
     private int pos;
	public DatoArbol(int pos) {
		this.pos = pos;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public String toString()
	{
		return pos + " ";
	}
     
}
